package com.kartikey.concurrency.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

import static com.kartikey.concurrency.ThreadColour.*;
import static com.kartikey.concurrency.concurrent.Main.END_OF_FILE;

public class Main {
    public static final String END_OF_FILE = "EOF";

    public static void main(String[] args) {
        // ArrayLists are not thread-safe
        List<String> buffer = new ArrayList<>();

        // ReentrantLocks have a count of how many times they are locked
        // If a particular one is locked twice, then it would have to be unlocked twice as well
        ReentrantLock bufferLock = new ReentrantLock();

        Producer producer = new Producer(buffer, ANSI_CYAN, bufferLock);
        Consumer consumer = new Consumer(buffer, ANSI_PURPLE, bufferLock);
        Consumer consumer2 = new Consumer(buffer, ANSI_GREEN, bufferLock);

        new Thread(producer).start();
        new Thread(consumer).start();
        new Thread(consumer2).start();

        // Drawbacks of synchronized() { }
        // Threads that are blocked due to waiting for the lock from the synchronized block
        // are stuck there. They cannot be interrupted.
        // Synchronized block must be within the same method scope.
        // We cannot time out after we have waited for the lock for a while
        // If multiple threads are waiting on the lock, it is not FCFS. Internal JVM rule is applied here.
    }
}

class Producer implements Runnable {
    private final List<String> buffer;
    private String color;
    private ReentrantLock bufferLock;

    public Producer(List<String> buffer, String color, ReentrantLock bufferLock) {
        this.buffer = buffer;
        this.color = color;
        this.bufferLock = bufferLock;
    }

    @Override
    public void run() {
        Random random = new Random();
        String[] nums = {"1", "2", "3", "4", "5"};

        for (String num : nums) {
            try {
                System.out.println(color + "Adding..." + num);

                bufferLock.lock();
                try {
                    buffer.add(num);
                } finally {
                    bufferLock.unlock();
                }

                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException ex) {
                System.out.println("Producer was interrupted.");
            }
        }

        System.out.println(color + "Adding EOF and exiting...");

        bufferLock.lock();
        try {
            buffer.add(END_OF_FILE);
        } finally {
            bufferLock.unlock();
        }
    }
}

class Consumer implements Runnable {
    private final List<String> buffer;
    private String color;
    private ReentrantLock bufferLock;

    public Consumer(List<String> buffer, String color, ReentrantLock bufferLock) {
        this.buffer = buffer;
        this.color = color;
        this.bufferLock = bufferLock;
    }

    @Override
    public void run() {
        int counter = 0;

        while (true) {
            if (bufferLock.tryLock()) {
                try {
                    if (buffer.isEmpty()) {
                        continue;
                    }
                    System.out.println(color + "Counter == " + counter);
                    counter = 0;
                    if (buffer.get(0).equals(END_OF_FILE)) {
                        System.out.println(color + "Exiting");
                        break;
                    } else {
                        System.out.println(color + "Removed " + buffer.remove(0));
                    }
                } finally {
                    bufferLock.unlock();
                }
            } else {
                counter++;
            }
        }
    }
}