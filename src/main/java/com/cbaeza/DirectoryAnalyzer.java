package com.cbaeza;

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

import com.cbaeza.filters.DefaultFilter;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

/**
 * Analyzer and get information about files into path
 */
public class DirectoryAnalyzer {

  public static final int ONE_MB_IN_BYTES = 1048576;
  public static final int ONE_GB_IN_BYTES = 1073741824;
  private static final String MAKE_NIKON = "NIKON";
  private static final String MAKE_APPLE = "APPLE";
  private static final String MODEL_IPHONE = "iPhone";
  private static final String DIRECTORY_APPLE_MAKERNOTE = "Apple Makernote";
  private static final String DIRECTORY_NIKON_MAKERNOTE = "Nikon Makernote";
  private final Path directoryPath;
  private final DirectoryStream.Filter filter;
  private long acumulateFileSizeInBytes = 0l;
  private MathContext mathContext = new MathContext(2);
  private List<Path> files = new ArrayList<>();
  private boolean printMetadata = false;

  public DirectoryAnalyzer(Path directoryPath, DirectoryStream.Filter filter, boolean printMetadata) {
    this.directoryPath = directoryPath;
    this.filter = filter;
    this.printMetadata = printMetadata;
    init();
  }

  public DirectoryAnalyzer(Path directoryPath, DirectoryStream.Filter filter) {
    this.directoryPath = directoryPath;
    this.filter = filter;
    init();
  }

  public DirectoryAnalyzer(Path directoryPath) {
    this.directoryPath = directoryPath;
    this.filter = new DefaultFilter();
    init();
  }

  private void init() {
    analyze(directoryPath);
  }

  private List<Path> analyze(Path path) {
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(path, filter)) {
      for (Path entry : stream) {
        if (Files.isDirectory(entry)) {
          analyze(entry);
        }
        if (isRelevant(entry)) {
          files.add(entry);
          if (printMetadata) {
            printMeta(entry);
          }
        }
      }
    } catch (DirectoryIteratorException | IOException e) {
      e.printStackTrace();
    }
    return files;
  }

  private boolean isRelevant(Path entry) {
    try {
      File file = entry.toFile();
      Metadata metadata = ImageMetadataReader.readMetadata(file);
      if (hasDirectoryRelevantMetadata(metadata)) {
        acumulateFileSizeInBytes += file.length();
        return true;
      }
      // for (Tag tag : directory.getTags()) {
      // if (tag.getTagName().equals("Make") && containSearchParameters(tag,
      // metadata)) {
      // acumulateFileSizeInBytes += file.length();
      // return true;
      // }
      // }
      // for (Directory directory : metadata.getDirectories()) {
      // }// for
    } catch (ImageProcessingException | IOException e) {
      return false;
    }
    return false;
  }

  private boolean hasDirectoryRelevantMetadata(Metadata metadata) {
    for (Directory directory : metadata.getDirectories()) {
      String name = directory.getName();
      if (name.contains(DIRECTORY_APPLE_MAKERNOTE) || name.contains(DIRECTORY_NIKON_MAKERNOTE)) {
        return true;
      }
    }
    return false;
  }

  private void printMeta(Path entry) {
    System.out.println("***************************************");
    System.out.println(entry.getFileName());
    System.out.println("***************************************");
    Metadata metadata;
    try {
      metadata = ImageMetadataReader.readMetadata(entry.toFile());
      for (Directory directory : metadata.getDirectories()) {
        for (Tag tag : directory.getTags()) {
          System.out.format("[%s] - %s = %s",
              directory.getName(), tag.getTagName(), tag.getDescription());
          System.out.println();
        }
        if (directory.hasErrors()) {
          for (String error : directory.getErrors()) {
            System.err.format("ERROR: %s", error);
          }
        }
      } // for
    } catch (ImageProcessingException | IOException e) {
    }
  }

  private boolean containSearchParameters(Tag tag, Metadata metadata) {
    String description = tag.getDescription().toUpperCase();
    boolean makeRelevant = description.contains(MAKE_APPLE) || description.contains(MAKE_NIKON);
    boolean directoryRelevant = hasDirectoryRelevantMetadata(metadata);
    return makeRelevant && directoryRelevant;
  }

  public Path getDirectoryPath() {
    return directoryPath;
  }

  public List<Path> getRelevantFiles() {
    return files;
  }

  public long getAcumulateFileSizeInBytes() {
    return acumulateFileSizeInBytes;
  }

  public BigDecimal getAcumulateFileSizeInMegaBytes() {
    return BigDecimal.valueOf(acumulateFileSizeInBytes).divide(BigDecimal.valueOf(ONE_MB_IN_BYTES), mathContext);
  }

  public BigDecimal getAcumulateFileSizeInGigaBytes() {
    return BigDecimal.valueOf(acumulateFileSizeInBytes).divide(BigDecimal.valueOf(ONE_GB_IN_BYTES), mathContext);
  }

}
