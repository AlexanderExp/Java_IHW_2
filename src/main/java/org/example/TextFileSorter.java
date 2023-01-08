package org.example;

import java.io.*;
import java.util.*;

public class TextFileSorter {
    private final List<File> _txtFilesList;
    private Dictionary<File, List<File>> _fileDependencies;
    TextFileSorter(List<File> txtFilesList) {
        _txtFilesList = txtFilesList;
        try {
            fillFileDependencies();
        } catch (IOException e) {
            System.out.println("\nFile was not found\n");
            throw new RuntimeException(e);
        }
    }
    private void fillFileDependencies() throws IOException {
            _fileDependencies = new Hashtable<>();
            // Проходим по всем файлам и заполняем словарь, где ключ - файл,
            // значение - список (List<File>) тех файлов, от которых зависит ключ-файл
            for (File file: _txtFilesList) {
                _fileDependencies.put(file, new ArrayList<>());
                BufferedReader fileReader = new BufferedReader(new FileReader(file));
                boolean reachedEndOfFile = false;
                String currentLineInFile = "";
                String requirementInCurrentLine = "";
                // Пока не дошли до конца файла, ищем зависимости
                while (!reachedEndOfFile) {
                    currentLineInFile = fileReader.readLine();
                    if (currentLineInFile == null) {
                        reachedEndOfFile = true;
                    }
                    if (!reachedEndOfFile) {
                        if (currentLineInFile.contains("require ")) {
                            int beginningOfFIlePath = currentLineInFile.indexOf("‘");
                            int endOfFilePath = currentLineInFile.lastIndexOf("’");
                            requirementInCurrentLine = currentLineInFile.substring(beginningOfFIlePath, endOfFilePath).trim();
                            _fileDependencies.get(file).add(new File(requirementInCurrentLine));
                        }
                    }
                }
            }
    }
    public File[] sortFiles() {
        File[] sortedFileArray = new File[_fileDependencies.size()];
        int numberOfElementsInSortedArray = 0;
        for (File file : _txtFilesList) {
            if (_fileDependencies.get(file).size() == 0) {
                if (numberOfElementsInSortedArray != 0) {
                    for (int j = numberOfElementsInSortedArray; j > 0; j--) {
                        sortedFileArray[j] = sortedFileArray[j - 1];
                    }
                }
                sortedFileArray[0] = file;
            } else {
                boolean fileWasInsertedInSortedArray = false;
                for (int i = 0; i < numberOfElementsInSortedArray; i++) {
                    if (_fileDependencies.get(sortedFileArray[i]).contains(file)) {
                        for (int j = numberOfElementsInSortedArray; j > i; j--) {
                            sortedFileArray[j] = sortedFileArray[j - 1];
                        }
                        sortedFileArray[i] = file;
                        fileWasInsertedInSortedArray = true;
                        break;
                    }
                }
                if (!fileWasInsertedInSortedArray) {
                    sortedFileArray[numberOfElementsInSortedArray] = file;
                }
            }
            ++numberOfElementsInSortedArray;
        }
        return sortedFileArray;
    }
}
