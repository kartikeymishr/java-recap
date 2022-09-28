package com.kartikey.concurrency.deadlocks;

public class PoliteMain {

    public static void main(String[] args) {
        final PolitePerson jane = new PolitePerson("Jane");
        final PolitePerson john = new PolitePerson("John");

        new Thread(() -> jane.sayHello(john)).start();
        new Thread(() -> john.sayHello(jane)).start();
    }

    static class PolitePerson {
        private final String name;

        public PolitePerson(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public synchronized void sayHello(PolitePerson politePerson) {
            System.out.format("%s: %s" + " has said hello to me!%n", this.name, politePerson.getName());
            politePerson.sayHelloBack(this);
        }

        public synchronized void sayHelloBack(PolitePerson politePerson) {
            System.out.format("%s: %s" + " has said hello back to me!%n", this.name, politePerson.getName());
        }
    }
}
