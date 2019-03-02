package com.cbaeza.analyzer;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

public abstract class AbstractDirectoryAnalizer {
  private static final Logger LOG = LoggerFactory.getLogger(AbstractDirectoryAnalizer.class);
  protected long totalFileSize = 0L;
  private MathContext mathContext = new MathContext(2);
  private static final int ONE_MB_IN_BYTES = 1048576;
  private static final int ONE_GB_IN_BYTES = 1073741824;

  protected List<Path> files = new ArrayList<>();

  public List<Path> getRelevantFiles() {
    return files;
  }

  protected void printMeta(final Path entry) {
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

  public void printSummary() {
    LOG.info("************************");
    for (Path path : files) {
      LOG.info(path.toString());
    }
    LOG.info(files.size() + " Files found.");
    LOG.info(totalFileSize + " Bytes");
    LOG.info(BigDecimal.valueOf(totalFileSize).divide(BigDecimal.valueOf(ONE_MB_IN_BYTES), mathContext) + " MB");
    LOG.info(BigDecimal.valueOf(totalFileSize).divide(BigDecimal.valueOf(ONE_GB_IN_BYTES), mathContext) + " GB");
  }
}
