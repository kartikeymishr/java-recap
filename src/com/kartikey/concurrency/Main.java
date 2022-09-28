package com.kartikey.concurrency;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Message message = new Message();
        new Thread(new Writer(message)).start();
        new Thread(new Reader(message)).start();
    }
}

class Message {
    private String message;
    private boolean empty = true;

    public synchronized String read() {
        while (empty) {
            try {
                wait();
            } catch (InterruptedException e) {
                // do something
            }
        }

        empty = true;
        notifyAll();

        return message;
    }

    public synchronized void write(String message) {
        while (!empty) {
            try {
                wait();
            } catch (InterruptedException e) {
                // do something
            }
        }

        empty = false;
        notifyAll(); // We can't notify a specific thread so by convention we use notifyAll()

        this.message = message;
    }
}

class Writer implements Runnable {
    private final Message message;

    public Writer(Message message) {
        this.message = message;
    }

    @Override
    public void run() {
        String[] messages = {
                "Humpty Dumpty sat on a wall",
                "Humpty Dumpty had a great fall",
                "All the kings horses and all the kings men",
                "Couldn't put Humpty together again"
        };

        Random random = new Random();

        for (String s : messages) {
            message.write(s);

            try {
                Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException ex) {
                // do something
            }
        }

        message.write("Finished");
    }
}

class Reader implements Runnable {
    private final Message message;

    public Reader(Message message) {
        this.message = message;
    }

    @Override
    public void run() {
        Random random = new Random();

        // This Thread is looping and holding a lock on Message object
        // Dead Lock here
        for (String latestMessage = message.read(); !latestMessage.equals("Finished"); latestMessage = message.read()) {
            System.out.println(latestMessage);

            try {
                Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException ex) {
                // do something
            }
        }
    }
}