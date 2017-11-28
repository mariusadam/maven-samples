package com.tora;

import junit.framework.TestCase;

import java.math.BigDecimal;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Marius Adam
 */
public class BigDecimalAdderTest extends TestCase {
    public void testApply() throws Exception {
        assertEquals(
                new BigDecimal("6"),
                new BigDecimalAdder().apply(
                        Stream.of(
                                new BigDecimal("1"),
                                new BigDecimal("2"),
                                new BigDecimal("3")
                        ).collect(Collectors.toList())
                )
        );
    }

}