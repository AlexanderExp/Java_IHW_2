package org.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TextFileSearcher {
    private final File _rootFile;
    private final List<File> _txtFilesList;

    /**
     * constructor for TextFileSearcher
     * @param rootFile path to root file
     */
    TextFileSearcher(String rootFile) {
        _rootFile = new File(rootFile);
        _txtFilesList = new ArrayList<>();
    }

    /**
     * Searches for text files
     * @return true if files were found& Else - false
     */
    public boolean searchTextFiles() {
        searchRecursive(_rootFile);
        if (_txtFilesList.size() == 0) {
            System.out.println("There are no text files in your root file");
            return false;
        }
        return true;
    }

    /**
     * Recursive searching for text files
     * @param currentFile needed for recursion& Current file
     */
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

    /**
     * returns list of files that were found
     * @return list of files that were found
     */
    public List<File> getTxtFiles() {
        return _txtFilesList;
    }

    /**
     * returns root file
     * @return root file
     */
    public File getRootFile() {
        return _rootFile;
    }
}

