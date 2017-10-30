package com.ubb.ppp.calculator;

/**
 * @author Marius Adam
 */
public interface Operation {
    String getName();
    String getAlias();
    Integer apply(Integer ...args);
}
