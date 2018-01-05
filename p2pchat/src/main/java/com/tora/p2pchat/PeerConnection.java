package com.tora.p2pchat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Marius Adam
 */
public class PeerConnection implements AutoCloseable {
    private final Socket      socket;
    private final PrintWriter writer;
    private final PeerInfo    peerInfo;
    private final ExecutorService messageReader = Executors.newSingleThreadExecutor();
    private final ExecutorService messageWriter = Executors.newSingleThreadExecutor();

    public PeerConnection(Peer thisPeer, PeerInfo other) throws IOException {
        socket = new Socket(other.getHost(), other.getPort());

        Scanner scanner = new Scanner(socket.getInputStream());
        writer = new PrintWriter(socket.getOutputStream());
        writer.println(thisPeer.getName());
        writer.flush();

        String firstLine = scanner.nextLine();
        if (!firstLine.equals("!ack")) {
            throw new RuntimeException("Failed to connect with " + other);
        }
        messageReader.submit(() -> {
            while (!socket.isClosed() && socket.isConnected()) {
                thisPeer.receiveMessage(other, scanner.nextLine());
            }
        });
        peerInfo = other;
    }

    public void sendMessage(String msg) {
        messageWriter.submit(() -> {
            try {
                writer.println(msg);
                writer.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public boolean check() {
        return !socket.isClosed() && socket.isConnected();
    }

    @Override
    public void close() throws Exception {
        messageReader.shutdown();
        messageWriter.shutdown();
        socket.close();
    }

    public PeerInfo getPeerInfo() {
        return peerInfo;
    }
}
