package com.tora.producerconsumer.simple;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;

/**
 * @author Marius Adam
 */
public class SimpleMessageConsumer implements Runnable {
    private final BlockingQueue<Message> queue;
    private String outputFile;

    public SimpleMessageConsumer(String outputFile, BlockingQueue<Message> queue) {
        this.outputFile = outputFile;
        this.queue = queue;
    }

    @Override
    public void run() {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(outputFile))) {
            while (true) {
                Message m = queue.take();
                System.out.println("Consumed " + m);
                writer.print("%");
                writer.print(m.format());
                writer.flush();
            }
        } catch (InterruptedException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
