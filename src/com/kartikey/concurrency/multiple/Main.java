package com.kartikey.concurrency.multiple;

import com.kartikey.concurrency.ThreadColour;

public class Main {

    public static void main(String[] args) {
        Countdown countdown = new Countdown();
        CountdownThread t1 = new CountdownThread(countdown);
        t1.setName("Thread 1");
        CountdownThread t2 = new CountdownThread(countdown);
        t2.setName("Thread 2");

        // Cannot guarantee order of execution this way.
        t1.start();
        t2.start();
    }

}

class Countdown {

    // Application/Process Heap (Common across all threads)
    private int i;

    // Adding synchronized keyword to method deals with race conditions
    public void doCountdown() {
        // Enhanced switch statement
        String color = switch (Thread.currentThread().getName()) {
            case "Thread 1" -> ThreadColour.ANSI_CYAN;
            case "Thread 2" -> ThreadColour.ANSI_PURPLE;
            default -> ThreadColour.ANSI_GREEN;
        };

        // Local variables are stored in Thread Stack -> Each Thread will create its own copy
        // of the variable, and each copy is an object that has its own lock
        // Local variables won't work for synchronized block except for local String variables
        synchronized (this) {
            for (i = 10; i > 0; i--) {
                System.out.println(color + Thread.currentThread().getName() + ": i = " + i);
            }
        }

        // Local variables, for e.g., here if int i = 0 is used as for loop initializer
        // are part of thread stack and isolated from other threads running the same process
        /*for (i = 10; i > 0; i--) {
            System.out.println(color + Thread.currentThread().getName() + ": i = " + i);
        }*/

        // Thread interference -> Two threads working on the same instance variable ~ race condition
        // like for private int i over here
    }

}

class CountdownThread extends Thread {
    private final Countdown threadCountdown;

    public CountdownThread(Countdown countdown) {
        threadCountdown = countdown;
    }

    @Override
    public void run() {
        threadCountdown.doCountdown();
    }
}
