package com.tora.p2pchat;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * @author Marius Adam
 */
public class Chat {
    private static final String CMD_HELLO      = "!hello";
    private static final String CMD_BYE        = "!bye";
    private static final String CMD_BYEBYE     = "!byebye";
    private static final String CMD_LIST_PEERS = "!active";
    private final Peer               thisPeer;
    private final PeerDatabaseClient peerDatabaseClient;
    private       PeerConnection     peerConnection;
    private boolean                     stopped        = false;
    private PeerRegistry                peerRegistry   = new PeerRegistry();
    private Map<String, CommandHandler> commandHandler = new HashMap<>();

    public Chat(String myName, PeerDatabaseClient peerDatabaseClient) throws IOException {
        this.peerDatabaseClient = peerDatabaseClient;

        commandHandler.put(CMD_HELLO, this::handleHello);
        commandHandler.put(CMD_BYE, this::handleBye);
        commandHandler.put(CMD_BYEBYE, this::handleByeBye);
        commandHandler.put(CMD_LIST_PEERS, this::handleListPeers);

        thisPeer = new Peer(myName);
        peerDatabaseClient.registerPeer(thisPeer.getName(), thisPeer.getPort());
        thisPeer.addMessageListener(this::onMessageReceived);
    }

    private void onMessageReceived(String senderName, String msg) {
        System.out.println(String.format("\r[ %s -> you ] : %s", senderName, msg));
        System.out.print(getPrompt());
    }

    public void start() {
        Scanner     scanner  = new Scanner(System.in);
        Set<String> commands = commandHandler.keySet();
        while (!stopped) {
            System.out.print(getPrompt());
            String line = scanner.nextLine();

            boolean isCmd = false;
            for (String cmd : commands) {
                if (line.startsWith(cmd)) {
                    commandHandler.get(cmd).handle(line);
                    isCmd = true;
                    break;
                }
            }

            if (!isCmd) {
                handleMessage(line);
            }
        }
    }

    private String getPrompt() {
        if (peerConnection == null) {
            return "[ disconnected ] : ";
        }

        return String.format("[ you -> %s ] : ", peerConnection.getPeerInfo().getName());
    }

    private void handleMessage(String line) {
        if (peerConnection == null) {
            System.out.println("Disconnected, cannot send message.");
            return;
        }

        peerConnection.sendMessage(line);
    }

    private void handleListPeers(String line) {
        try {
            peerDatabaseClient.listPeers().forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleBye(String line) {
        handleMessage(line);
        try {
            peerRegistry.disconnect(peerConnection.getPeerInfo());
            peerConnection = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleByeBye(String line) {
        try {
            peerRegistry.getConnections().forEach(peerConnection -> peerConnection.sendMessage(CMD_BYEBYE));
            peerRegistry.closeAll();
            peerDatabaseClient.unRegisterPeer(thisPeer.getName());
            thisPeer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            stopped = true;
        }
    }

    private void handleHello(String line) {
        String name = line.substring(CMD_HELLO.length() + 1);
        if (name.equals(thisPeer.getName())) {
            System.out.println("Cannot connect to self.");
            return;
        }

        try {
            PeerInfo peerInfo = peerDatabaseClient.getPeerInfo(name);
            if (peerInfo == null) {
                System.out.printf("Could not find peer %s. Type !active to refresh the list of peers%n", name);
                return;
            }

            peerConnection = peerRegistry.connect(thisPeer, peerInfo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    interface CommandHandler {
        void handle(String input);
    }
}
