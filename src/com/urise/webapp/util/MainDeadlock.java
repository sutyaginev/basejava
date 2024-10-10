package com.urise.webapp.util;

public class MainDeadlock {

    public static void main(String[] args) {
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
}
