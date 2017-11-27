package com.ubb.ppp.calculator;

import static org.junit.Assert.assertEquals;

public class AddCommandTest {
    @org.junit.Test
    public void execute() throws Exception {
        Command cmd = new AddCommand();

        assertEquals("3.0", cmd.execute("1", "2"));
        assertEquals("3.0", cmd.execute("3", "0"));
    }

}