package com.cbaeza;

import com.cbaeza.filters.JpgAndDirectoryFilter;
import org.junit.Test;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by cbaeza on 19/02/2017.
 */
public class DirectoryAnalyzerTest {
    @Test
    public void analyze() throws Exception {
        Path path = FileSystems.getDefault().getPath(System.getProperty("user.home") + "/Pictures");
        DirectoryAnalyzer directoryAnalyzer = new DirectoryAnalyzer(path, new JpgAndDirectoryFilter());
        assertNotNull(directoryAnalyzer);
        List files = directoryAnalyzer.getFiles();
        System.out.println("************************");
        System.out.println(files.size() + " Files found.");
    }

}