package org.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TextFileSearcher {
    private final File _rootFile;
    private final List<File> _txtFilesList;
    TextFileSearcher(String rootFile) {
        _rootFile = new File(rootFile);
        _txtFilesList = new ArrayList<>();
    }
    public boolean searchTextFiles() {
        searchRecursive(_rootFile);
        if (_txtFilesList.size() == 0) {
            System.out.println("There are no text files in your root file");
            return false;
        }
        return true;
    }
    private void searchRecursive(File currentFile) {
        if (currentFile.isDirectory()) {
            for (File file : Objects.requireNonNull(currentFile.listFiles())) {
                if (file.isFile() && (file.getName().endsWith(".txt") || file.getName().endsWith(".TXT"))) {
                    _txtFilesList.add(file);
                } else {
                    searchRecursive(file);
                }
            }
        }
    }
    public List<File> getTxtFiles() {
        return _txtFilesList;
    }
    public File getRootFile() {
        return _rootFile;
    }
}

