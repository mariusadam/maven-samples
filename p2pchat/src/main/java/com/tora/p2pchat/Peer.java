package com.tora.p2pchat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Marius Adam
 */
public class Peer implements AutoCloseable {
    private String               name;
    private Set<MessageListener> messageListeners;
    private ServerSocket         serverSocket;
    private ExecutorService awaitExecutor      = Executors.newSingleThreadExecutor();
    private ExecutorService connectionExecutor = Executors.newWorkStealingPool();

    public Peer(String name) throws IOException {
        this.serverSocket = new ServerSocket(0);
        this.name = name;
        this.messageListeners = new HashSet<>();
        awaitExecutor.execute(this::awaitConnections);
    }

    private void awaitConnections() {
        try {
            while (!serverSocket.isClosed()) {
                Socket other = serverSocket.accept();
                receiveMessage("app", "Connected to " + other);
                connectionExecutor.submit(() -> waitForMessages(other));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void waitForMessages(Socket socket) {
        PrintWriter writer  = null;
        Scanner     scanner = null;
        try {
            writer = new PrintWriter(socket.getOutputStream());
            scanner = new Scanner(socket.getInputStream());
            String otherName = scanner.nextLine();
            writer.println("!ack");
            writer.flush();

            while (!socket.isClosed() && socket.isConnected()) {
                receiveMessage(otherName, scanner.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    public void addMessageListener(MessageListener listener) {
        messageListeners.add(listener);
    }

    public void receiveMessage(PeerInfo sender, String msg) {
        receiveMessage(sender.getName(), msg);
    }

    public void receiveMessage(String sender, String msg) {
        messageListeners.forEach(listener -> listener.onMessageReceived(sender, msg));
    }

    public String getName() {
        return name;
    }

    public int getPort() {
        return serverSocket.getLocalPort();
    }

    @Override
    public void close() throws Exception {
        awaitExecutor.shutdown();
        serverSocket.close();
    }

    interface MessageListener {
        void onMessageReceived(String senderName, String msg);
    }
}
