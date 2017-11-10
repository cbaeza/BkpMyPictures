package com.cbaeza;

import com.cbaeza.filters.JpgAndDirectoryFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = App.class)
@DirtiesContext
public class DirectoryAnalyzerTest {

    @Test
    public void analyzeAndCopy() throws Exception {
        Path path = FileSystems.getDefault().getPath(System.getProperty("user.home") + "/Pictures");
        DirectoryAnalyzer directoryAnalyzer = new DirectoryAnalyzer(path, new JpgAndDirectoryFilter(), false);
        assertNotNull(directoryAnalyzer);
        List<Path> files = directoryAnalyzer.getRelevantFiles();

        /*
        // copy all relevant files
        int i = 0;
        for (Path source : files) {
            System.out.println(source);
            Files.copy(source,
                    FileSystems.getDefault().getPath(System.getProperty("user.home") + "/ALL_PICTURES/" + "IMG_" + i + ".jpg"),
                    StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING);
            i++;
        } // for
        */
        System.out.println("************************");
        System.out.println(files.size() + " Files found.");
        System.out.println(directoryAnalyzer.getAcumulateFileSizeInBytes() + " Bytes");
        System.out.println(directoryAnalyzer.getAcumulateFileSizeInMegaBytes() + " MB");
        System.out.println(directoryAnalyzer.getAcumulateFileSizeInGigaBytes() + " GB");
    }

}