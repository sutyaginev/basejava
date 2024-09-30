package com.urise.webapp;

import java.io.File;
import java.io.IOException;

public class MainFile {

    public static void main(String[] args) {
        String filePath = "./.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("./src/com/urise");
        System.out.println(dir.isDirectory());
        try {
            System.out.println(dir.getCanonicalPath());
            System.out.println(dir.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        printDirectoryDeeply(dir, "");

//        FileInputStream fis = null;
//        try {
//            fis = new FileInputStream(filePath);
//            System.out.println(fis.read());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } finally {
//            if (fis != null) {
//                try {
//                    fis.close();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }

//        try (FileInputStream fis =new FileInputStream(filePath)) {
//            System.out.println(fis.read());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    public static void printDirectoryDeeply(File dir, String tabSign) {
        File[] files = dir.listFiles();

        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isFile()) {
                System.out.println(tabSign + "\uD83D\uDDCE" + file.getName());
            } else if (file.isDirectory()) {
                System.out.println(tabSign + "\uD83D\uDCC1" + file.getName());
                printDirectoryDeeply(file, tabSign + "  ");
            }
        }
    }
}
