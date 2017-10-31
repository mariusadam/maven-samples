package com.ubb.ppp.calculator;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * @author Marius Adam
 */
public class SubtractCommand implements Command {
    @Override
    public String getKey() {
        return "-";
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void execute(Scanner scanner, PrintStream out) {

        Double a, b;
        out.print("a=");
        a = scanner.nextDouble();
        out.print("b=");
        b = scanner.nextDouble();

        System.out.println("The subtraction is: " + Double.toString(a - b));
    }
}
