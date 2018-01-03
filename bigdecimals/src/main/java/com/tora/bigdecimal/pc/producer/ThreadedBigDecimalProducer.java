package com.tora.bigdecimal.pc.producer;

import com.tora.bigdecimal.Utils;
import com.tora.bigdecimal.pc.Message;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * @author Marius Adam
 */
public class ThreadedBigDecimalProducer extends BigDecimalProducer {
    private Integer threadsCount;

    public ThreadedBigDecimalProducer(String sourceDir, BlockingQueue<Message> queue) {
        this(sourceDir, queue, Runtime.getRuntime().availableProcessors());
    }

    public ThreadedBigDecimalProducer(String sourceDir, BlockingQueue<Message> queue, Integer threadsCount) {
        super(sourceDir, queue);
        this.threadsCount = threadsCount;
    }

    @Override
    protected void readAndPut(File[] files) {
        List<Thread> threads = Utils.paralleliseJobs(files.length, threadsCount, (start, stop) -> () -> {
            for (Integer i = start; i < stop; i++) {
                put(new Message(i.toString(), read(files[i])));
            }
        });

        threads.forEach(Thread::start);
        System.out.printf("Started %d threads to read BigDecimal objects from [%s]%n", threads.size(), getSourceFolderName());
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
