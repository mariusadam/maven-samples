package com.ubb.ppp.calculator;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * @author Marius Adam
 */
public class ExitCommand implements Command {
    @Override
    public String getKey() {
        return "q";
    }

    @Override
    public String getDescription() {
        return null;
    }

    public String execute(String left, String right) {
        System.exit(0);
        return "";
    }
}
