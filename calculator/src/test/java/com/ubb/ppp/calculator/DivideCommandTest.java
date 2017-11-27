package com.ubb.ppp.calculator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DivideCommandTest {
    @Test
    public void execute() throws Exception {
        Command cmd = new DivideCommand();

        assertEquals("3.0", cmd.execute("6", "2"));
        assertEquals("Infinity", cmd.execute("3", "0"));
    }

}