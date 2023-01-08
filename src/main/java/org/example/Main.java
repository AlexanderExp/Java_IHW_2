package org.example;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Введите полный путь корневой папки: ");
        String rootFile = null;
        while (rootFile == null) {
            rootFile = getRootFileName();
        }
        TextFileSearcher searcher = new TextFileSearcher(rootFile);
    }
    public static String getRootFileName() {
        String rootFile = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Please enter full path to root file: ");
            rootFile = reader.readLine().trim();
            File file = new File(rootFile);
            if (!file.exists()) {
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException exception) {
            System.out.println("Entered path does not lead to an existing file.\n" +
                    "Please try again ");
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Something went wrong.\n" +
                    "Please try again ");
        }
        return rootFile;
    }
}

