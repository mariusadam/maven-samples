package com.ubb.ppp.calculator;

/**
 * @author Marius Adam
 */
public class AddCommand implements Command {
    @Override
    public String getKey() {
        return "+";
    }

    @Override
    public String getDescription() {
        return "a + b";
    }

    @Override
    public String execute(String left, String right) {
        return ((Double) (Double.parseDouble(left) + Double.parseDouble(right))).toString();
    }
}
