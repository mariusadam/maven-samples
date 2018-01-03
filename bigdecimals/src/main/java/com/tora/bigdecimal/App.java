package com.tora.bigdecimal;

import com.tora.bigdecimal.function.BigDecimalFunctions;
import com.tora.bigdecimal.pc.Message;
import com.tora.bigdecimal.pc.consumer.BigDecimalConsumer;
import com.tora.bigdecimal.pc.producer.SimpleBigDecimalProducer;
import com.tora.bigdecimal.pc.producer.ThreadedBigDecimalProducer;
import com.tora.bigdecimal.serializer.BigDecimalFunctionsSerializer;
import com.tora.bigdecimal.serializer.BigDecimalListSerializer;
import com.tora.bigdecimal.serializer.FileTreeBigDecimalListSerializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Hello world!
 */
public class App {
    public static final int TEST_REPETITIONS = 10;
    private static final String BASE_DIR = "/home/marius/IdeaProjects/ppp/maven-samples/bigdecimals/storage/";
    public static final String DECIMALS_DIR = BASE_DIR + "bigdecimals";
    public static final String CONSUMER_DEST_DIR = BASE_DIR + "consumer";
    public static final String FUNCTION_FILE_NAME = BASE_DIR + "functions/data";

    public static void main(String[] args) {

    }

    private static void pc() {
        List<Function<Void, BlockingQueue<Message>>> queueFactories = Stream.of(
                (Function<Void, BlockingQueue<Message>>) v -> new LinkedBlockingDeque<>(),
                v -> new LinkedBlockingQueue<>(),
                v -> new ArrayBlockingQueue<>(100),
                v -> new PriorityBlockingQueue<>(11, Comparator.comparing(Message::getObjectId))
        ).collect(Collectors.toList());

        long itemsCount = countFiles(DECIMALS_DIR);
        System.out.println("Decimals count " + itemsCount);

        List<Function<BlockingQueue<Message>, Runnable>> producerFactories = Stream.of(
                (Function<BlockingQueue<Message>, Runnable>) queue -> new SimpleBigDecimalProducer(DECIMALS_DIR, queue),
                queue -> new ThreadedBigDecimalProducer(DECIMALS_DIR, queue)
        ).collect(Collectors.toList());

        List<String> benchmarks = new ArrayList<>();
        queueFactories.forEach(queueFactory -> producerFactories.forEach(producerFactory -> {
            BlockingQueue<Message> queue = queueFactory.apply(null);
            Runnable producer = producerFactory.apply(queue);
            Runnable consumer = new BigDecimalConsumer(CONSUMER_DEST_DIR, queue, itemsCount);
            String key = String.format(
                    "[%s] -> [%s] using [%s]",
                    producer.getClass().getSimpleName(),
                    consumer.getClass().getSimpleName(),
                    queue.getClass().getSimpleName()
            );
            System.out.println("Running " + key);
            benchmarks.add(key + " took " + producerConsumerTest(producer, consumer));
        }));


        benchmarks.forEach(System.out::println);
    }

    private static long countFiles(String dir) {
        try {
            return Files.list(Paths.get(dir)).count();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static double producerConsumerTest(Runnable producer, Runnable consumer) {
        long start = System.currentTimeMillis();
        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        producerThread.start();
        consumerThread.start();

        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return System.currentTimeMillis() - start;
    }

    private static void bigDecimalsSerialization() {
        int decimalsToGenerate = 100000;
        Random random = new SecureRandom();
        List<BigDecimal> decimals = new ArrayList<>();
        for (int i = 0; i < decimalsToGenerate; i++) {
            decimals.add(new BigDecimal(random.nextDouble() * random.nextInt()));
        }

        BigDecimalListSerializer serializer = new FileTreeBigDecimalListSerializer(DECIMALS_DIR);
        serializer.serialize(decimals);


        System.out.println("Deserialized decimals are " + serializer.unserialize(decimals.size()));


        System.out.println(decimals);
        BigDecimalFunctionsSerializer functionsSerializer = new BigDecimalFunctionsSerializer();
        functionsSerializer.serialize(BigDecimalFunctions.create(), FUNCTION_FILE_NAME);
        BigDecimalFunctions calculator = functionsSerializer.unserialize(FUNCTION_FILE_NAME);

        System.out.println("Sum is: " + calculator.getAdder().apply(decimals));
        System.out.println("Average is: " + calculator.getAverager().apply(decimals));
        System.out.println("Top 50 value: " + calculator.getReporter().apply(decimals, 50D));
    }
}
