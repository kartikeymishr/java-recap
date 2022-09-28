package com.kartikey.concurrency.challenge;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {

    private double balance;
    private final String accountNumber;
    private Lock lock;

    public static final String ERROR_MESSAGE = "Thread was interrupted.";
    public static final String LOCK_ERROR_MESSAGE = "Couldn't get the lock.";

    public BankAccount(double balance, String accountNumber) {
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.lock = new ReentrantLock();
    }

    public void deposit(double amount) {
        boolean status = false;

        try {
            if (lock.tryLock(1000, TimeUnit.MILLISECONDS)) {
                try {
                    balance += amount;
                    status = true;
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println(LOCK_ERROR_MESSAGE);
            }
        } catch (InterruptedException e) {
            System.out.println(ERROR_MESSAGE);
        }

        System.out.println("Transaction status == " + status);
    }

    public void withdraw(double amount) {
        boolean status = false;

        try {
            if (lock.tryLock(1000, TimeUnit.MILLISECONDS)) {
                try {
                    balance -= amount;
                    status = true;
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println(LOCK_ERROR_MESSAGE);
            }
        } catch (InterruptedException ex) {
            System.out.println(ERROR_MESSAGE);
        }

        System.out.println("Transaction status == " + status);
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void printAccountNumber() {
        System.out.println("Account Number == " + accountNumber);
    }
}
