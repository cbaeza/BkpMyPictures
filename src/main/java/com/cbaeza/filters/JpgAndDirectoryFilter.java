package com.cbaeza.filters;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

import org.apache.commons.io.FilenameUtils;

/**
 * Filter for jpg format
 */
public class JpgAndDirectoryFilter implements DirectoryStream.Filter<Path> {

  public static final String JPG = "jpg";

  @Override
  public boolean accept(Path entry) throws IOException {
    BasicFileAttributes basicFileAttributes = Files.readAttributes(entry, BasicFileAttributes.class);
    if (basicFileAttributes.isDirectory()) {
      return true;
    }
    String extension = FilenameUtils.getExtension(entry.getFileName().toString());
    return (extension.equalsIgnoreCase(JPG));
  }
}
