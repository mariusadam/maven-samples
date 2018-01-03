package com.tora.bigdecimal.serializer;

import com.tora.bigdecimal.Utils;

import java.io.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @author Marius Adam
 */
public class FileTreeBigDecimalListSerializer implements BigDecimalListSerializer {
    private final String destinationDir;
    private final int THREADS_COUNT = Runtime.getRuntime().availableProcessors();

    public FileTreeBigDecimalListSerializer(String destinationDir) {
        this.destinationDir = destinationDir;
    }

    @Override
    public void serialize(List<BigDecimal> decimals) {
        List<Thread> threads = Utils.paralleliseJobs(decimals.size(), THREADS_COUNT, (start, stop) -> () -> {
            for (int i = start; i < stop; ++i) {
                write(decimals.get(i), i);
            }
        });

        threads.forEach(Thread::start);
        threads.forEach(this::joinThreadSilently);
    }

    private void joinThreadSilently(Thread t) {
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<BigDecimal> unserialize(int size) {
        BigDecimal[] decimals = new BigDecimal[size];

        List<Thread> threads = Utils.paralleliseJobs(size, THREADS_COUNT, (start, stop) -> () -> {
            for (int i = start; i < stop; ++i) {
                decimals[i] = read(i);
            }
        });

        threads.forEach(Thread::start);
        threads.forEach(this::joinThreadSilently);

        return Arrays.asList(decimals);
    }

    private BigDecimal read(int offset) {
        try (FileInputStream fileOut = new FileInputStream(getActualFilename(offset));
             ObjectInputStream out = new ObjectInputStream(fileOut)) {

            return (BigDecimal) out.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void write(BigDecimal decimal, int offset) {
        try (FileOutputStream fileOut = new FileOutputStream(getActualFilename(offset));
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(decimal);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getActualFilename(int offset) {
        return destinationDir + File.separator + offset;
    }
}
