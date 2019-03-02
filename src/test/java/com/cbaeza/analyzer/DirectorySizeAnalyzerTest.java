package com.cbaeza.analyzer;

import static org.junit.Assert.assertNotNull;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;

import org.junit.Test;

import com.cbaeza.filters.JpgAndDirectoryFilter;

public class DirectorySizeAnalyzerTest {

  @Test
  public void analyze() {
    Path path = FileSystems.getDefault().getPath(System.getProperty("user.home") + "/Pictures");
    DirectorySizeAnalyzer directorySizeAnalyzer = new DirectorySizeAnalyzer(path, new JpgAndDirectoryFilter(), false);
    assertNotNull(directorySizeAnalyzer);
    List<Path> files = directorySizeAnalyzer.getRelevantFiles();
    assertNotNull(files);
    directorySizeAnalyzer.printSummary();
  }

}