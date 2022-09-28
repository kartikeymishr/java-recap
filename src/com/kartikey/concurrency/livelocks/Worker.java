package com.kartikey.concurrency.livelocks;

public class Worker {

    private String name;
    private boolean active;

    public Worker(String name, boolean active) {
        this.name = name;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public synchronized void work(SharedResource resource, Worker otherWorker) {
        while (active) {
            if (resource.getOwner() != this) {
                try {
                    wait(100);
                } catch (InterruptedException ex) {
                    // do something
                }

                continue;
            }

            if (otherWorker.isActive()) {
                System.out.println(getName() + ": give the resource to the worker " + otherWorker.getName());
                resource.setOwner(otherWorker);
                continue;
            }

            System.out.println(getName() + ": working on the common resource");
            active = false;
            resource.setOwner(otherWorker);
        }
    }
}
