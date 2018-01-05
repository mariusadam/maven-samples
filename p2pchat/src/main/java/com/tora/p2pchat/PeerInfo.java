package com.tora.p2pchat;

/**
 * @author Marius Adam
 */
public class PeerInfo {
    private String name;
    private String host;
    private int    port;

    public PeerInfo(String name, String host, int port) {
        this.name = name;
        this.host = host;
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return String.format("PeerInfo{name='%s', port=%d, host='%s'}", name, port, host);
    }
}
