package com.kartikey.concurrency.concurrent;

import java.util.Random;
import java.util.concurrent.*;

import static com.kartikey.concurrency.ThreadColour.*;
import static com.kartikey.concurrency.concurrent.ExecutorMain.END_OF_FILE;

public class ExecutorMain {
    public static final String END_OF_FILE = "EOF";

    public static void main(String[] args) {
        ArrayBlockingQueue<String> buffer = new ArrayBlockingQueue<>(6);

        // Provides thread safe queues
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        ExecutorProducer producer = new ExecutorProducer(buffer, ANSI_CYAN);
        ExecutorConsumer consumer = new ExecutorConsumer(buffer, ANSI_PURPLE);
        ExecutorConsumer consumer2 = new ExecutorConsumer(buffer, ANSI_GREEN);

        executorService.execute(producer);
        executorService.execute(consumer);
        executorService.execute(consumer2);

        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() {
                System.out.println(ANSI_BLACK + "I am being printed from the callable class.");
                return ANSI_BLACK + "This is a callable result.";
            }
        });

        try {
            System.out.println(future.get());
        } catch (ExecutionException ex) {
            System.out.println(ANSI_BLACK + "Something went wrong.");
        } catch (InterruptedException ex) {
            System.out.println(ANSI_BLACK + "Thread running the task was interrupted.");
        }

        // Necessary otherwise app will remain live long after the threads have finished execution.
        executorService.shutdown();
    }
}

class ExecutorProducer implements Runnable {
    private final ArrayBlockingQueue<String> buffer;
    private final String color;

    public ExecutorProducer(ArrayBlockingQueue<String> buffer, String color) {
        this.buffer = buffer;
        this.color = color;
    }

    @Override
    public void run() {
        Random random = new Random();
        String[] nums = {"1", "2", "3", "4", "5"};

        for (String num : nums) {
            try {
                System.out.println(color + "Adding..." + num);

                // put() is a synchronized method
                buffer.put(num);

                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException ex) {
                System.out.println("Producer was interrupted.");
            }
        }

        System.out.println(color + "Adding EOF and exiting...");

        try {
            buffer.put(END_OF_FILE);
        } catch (InterruptedException ex) {
            // do something
        }
    }
}

class ExecutorConsumer implements Runnable {
    private final ArrayBlockingQueue<String> buffer;
    private final String color;

    public ExecutorConsumer(ArrayBlockingQueue<String> buffer, String color) {
        this.buffer = buffer;
        this.color = color;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (buffer) {
                try {
                    if (buffer.isEmpty()) {
                        continue;
                    }

                    if (buffer.peek().equals(END_OF_FILE)) {
                        System.out.println(color + "Exiting");
                        break;
                    } else {
                        System.out.println(color + "Removed " + buffer.take());
                    }
                } catch (InterruptedException ex) {
                    // do something
                }
            }
        }
    }
}