package com.urise.webapp;

import java.io.File;

public class MainRecursion {

    public static void main(String[] args) {
        File dir = new File("./");

        printFiles(dir, "  ", 0);

    }

    public static void printFiles(File dir, String tabSign, int count) {
        System.out.println(tabSign.repeat(count) + "\uD83D\uDCC1" + dir.getName());
        count++;
        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isFile()) {
                System.out.println(tabSign.repeat(count) + "\uD83D\uDDCE" + file.getName());
            } else if (file.isDirectory()) {
                printFiles(file, tabSign, count);
            }
        }
    }
}

