package com.cbaeza;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cbaeza.filters.JpgAndDirectoryFilter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = App.class)
@DirtiesContext
public class DirectoryAnalyzerTest {

  private static final Logger LOG = LoggerFactory.getLogger(DirectoryAnalyzerTest.class);

  @Test
  public void analyzeAndCopy() throws IOException {
    Path path = FileSystems.getDefault().getPath(System.getProperty("user.home") + "/Pictures/test");
    DirectoryAnalyzer directoryAnalyzer = new DirectoryAnalyzer(path, new JpgAndDirectoryFilter(), false);
    assertNotNull(directoryAnalyzer);
    List<Path> files = directoryAnalyzer.getRelevantFiles();
   // displayAndCopy(files);

    LOG.info("************************");
    LOG.info(files.size() + " Files found.");
    LOG.info(directoryAnalyzer.getAcumulateFileSizeInBytes() + " Bytes");
    LOG.info(directoryAnalyzer.getAcumulateFileSizeInMegaBytes() + " MB");
    LOG.info(directoryAnalyzer.getAcumulateFileSizeInGigaBytes() + " GB");
  }

  private void displayAndCopy(List<Path> files) {
    // copy all relevant files
    int i = 0;
    for (Path source : files) {
      LOG.info(source.toString());
      /*Files.copy(source,
          FileSystems.getDefault().getPath("/Volumes/Data-Timecapsule/ALL_PICTURES/" +
              "IMG_" + i + ".jpg"),
          StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING);*/
      i++;
    } // for
  }

}