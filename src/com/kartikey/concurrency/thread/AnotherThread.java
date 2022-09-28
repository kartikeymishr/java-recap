package com.kartikey.concurrency.thread;

import static com.kartikey.concurrency.ThreadColour.ANSI_BLUE;

public class AnotherThread extends Thread {
    @Override
    public void run() {
        System.out.println(ANSI_BLUE + "Hello from " + currentThread().getName());

        try {
            System.out.println(ANSI_BLUE + currentThread().getName() + " is sleeping now.");
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            System.out.println(ANSI_BLUE + "Another thread woke me up.");

            return;
        }

        System.out.println(ANSI_BLUE + "3 seconds have passed, and I am awake.");
    }
}
