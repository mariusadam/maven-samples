package com.tora.bigdecimal.pc.consumer;

import com.tora.bigdecimal.pc.Message;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.BlockingQueue;

/**
 * @author Marius Adam
 */
public class BigDecimalConsumer implements Runnable {
    private String                 destinationFolderName;
    private BlockingQueue<Message> queue;
    private long                   itemsCount;

    public BigDecimalConsumer(String destinationDir, BlockingQueue<Message> queue, long itemsCount) {
        this.itemsCount = itemsCount;
        File destinationFolder = new File(destinationDir);
        if (!destinationFolder.isDirectory()) {
            throw new RuntimeException(String.format("The destination directory has to be a directory, [%s] given", destinationDir));
        }

        destinationFolderName = destinationDir;
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < itemsCount; i++) {
                consume(queue.take());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void consume(Message message) {
        String destinationFileName = destinationFolderName + File.separator + message.getObjectId();
        try (FileOutputStream fileOut = new FileOutputStream(destinationFileName);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(message.getPayload());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
