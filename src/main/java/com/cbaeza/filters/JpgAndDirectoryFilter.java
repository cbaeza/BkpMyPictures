package com.cbaeza.filters;

import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Filter for jpg format
 */
public class JpgAndDirectoryFilter implements DirectoryStream.Filter<Path> {
    @Override
    public boolean accept(Path entry) throws IOException {
        BasicFileAttributes basicFileAttributes = Files.readAttributes(entry, BasicFileAttributes.class);
        if (basicFileAttributes.isDirectory()) {
            return true;
        }
        String extension = FilenameUtils.getExtension(entry.getFileName().toString());
        if (extension != null) {
            return (extension.equalsIgnoreCase("jpg")) ? true : false;
        }
        return false;
    }
}
