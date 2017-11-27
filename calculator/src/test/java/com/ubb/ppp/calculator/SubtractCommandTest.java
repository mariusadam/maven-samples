package com.ubb.ppp.calculator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SubtractCommandTest {
    @Test
    public void execute() throws Exception {
        Command cmd = new SubtractCommand();

        assertEquals("-1.0", cmd.execute("1", "2"));
        assertEquals("3.0", cmd.execute("3", "0"));
    }

}