package com.cbaeza.analyzer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Analyzer and get information about files into path
 */
public class DirectoryResolutionAnalyzer extends AbstractDirectoryAnalizer {

  private static final Logger LOG = LoggerFactory.getLogger(DirectoryResolutionAnalyzer.class);

  private static final int DEFAULT_HEIGHT = 2000;
  private static final int DEFAULT_WIDTH = 2000;
  private final Path directoryPath;
  private final DirectoryStream.Filter filter;
  private boolean printMetadata;
  private int expectedWidth;
  private int expectedHeight;

  public DirectoryResolutionAnalyzer(
      Path directoryPath,
      DirectoryStream.Filter filter,
      boolean printMetadata) {
    this.directoryPath = directoryPath;
    this.filter = filter;
    this.printMetadata = printMetadata;
    this.expectedWidth = DEFAULT_WIDTH;
    this.expectedHeight = DEFAULT_HEIGHT;
    init();
  }

  public DirectoryResolutionAnalyzer(
      Path directoryPath,
      DirectoryStream.Filter filter,
      int expectedWidth,
      int expectedHeight,
      boolean printMetadata) {
    this.directoryPath = directoryPath;
    this.filter = filter;
    this.printMetadata = printMetadata;
    this.expectedWidth = expectedWidth;
    this.expectedHeight = expectedHeight;
    init();
  }

  private void init() {
    analyze(directoryPath, expectedHeight, expectedWidth);
  }

  @SuppressWarnings("unchecked")
  private void analyze(
      final Path path,
      final int expectedHeight,
      final int expectedWidth) {
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(path, filter)) {
      for (Path entry : stream) {
        if (Files.isDirectory(entry)) {
          analyze(entry, expectedHeight, expectedWidth);
        }
        File file = entry.toFile();
        if (isFileRelevant(file, expectedHeight, expectedWidth)) {
          files.add(entry);
          totalFileSize += file.length();
          if (printMetadata) {
            printMeta(entry);
          }
        }
      }
    } catch (DirectoryIteratorException | IOException e) {
      LOG.error("Error {}", e);
    }
  }

  private boolean isFileRelevant(
      final File file,
      final int expectedHeight,
      final int expectedWidth) {
    try {
      BufferedImage bufferedImage = ImageIO.read(file);
      int height = bufferedImage.getHeight();
      int width = bufferedImage.getWidth();
      return height >= expectedHeight && width >= expectedWidth;
    } catch (IOException e) {
      return false;
    }
  }

}
