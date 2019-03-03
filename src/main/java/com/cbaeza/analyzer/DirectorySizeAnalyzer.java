package com.cbaeza.analyzer;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DirectorySizeAnalyzer extends AbstractDirectoryAnalizer {
  private static final Logger LOG = LoggerFactory.getLogger(DirectoryResolutionAnalyzer.class);

  private final Path directoryPath;
  private final DirectoryStream.Filter filter;
  private final boolean printMetadata;
  private long nimSizeInBytes = 1000000; // 1 MB

  public DirectorySizeAnalyzer(
      Path directoryPath,
      DirectoryStream.Filter filter,
      boolean printMetadata) {
    this.directoryPath = directoryPath;
    this.filter = filter;
    this.printMetadata = printMetadata;
    analyze(directoryPath);
  }

  public DirectorySizeAnalyzer(
      Path directoryPath,
      DirectoryStream.Filter filter,
      boolean printMetadata,
      long nimSizeInBytes) {
    this.directoryPath = directoryPath;
    this.filter = filter;
    this.printMetadata = printMetadata;
    this.nimSizeInBytes = nimSizeInBytes;
    analyze(directoryPath);
  }

  @SuppressWarnings("unchecked")
  private void analyze(
      final Path path) {
    LOG.info("Visiting: " + path.toString());
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(path, filter)) {
      for (Path entry : stream) {
        if (Files.isDirectory(entry)) {
          analyze(entry);
        }
        File file = entry.toFile();
        if (isFileRelevant(file)) {
          LOG.info("File relevant: " + path.toString());
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
      final File file) {
    return file.length() >= nimSizeInBytes;
  }

}
