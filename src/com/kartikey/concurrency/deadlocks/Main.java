package com.kartikey.concurrency.deadlocks;

import static com.kartikey.concurrency.ThreadColour.ANSI_BLUE;
import static com.kartikey.concurrency.ThreadColour.ANSI_RED;

public class Main {

    public static Object lock1 = new Object();
    public static Object lock2 = new Object();

    public static void main(String[] args) {
        new Thread1().start();
        new Thread2().start();
    }

    private static class Thread1 extends Thread {
        public void run() {
            System.out.println(ANSI_RED + "=== Starting ===");
            synchronized (lock1) {
                System.out.println(ANSI_RED + "Thread 1: Has lock1");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    // do something
                }

                System.out.println(ANSI_RED + "Thread 1: Waiting for lock2");
                synchronized (lock2) {
                    System.out.println(ANSI_RED + "Thread 1: Has lock1 and lock2");
                }

                System.out.println(ANSI_RED + "Thread 1: Released lock2");
            }

            System.out.println(ANSI_RED + "Thread 1: Released lock1");
            System.out.println(ANSI_RED + "=== Exiting ===");
        }
    }

    private static class Thread2 extends Thread {
        public void run() {
            System.out.println(ANSI_BLUE + "=== Starting ===");
            synchronized (lock1) {
                System.out.println(ANSI_BLUE + "Thread 2: Has lock1");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    // do something
                }

                System.out.println(ANSI_BLUE + "Thread 2: Waiting for lock2");
                synchronized (lock2) {
                    System.out.println(ANSI_BLUE + "Thread 2: Has lock1 and lock2");
                }

                System.out.println(ANSI_BLUE + "Thread 2: Released lock2");
            }

            System.out.println(ANSI_BLUE + "Thread 2: Released lock1");
            System.out.println(ANSI_BLUE + "=== Exiting ===");
        }
    }
}


