package com.cbaeza.filters;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;

/**
 * read all
 */
public class DefaultFilter implements DirectoryStream.Filter<Path> {
    @Override
    public boolean accept(Path entry) throws IOException {
        return true;
    }
}
