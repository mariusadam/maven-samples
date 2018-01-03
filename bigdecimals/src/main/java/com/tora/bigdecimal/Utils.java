package com.tora.bigdecimal;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

/**
 * @author Marius Adam
 */
public class Utils {
    public static List<Thread> paralleliseJobs(int size, int threadsCount, BiFunction<Integer, Integer, Runnable> jobFactory) {
        threadsCount = Math.min(size, threadsCount);

        int span = size / threadsCount;
        int remaining = size % threadsCount;
        List<Thread> threads = new ArrayList<>();
        int start = 0;
        int stop = 0;

        for (int i = 0; i < threadsCount; i++) {
            stop = stop + span;
            if (remaining > 0) {
                stop = stop + 1;
                remaining = remaining - 1;
            }

            threads.add(new Thread(jobFactory.apply(start, stop)));
            start = stop;
        }

        return threads;
    }
}

