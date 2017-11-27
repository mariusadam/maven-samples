package com.ubb.ppp.calculator;

/**
 * @author Marius Adam
 */
public class Main {
    public static void main(String[] args) {
        CliExecutor executor = new CliExecutor();

        executor.addCommand(new AddCommand());
        executor.addCommand(new SubtractCommand());
        executor.addCommand(new MultiplyCommand());
        executor.addCommand(new DivideCommand());
        executor.addCommand(new ExitCommand());

        executor.execute();
    }
}
