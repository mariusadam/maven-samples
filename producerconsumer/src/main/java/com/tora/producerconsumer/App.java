package com.tora.producerconsumer;

import com.tora.producerconsumer.simple.Message;
import com.tora.producerconsumer.simple.SimpleMessageConsumer;
import com.tora.producerconsumer.simple.SimpleMessageProducer;

import java.io.FileNotFoundException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws FileNotFoundException {
        BlockingQueue<Message> queue = new LinkedBlockingQueue<>();
        String dir = "/home/marius/IdeaProjects/ppp/maven-samples/producerconsumer/src/main/resources/";

        new Thread(new SimpleMessageProducer(dir + "input4.txt", queue)).start();
        new Thread(new SimpleMessageConsumer(dir + "output4.txt", queue)).start();

         int i = 10;
         while (i > 0) {
             i--;
         }

        System.out.println("Startd...");
    }
}
