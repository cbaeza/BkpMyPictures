package com.cbaeza.filters;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

import org.apache.commons.io.FilenameUtils;

/**
 * @author GK Software AG
 */
public class MovFilter implements DirectoryStream.Filter<Path> {
  private static final String MOV = "mov";

  @Override
  public boolean accept(Path entry) throws IOException {
    BasicFileAttributes basicFileAttributes = Files.readAttributes(entry, BasicFileAttributes.class);
    if (basicFileAttributes.isDirectory()) {
      return true;
    }
    String extension = FilenameUtils.getExtension(entry.getFileName().toString());
    return (extension.equalsIgnoreCase(MOV));
  }
}
