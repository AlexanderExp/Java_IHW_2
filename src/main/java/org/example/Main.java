package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    /**
     * main method of the program
     * @param args
     */
    public static void main(String[] args) {
        boolean userWantsToTryAgain = true;
        boolean everythingWentSmoothly;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (userWantsToTryAgain) {
            everythingWentSmoothly = startProcess();
            if (!everythingWentSmoothly) {
                System.out.println("Something went wrong !\n");
            }
            System.out.println("\nIf you want to try another file enter \"1\" else enter anything else:\n");
            String continueOrNot;
            try {
                continueOrNot = reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            userWantsToTryAgain = continueOrNot.equals("1");
        }
        System.out.println("""
                Thank you for trying out my code !
                Bye ! Have a great day !
                """);
    }

    /**
     * Starts the work of all main features. Needed to simplify the process of reloading the work of program
     * @return true if everything is fine. Else - false
     */
    public static boolean startProcess() {
        System.out.println("Введите полный путь корневой папки: ");
        String rootFile = null;
        while (rootFile == null) {
            rootFile = getRootFileName();
        }
        TextFileSearcher searcher = new TextFileSearcher(rootFile);
        if (!searcher.searchTextFiles()) {
            return false;
        }
        List<File> txtFiles = searcher.getTxtFiles();
        File rootFileFromSearcher = searcher.getRootFile();
        TextFileSorter sorter = new TextFileSorter(txtFiles, rootFileFromSearcher);
        File[] sortedFiles = sorter.sortFiles();
        File resultingFile = new File(rootFile + "/resultingFile.txt");
        try {
            concatenateSortedFilesInOneResultingFile(sortedFiles, resultingFile);
        } catch (IOException e) {
            System.out.println("""
                    Was unable to create resulting file. Please check you root path and try to change it
                    Thank you !
                    """);
            throw new RuntimeException(e);
        }
        return true;
    }

    /**
     * Reads input and gets name of root file& Checks if the name is correct
     * @return name of rootFile from input
     */
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

    /**
     * Concatenates all text from sorted array of files
     * @param sortedFiles A sorted array of files that we need to concatenate
     * @param resultingFile The file for final output
     * @throws IOException In case of bad file names or any problems with files
     */
    public static void concatenateSortedFilesInOneResultingFile(File[] sortedFiles, File resultingFile) throws IOException {
        BufferedReader fileReader;
        BufferedWriter fileWriter;
        if (!resultingFile.exists()) {
            Path pathToResultingFile = Paths.get(resultingFile.getName());
            Files.createFile(pathToResultingFile);
        }
        try {
            fileWriter = new BufferedWriter(new FileWriter(resultingFile));
        } catch (IOException e) {
            System.out.println("""
                    Was unable to create resulting file. Please check you root path and try to change it
                    Thank you !
                    """);
            throw new RuntimeException(e);
        }
        String currentString;
        for (File file : sortedFiles) {
            fileReader = new BufferedReader(new FileReader(file));
            boolean reachedEndOfFile = false;
            while (!reachedEndOfFile) {
                currentString = fileReader.readLine();
                if (currentString == null) {
                    reachedEndOfFile = true;
                }
                if (!reachedEndOfFile) {
                    fileWriter.write(currentString + "\n");
                }
            }
        }
        fileWriter.close();
    }
}

