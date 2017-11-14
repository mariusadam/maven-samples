package com.ubb.ppp.calculator;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * @author Marius Adam
 */
public interface Command {
    String getKey();
    String getDescription();
    String execute(String left, String right);
}
