package com.tora.p2pchat;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author Marius Adam
 */
public class PeerRegistry {
    private Map<String, PeerConnection> connectedPeers = new HashMap<>();

    public PeerConnection connect(Peer thisPeer, PeerInfo other) throws IOException {
        if (connectedPeers.get(other.getName()) == null) {
            connectedPeers.put(other.getName(), new PeerConnection(thisPeer, other));
        }

        PeerConnection connection = connectedPeers.get(other.getName());
        if (!connection.check()) {
            throw new RuntimeException("Cannot connect to " + other);
        }

        return connection;
    }

    public void disconnect(PeerInfo other) throws Exception {
        PeerConnection peerConnection = connectedPeers.get(other.getName());
        if (peerConnection == null) {
            throw new RuntimeException(String.format("Peer %s is not connected.", other));
        }

        connectedPeers.remove(other.getName());
        peerConnection.close();
    }

    public void closeAll() {
        connectedPeers.forEach((s, peerConnection) -> {
            try {
                peerConnection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        connectedPeers.clear();
    }

    public Collection<PeerConnection> getConnections() {
        return connectedPeers.values();
    }
}
