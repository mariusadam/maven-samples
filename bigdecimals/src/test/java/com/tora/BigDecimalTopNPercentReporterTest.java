package com.tora;

import junit.framework.TestCase;

import java.math.BigDecimal;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Marius Adam
 */
public class BigDecimalTopNPercentReporterTest extends TestCase {
    public void testApply() throws Exception {
        assertEquals(
                Stream.of(
                        new BigDecimal("3"),
                        new BigDecimal("2")
                ).collect(Collectors.toList()),
                new BigDecimalTopNPercentReporter().apply(
                        Stream.of(
                                new BigDecimal("1"),
                                new BigDecimal("0"),
                                new BigDecimal("2"),
                                new BigDecimal("3")
                        ).collect(Collectors.toList()),
                        50d
                )
        );
    }
}