package com.tora.producerconsumer.simple;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

/**
 * @author Marius Adam
 */
public class SimpleMessageProducer implements Runnable {
    private final BlockingQueue<Message> queue;
    private String fileName;

    public SimpleMessageProducer(String fileName, BlockingQueue<Message> queue) {
        this.fileName = fileName;
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            Scanner scanner = new Scanner(new FileInputStream(fileName)).useDelimiter("%");
            scanner.forEachRemaining(this::putMessage);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void putMessage(String s) {
        try {
            queue.put(new Message(s));
        } catch (InterruptedException e) {
            throw new RuntimeException("Failed to create " + s, e);
        }
    }
}
