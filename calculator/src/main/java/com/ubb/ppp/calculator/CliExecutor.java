package com.ubb.ppp.calculator;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Marius Adam
 */
public class CliExecutor {
    private Map<String, Command> commands;

    public CliExecutor() {
        commands = new HashMap<>();
    }

    public void addCommand(Command c) {
        commands.put(c.getKey(), c);
    }

    public void execute() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Menu: ");
            commands.forEach((s, c) -> System.out.printf("    - %s %s%n", s, c.getDescription()));
            System.out.print("Your pick ? ");
            String cmdKey = scanner.nextLine();
            Command cmd = commands.get(cmdKey);
            if (cmd == null) {
                System.err.printf("Unknown option '%s'%n", cmdKey);
                continue;
            }
            cmd.execute(scanner, System.out);
        }
    }
}
