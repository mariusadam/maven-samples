package com.ubb.ppp.calculator;

/**
 * @author Marius Adam
 */
public class Util {
    public static void assertMaxCount(Object[] items, int maxCount) {
        if (items.length > maxCount) {
            throw new RuntimeException("Max count count is " + maxCount);
        }
    }
}
