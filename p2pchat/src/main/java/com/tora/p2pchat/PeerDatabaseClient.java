package com.tora.p2pchat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Marius Adam
 */
public class PeerDatabaseClient {
    private static final String STATUS_OK  = "OK";
    private static final String STATUS_ERR = "ERROR";
    private final SocketFactory databaseServerSocketFactory;
    private final Map<String, PeerInfo> peerInfoMap = new HashMap<>();

    public PeerDatabaseClient(SocketFactory databaseServerSocketFactory) {
        this.databaseServerSocketFactory = databaseServerSocketFactory;
    }

    public void registerPeer(String peerName, int port) throws IOException {
        try (Socket socket = databaseServerSocketFactory.create()) {
            Scanner     scanner = new Scanner(socket.getInputStream());
            PrintWriter writer  = new PrintWriter(socket.getOutputStream());

            writer.println("!register");
            writer.flush();
            writer.println(peerName);
            writer.println(port);
            writer.flush();
            String response = scanner.nextLine();
            if (response.equals(STATUS_OK)) {
                return;
            }
            if (response.equals(STATUS_ERR)) {
                throw new RuntimeException(
                        String.format("Failed to register peer %s, reason: %s", peerName, scanner.nextLine())
                );
            }

            throw new RuntimeException("Unrecognized response " + response);
        }
    }

    public void unRegisterPeer(String peerName) throws IOException {
        try (Socket socket = databaseServerSocketFactory.create()) {
            Scanner     scanner = new Scanner(socket.getInputStream());
            PrintWriter writer  = new PrintWriter(socket.getOutputStream());

            writer.println("!unregister");
            writer.flush();
            writer.println(peerName);
            writer.flush();
            String response = scanner.nextLine();
            if (response.equals(STATUS_OK)) {
                return;
            }
            if (response.equals(STATUS_ERR)) {
                throw new RuntimeException(
                        String.format("Failed to un-register peer %s, reason: %s", peerName, scanner.nextLine())
                );
            }

            throw new RuntimeException("Unrecognized response " + response);
        }
    }

    public Collection<PeerInfo> listPeers() throws IOException {
        try (Socket socket = databaseServerSocketFactory.create()) {
            Scanner     scanner = new Scanner(socket.getInputStream());
            PrintWriter writer  = new PrintWriter(socket.getOutputStream());

            writer.println("!list");
            writer.flush();
            String firstLine = scanner.nextLine();
            if (!firstLine.equals(STATUS_OK)) {
                throw new RuntimeException("Failed to read peer list");
            }

            Integer size = Integer.parseInt(scanner.nextLine());
            peerInfoMap.clear();
            for (int i = 0; i < size; i++) {
                PeerInfo peerInfo = new PeerInfo(
                        scanner.nextLine(),
                        scanner.nextLine(),
                        Integer.parseInt(scanner.nextLine())
                );
                peerInfoMap.put(peerInfo.getName(), peerInfo);
            }

            return peerInfoMap.values();
        }
    }

    public PeerInfo getPeerInfo(String name) {
        return peerInfoMap.get(name);
    }

    interface SocketFactory {
        Socket create() throws IOException;
    }
}
