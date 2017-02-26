package com.cbaeza;

import com.cbaeza.filters.JpgAndDirectoryFilter;
import com.cbaeza.metadata.MetadataReader;
import org.junit.Test;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by cbaeza on 19/02/2017.
 */
public class DirectoryAnalyzerTest {
    @Test
    public void analyze() throws Exception {
        Path path = FileSystems.getDefault().getPath(ClassLoader.getSystemResource("pictures").getPath());
        DirectoryAnalyzer directoryAnalyzer = new DirectoryAnalyzer(path, new JpgAndDirectoryFilter());
        assertNotNull(directoryAnalyzer);
        List files = directoryAnalyzer.getFiles();
        System.out.println("************************");
        System.out.println(files.size() + " Files found.");

        for(Path entry: directoryAnalyzer.getFiles()){
            if(!Files.isDirectory(entry)){
                MetadataReader metadataReader = new MetadataReader(entry);
            }
            System.out.println("###############################################");
        }
    }

}