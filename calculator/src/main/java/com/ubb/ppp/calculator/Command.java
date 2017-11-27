package com.ubb.ppp.calculator;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * @author Marius Adam
 */
public interface Command {
    String getKey();
    String getDescription();
    void execute(Scanner scanner, PrintStream out);
}
