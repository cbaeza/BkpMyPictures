package com.cbaeza;

import com.cbaeza.filters.JpgAndDirectoryFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = App.class)
@DirtiesContext
public class DirectoryAnalyzerTest {
    @Test
    public void analyze() throws Exception {
        Path path = FileSystems.getDefault().getPath(ClassLoader.getSystemResource("pictures").getPath());
        //Path path = FileSystems.getDefault().getPath(System.getProperty("user.home") + "/Pictures");
        DirectoryAnalyzer directoryAnalyzer = new DirectoryAnalyzer(path, new JpgAndDirectoryFilter(), false);
        assertNotNull(directoryAnalyzer);
        List files = directoryAnalyzer.getRelevantFiles();

        for(Path entry: directoryAnalyzer.getRelevantFiles()){
            System.out.println(entry);
        }

//        for(Path entry: directoryAnalyzer.getRelevantFiles()){
//
//            if(Files.isDirectory(entry)){
//                continue;
//            }
//            System.out.println("###########################");
//            System.out.println(entry.getFileName());
//            System.out.println("###########################");
//            Metadata metadata = ImageMetadataReader.readMetadata(entry.toFile());
//            for (Directory directory : metadata.getDirectories()) {
//                for (Tag tag : directory.getTags()) {
//                    System.out.format("[%s] - %s = %s",
//                            directory.getName(), tag.getTagName(), tag.getDescription());
//                    System.out.println();
//                }
//                if (directory.hasErrors()) {
//                    for (String error : directory.getErrors()) {
//                        System.err.format("ERROR: %s", error);
//                    }
//                }
//            }// for
//        }
        System.out.println("************************");
        System.out.println(files.size() + " Files found.");
        System.out.println(directoryAnalyzer.getAcumulateFileSizeInBytes() + " Bytes");
        System.out.println(directoryAnalyzer.getAcumulateFileSizeInMegaBytes() + " MB");
        System.out.println(directoryAnalyzer.getAcumulateFileSizeInGigaBytes() + " GB");
    }

}