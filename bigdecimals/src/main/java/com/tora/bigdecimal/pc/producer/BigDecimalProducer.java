package com.tora.bigdecimal.pc.producer;

import com.tora.bigdecimal.pc.Message;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.util.concurrent.BlockingQueue;

/**
 * @author Marius Adam
 */
abstract public class BigDecimalProducer implements Runnable {
    private File sourceFolder;
    private String sourceFolderName;
    private BlockingQueue<Message> queue;

    public BigDecimalProducer(String sourceDir, BlockingQueue<Message> queue) {
        this.sourceFolder = new File(sourceDir);
        if (!sourceFolder.isDirectory()) {
            throw new RuntimeException(String.format("The source directory has to be a directory, [%s] given", sourceDir));
        }
        sourceFolderName = sourceDir;
        this.queue = queue;
    }

    @Override
    public void run() {
        readAndPut(sourceFolder.listFiles());
    }

    protected abstract void readAndPut(File[] files);

    protected void put(Message message) {
        try {
            queue.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    protected BigDecimal read(File file) {
        try (FileInputStream fileOut = new FileInputStream(file);
             ObjectInputStream out = new ObjectInputStream(fileOut)) {

            return (BigDecimal) out.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    protected String getSourceFolderName() {
        return sourceFolderName;
    }
}
