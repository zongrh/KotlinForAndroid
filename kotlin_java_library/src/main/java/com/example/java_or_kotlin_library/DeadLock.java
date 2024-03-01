package com.example.java_or_kotlin_library;

/**
 * FileName: DeadLock
 * Author: nanzong
 * Date: 2023/3/21 19:59
 * Description:
 * History:
 */
public class DeadLock {
    public static String obj1 = "obj1";
    public static String obj2 = "obj2";

    public static void main(String[] args) {
        Thread a = new Thread(new Lock1());
        Thread b = new Thread(new Lock2());
        a.start();
        b.start();
    }
}

class Lock1 implements Runnable {

    @Override
    public void run() {
        while (true) {
            synchronized (DeadLock.obj1) {
                try {
                    Thread.sleep(3000);
                    System.out.println("Lock1 lock obj2");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

class Lock2 implements Runnable {

    @Override
    public void run() {
        while (true) {
            synchronized (DeadLock.obj2) {
                try {
                    System.out.println("Lock2 lock obj2");
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (DeadLock.obj1) {
                    System.out.println("Lock2 lock obj1");
                }

            }
        }
    }
}