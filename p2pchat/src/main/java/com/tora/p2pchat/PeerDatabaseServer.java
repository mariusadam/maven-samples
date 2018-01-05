package com.tora.p2pchat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Marius Adam
 */
public class PeerDatabaseServer {
    public static final  int                        DB_PORT           = 10000;
    private static final String                     STATUS_OK         = "OK";
    private static final String                     STATUS_ERR        = "ERROR";
    private static final String                     ACTION_REGISTER   = "!register";
    private static final String                     ACTION_UNREGISTER = "!unregister";
    private static final String                     ACTION_LIST       = "!list";
    private final        ExecutorService            executorService   = Executors.newFixedThreadPool(5);
    private final        Map<String, PeerInfo>      registeredPeers   = Collections.synchronizedMap(new HashMap<>());
    private final        Map<String, ActionHandler> actionHandlerMap  = new HashMap<>();
    private final ServerSocket serverSocket;

    public PeerDatabaseServer(int port) throws IOException {
        actionHandlerMap.put(ACTION_LIST, this::listPeers);
        actionHandlerMap.put(ACTION_REGISTER, this::registerPeer);
        actionHandlerMap.put(ACTION_UNREGISTER, this::unRegisterPeer);
        serverSocket = new ServerSocket(port);
    }

    public static void main(String[] args) throws IOException {
        new PeerDatabaseServer(DB_PORT).start();
    }

    public void start() {
        try {
            System.out.println("Listening for connections on port " + serverSocket.getLocalPort());
            while (!serverSocket.isClosed()) {
                if (Thread.interrupted()) {
                    throw new InterruptedException();
                }

                Socket socket = serverSocket.accept();
                executorService.submit(() -> serve(socket));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void serve(Socket client) {
        try {
            Scanner       scanner       = new Scanner(client.getInputStream());
            PrintWriter   writer        = new PrintWriter(client.getOutputStream());
            String        firstLine     = scanner.nextLine();
            ActionHandler actionHandler = actionHandlerMap.get(firstLine);
            if (actionHandler != null) {
                System.out.printf("Handling action %s for client %s%n", firstLine, client);
                actionHandler.handle(client, scanner, writer);
            } else {
                sendError(writer, "Undefined action  " + firstLine);
            }
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void registerPeer(Socket client, Scanner scanner, PrintWriter writer) {
        try {
            PeerInfo peerInfo = new PeerInfo(
                    scanner.nextLine(),
                    client.getInetAddress().getHostAddress(),
                    Integer.parseInt(scanner.nextLine())
            );
            registeredPeers.put(peerInfo.getName(), peerInfo);
            writer.println(STATUS_OK);
        } catch (Exception e) {
            sendError(writer, e.getMessage());
            throw e;
        }
    }

    private void unRegisterPeer(Socket client, Scanner scanner, PrintWriter writer) {
        try {
            String peerName = scanner.nextLine();
            if (registeredPeers.remove(peerName) == null) {
                sendError(writer, "Peer with name " + peerName + " is not registered");
                return;
            }
            writer.println(STATUS_OK);
            writer.flush();
        } catch (Exception e) {
            sendError(writer, e.getMessage());
            throw e;
        }
    }

    private void sendError(PrintWriter writer, String msg) {
        writer.println(STATUS_ERR);
        writer.println(msg);
    }

    private void listPeers(Socket client, Scanner scanner, PrintWriter writer) {
        try {
            writer.println(STATUS_OK);
            writer.println(registeredPeers.size());
            writer.flush();
            registeredPeers.forEach((s, peerInfo) -> {
                writer.println(peerInfo.getName());
                writer.println(peerInfo.getHost());
                writer.println(peerInfo.getPort());
            });
        } catch (Exception e) {
            sendError(writer, e.getMessage());
        }
    }

    interface ActionHandler {
        void handle(Socket client, Scanner input, PrintWriter writer);
    }
}
