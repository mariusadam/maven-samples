package com.tora;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        List<BigDecimal> decimals = Stream.of(
                new BigDecimal("1"),
                new BigDecimal("12"),
                new BigDecimal("13"),
                new BigDecimal("14"),
                new BigDecimal("15"),
                new BigDecimal("16"),
                new BigDecimal("17"),
                new BigDecimal("28"),
                new BigDecimal("39")
        ).collect(Collectors.toList());

        String destDir = "/home/marius/IdeaProjects/ppp/maven-samples/bigdecimals/data";
        BigDecimalListSerializer serializer = new FileTreeBigDecimalListSerializer(destDir);
        serializer.serialize(decimals);


        System.out.println("Deserialized decimals are " + serializer.unserialize(decimals.size()));


        System.out.println(decimals);
        String helperFilename = destDir + "/" + "helper";
        BigDecimalHelperSerializer helperSerializer = new BigDecimalHelperSerializer();
        helperSerializer.serialize(BigDecimalHelper.create(), helperFilename);
        BigDecimalHelper calculator = helperSerializer.unserialize(helperFilename);

        System.out.println("Sum is: " + calculator.getAdder().apply(decimals));
        System.out.println("Average is: " + calculator.getAverager().apply(decimals));
        System.out.println("Top 50 value: " + calculator.getReporter().apply(decimals, 50D));
    }
}
