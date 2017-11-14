package com.ubb.ppp.calculator;

import org.junit.Test;

import static org.junit.Assert.*;

public class MultiplyCommandTest {
    @Test
    public void execute() throws Exception {
        Command cmd = new MultiplyCommand();

        assertEquals("2.0", cmd.execute("1", "2"));
        assertEquals("0.0", cmd.execute("3", "0"));
    }

}