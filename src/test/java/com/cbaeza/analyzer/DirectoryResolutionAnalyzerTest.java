package com.cbaeza.analyzer;

import static org.junit.Assert.assertNotNull;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;

import org.junit.Test;

import com.cbaeza.filters.JpgAndDirectoryFilter;

public class DirectoryResolutionAnalyzerTest {

  @Test
  public void analyze() {
    Path path = FileSystems.getDefault().getPath(System.getProperty("user.home") + "/Pictures/test");
    DirectoryResolutionAnalyzer directoryResolutionAnalyzer = new DirectoryResolutionAnalyzer(path,
        new JpgAndDirectoryFilter(), false);
    assertNotNull(directoryResolutionAnalyzer);
    List<Path> files = directoryResolutionAnalyzer.getRelevantFiles();
    assertNotNull(files);
    directoryResolutionAnalyzer.printSummary();
  }

}