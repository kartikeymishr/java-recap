package com.kartikey.concurrency.challenge;

public class Main {
    public static void main(String[] args) {
        final BankAccount account = new BankAccount(1000.0, "12345-678");

        Thread t1 = new Thread(() -> {
            account.deposit(300.00);
            account.withdraw(50.00);

            System.out.println("Balance remaining for the account number: " + account.getAccountNumber()
                    + " is == " + account.getBalance());
        });

        Thread t2 = new Thread(() -> {
            account.deposit(203.75);
            account.withdraw(100.00);

            System.out.println("Balance remaining for the account number: " + account.getAccountNumber()
                    + " is == " + account.getBalance());
        });

        t1.start();
        t2.start();
    }
}
