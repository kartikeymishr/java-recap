package com.kartikey.concurrency.livelocks;

public class Main {
    public static void main(String[] args) {
        final Worker worker1 = new Worker("Worker1", true);
        final Worker worker2 = new Worker("Worker2", true);

        final SharedResource sharedResource = new SharedResource(worker1);

        new Thread(() -> worker1.work(sharedResource, worker2)).start();
        new Thread(() -> worker2.work(sharedResource, worker1)).start();
    }
}
