package org.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TextFileSearcher {
    private String _rootFile;
    private List<File> txtFilesList;
    private File result;
    TextFileSearcher(String rootFile) {
        _rootFile = rootFile;
        txtFilesList = new ArrayList<>();
        result = new File(rootFile + "\\resultingFile.txt");
    }
}
