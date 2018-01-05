package com.tora.p2pchat;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws IOException {
        System.out.print( "Your name = " );
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        PeerDatabaseClient peerDatabaseClient = new PeerDatabaseClient(() -> new Socket("localhost", 10000));
        Chat chat = new Chat(name, peerDatabaseClient);
        chat.start();
    }

    private static void databaseClient() throws IOException {
        PeerDatabaseClient client = new PeerDatabaseClient(() -> new Socket("localhost", 10000));
        ExecutorService    reader = Executors.newFixedThreadPool(5);
        ExecutorService    writer = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 2; i++) {
//            client.registerPeer("peer" + i, i);
//            writer.submit(() -> {
//                try {
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
//
//            reader.submit(() -> {
//                try {
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
        }

        client.listPeers().forEach(System.out::println);

        reader.shutdown();
        writer.shutdown();
    }
}
