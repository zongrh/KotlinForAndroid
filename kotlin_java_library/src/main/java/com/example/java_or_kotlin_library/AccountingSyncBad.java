package com.example.java_or_kotlin_library;

public class AccountingSyncBad implements Runnable {
    static int i = 0;

    public synchronized void increase() {
        i++;
    }

    @Override
    public void run() {
        for (int j = 0; j < 1000000; j++) {
            increase();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //new新实例
        Thread t1 = new Thread(new AccountingSyncBad());
        //new新实例
        Thread t2 = new Thread(new AccountingSyncBad());
        t1.start();
        t2.start();
        //join含义:当前线程A等待thread线程终止之后才能从thread.join()返回
        t1.join();
        t2.join();
        System.out.println(i);
        System.out.println("输出数据："+i);
    }
} 