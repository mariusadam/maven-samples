package com.ubb.ppp.calculator;

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

    public String execute(String left, String right) {
        return ((Double) (Double.parseDouble(left) - Double.parseDouble(right))).toString();
    }
}
