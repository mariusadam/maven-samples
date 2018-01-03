package com.tora.bigdecimal.pc.producer;

import com.tora.bigdecimal.pc.Message;

import java.io.File;
import java.math.BigDecimal;
import java.util.concurrent.BlockingQueue;

/**
 * @author Marius Adam
 */
public class SimpleBigDecimalProducer extends BigDecimalProducer {
    public SimpleBigDecimalProducer(String sourceDir, BlockingQueue<Message> queue) {
        super(sourceDir, queue);
    }

    @Override
    protected void readAndPut(File[] files) {
        for (Integer i = 0; i < files.length; i++) {
            put(new Message(i.toString(), read(files[i])));
        }
    }
}
