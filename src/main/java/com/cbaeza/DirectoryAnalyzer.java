package com.cbaeza;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

/**
 * Analyzer and get information about files into path
 */
public class DirectoryAnalyzer {

  private static final Logger LOG = LoggerFactory.getLogger(DirectoryAnalyzer.class);

  private static final int DEFAULT_HEIGHT = 2000;
  private static final int DEFAULT_WIDTH = 2000;
  private static final int ONE_MB_IN_BYTES = 1048576;
  private static final int ONE_GB_IN_BYTES = 1073741824;
  private final Path directoryPath;
  private final DirectoryStream.Filter filter;
  private long totalFileSize = 0L;
  private MathContext mathContext = new MathContext(2);
  private List<Path> files = new ArrayList<>();
  private boolean printMetadata;
  private int expectedWidth;
  private int expectedHeight;

  public DirectoryAnalyzer(
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

  public DirectoryAnalyzer(
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

  private void printMeta(final Path entry) {
    LOG.info("***************************************");
    LOG.info(entry.getFileName().toString());
    LOG.info("***************************************");
    Metadata metadata;
    try {
      metadata = ImageMetadataReader.readMetadata(entry.toFile());
      for (Directory directory : metadata.getDirectories()) {
        for (Tag tag : directory.getTags()) {
          LOG.info("[%s] - %s = %s",
              directory.getName(), tag.getTagName(), tag.getDescription());
        }
        if (directory.hasErrors()) {
          for (String error : directory.getErrors()) {
            LOG.error("ERROR: %s", error);
          }
        }
      } // for
    } catch (ImageProcessingException | IOException e) {
      LOG.error("Error {}", e);
    }
  }

  public List<Path> getRelevantFiles() {
    return files;
  }

  public long getTotalFileSize() {
    return totalFileSize;
  }

  public BigDecimal getTotalFileSizeInMegaBytes() {
    return BigDecimal.valueOf(totalFileSize).divide(BigDecimal.valueOf(ONE_MB_IN_BYTES), mathContext);
  }

  public BigDecimal getTotalFileSizeInGigaBytes() {
    return BigDecimal.valueOf(totalFileSize).divide(BigDecimal.valueOf(ONE_GB_IN_BYTES), mathContext);
  }

}
