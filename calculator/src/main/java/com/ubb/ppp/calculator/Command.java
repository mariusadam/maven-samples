package com.ubb.ppp.calculator;

/**
 * @author Marius Adam
 */
public interface Command {
    String getKey();

    String getDescription();

    String execute(String left, String right);
}
