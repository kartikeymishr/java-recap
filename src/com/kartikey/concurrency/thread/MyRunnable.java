package com.kartikey.concurrency.thread;

import static com.kartikey.concurrency.ThreadColour.ANSI_RED;

public class MyRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println(ANSI_RED + "Hello from MyRunnable");
    }
}
