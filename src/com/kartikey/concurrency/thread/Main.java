package com.kartikey.concurrency.thread;

import static com.kartikey.concurrency.ThreadColour.*;

public class Main {
    public static void main(String[] args) {
        System.out.println(ANSI_PURPLE + "Hello from the main thread");

        // Implement the run() method but call the start() method
        // start() -> JVM creates a new thread apart from the main thread to run the code in run()
        // if you call run() -> the code is run on the same thread as where it is called.
        Thread anotherThread = new AnotherThread();
        anotherThread.setName("=== Another Thread ===");
        anotherThread.start(); // --> prints === Another Thread ===
        // anotherThread.run(); --> prints main

        // If you use anonymous classes for thread you have to start it immediately.
        new Thread(() -> System.out.println(ANSI_GREEN + "Hello from the anonymous class thread")).start();

        // Runnable interface
        // Runnable is more flexible, and most of Java 8 APIs and lambda expressions support Runnable
        Thread myRunnableThread = new Thread(new MyRunnable() {
            @Override
            public void run() {
                System.out.println(ANSI_RED + "Hello from the anonymous class's implementation " +
                        "of MyRunnable");

                try {
                    anotherThread.join();
                    System.out.println(ANSI_RED + "AnotherThread terminated/timed out, so I'm running again.");
                } catch (InterruptedException ex) {
                    System.out.println(ANSI_RED + "I couldn't wait after all. I was interrupted");
                }
            }
        });

        myRunnableThread.start();
        // anotherThread.interrupt();

        System.out.println(ANSI_PURPLE + "Hello again from the main thread");
    }
}
