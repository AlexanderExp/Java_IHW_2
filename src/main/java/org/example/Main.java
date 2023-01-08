package org.example;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        boolean userWantsToTryAgain = true;
        boolean everythingWentSmoothly = true;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (userWantsToTryAgain) {
            everythingWentSmoothly = startProcess();
            System.out.println("\nIf you want to try another file enter \"1\" else enter anything else:\n");
            String continueOrNot = null;
            try {
                continueOrNot = reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            userWantsToTryAgain = continueOrNot.equals("1");
        }
    }

    public static boolean startProcess() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите полный путь корневой папки: ");
        String rootFile = null;
        while (rootFile == null) {
            rootFile = getRootFileName();
        }
        TextFileSearcher searcher = new TextFileSearcher(rootFile);
        if (!searcher.searchTextFiles()) {
            return false;
        }
        searcher.sortTextFiles();
        //File result = searcher.makeResultingFile();

        return true;
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

