package com.example.java_or_kotlin_library;


/**
 * FileName: AccountingSync
 * Author: nanzong
 * Date: 2023/3/7 15:31
 * Description:
 * History:
 */
public class AccountingSync implements Runnable{
    //共享资源(临界资源)
    static int i = 0;
    /**
     * synchronized 修饰实例方法
     */
    public synchronized void increate(){
        i++;
    }

    @Override
    public void run() {
        for (int j = 0; j < 100000; j++) {
            increate();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AccountingSync accountingSync = new AccountingSync();
        Thread t1 = new Thread(accountingSync);
        Thread t2 = new Thread(accountingSync);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("输出数据："+i);
    }

}


