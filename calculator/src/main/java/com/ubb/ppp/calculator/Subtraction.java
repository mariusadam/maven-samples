package com.ubb.ppp.calculator;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.Arrays;

/**
 * @author Marius Adam
 */
public class Subtraction<T> implements Operation {
    @Override
    public String getName() {
        return "subtraction";
    }

    @Override
    public String getAlias() {
        return "-";
    }

    @Override
    public Integer apply(Integer... args) {
        Util.assertMaxCount(args, 2);

        return args[0] - args[1];
    }
}
