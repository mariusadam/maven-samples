package com.ubb.ppp.calculator;

/**
 * @author Marius Adam
 */
public abstract class Addition<T> implements Operation<T> {
    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getAlias() {
        return "+";
    }
}
