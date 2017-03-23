package com.annotation;

/**
 * Created by root on 17-3-23.
 */
public class Testable {
    public void execute() {
        System.out.println("Executing..");
    }

    @Test
    void testExecute() {
        execute();
    }
}
