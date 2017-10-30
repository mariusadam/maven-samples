package com.ubb.ppp.calculator;

/**
 * @author Marius Adam
 */
public abstract class Division<T> implements Operation<T> {
    @Override
    public String getName() {
        return "division";
    }

    @Override
    public String getAlias() {
        return "/";
    }
}
