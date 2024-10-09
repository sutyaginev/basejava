package com.urise.webapp;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {

    public static final int THREADS_NUMBER = 10000;
    private  int counter;
    private static final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());

        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
                throw new IllegalStateException();
            }
        };
        thread0.start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
        }).start();

        System.out.println(thread0.getState());

        final MainConcurrency mainConcurrency = new MainConcurrency();
        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);
        
        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
            });
            thread.start();
            threads.add(thread);
        }

        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println(mainConcurrency.counter);

        // deadlock
        int[] array1 = new int[]{1, 2, 3};
        int[] array2 = new int[]{4, 5, 6};
        createThread(array1, array2).start();
        createThread(array2, array1).start();
    }

    private static Thread createThread(Object o1, Object o2) {
        return new Thread(() -> {
            synchronized (o1) {
                System.out.println(o1 + " is holding");

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                System.out.println("Waiting for " + o2);
                synchronized (o2) {
                    System.out.println(o2 + " is holding");
                }
            }
        });
    }

    private void inc() {
//        synchronized (this) {
//        synchronized (MainConcurrency.class) {

        counter++;
//                wait();
//                readFile
//                ...
    }

//        synchronized (LOCK) {
//            counter++;
//        }

//    private static synchronized void inc() {
//          double a = Math.sin(13);
//          counter++;
//    }
}

