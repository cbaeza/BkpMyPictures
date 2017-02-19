package com.cbaeza;

import com.cbaeza.filters.DefaultFilter;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Analyzer and get information about files into path
 */
public class DirectoryAnalyzer {

    private final Path directoryPath;
    private final DirectoryStream.Filter filter;
    private List files = new ArrayList();

    public DirectoryAnalyzer(Path directoryPath, DirectoryStream.Filter filter) {
        this.directoryPath = directoryPath;
        this.filter = filter;
        init();
    }

    public DirectoryAnalyzer(Path directoryPath) {
        this.directoryPath = directoryPath;
        this.filter = new DefaultFilter();
        init();
    }

    private void init(){
        analyze(directoryPath);
    }

    private List analyze(Path path) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path, filter)) {
            for( Path entry: stream){
                System.out.println(entry.getFileName());
                if (Files.isDirectory(entry)) {
                    analyze(entry);
                }
                files.add(entry);
            }
        } catch (DirectoryIteratorException | IOException e) {
            e.printStackTrace();
        }
        return files;
    }

    public Path getDirectoryPath() {
        return directoryPath;
    }

    public List getFiles() {
        return files;
    }
}
