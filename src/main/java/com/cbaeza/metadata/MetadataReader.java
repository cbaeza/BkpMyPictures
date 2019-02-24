package com.cbaeza.metadata;

import java.io.File;
import java.nio.file.Path;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Read metadata of file.
 */
public class MetadataReader {

  private static final Logger LOG = LoggerFactory.getLogger(MetadataReader.class);

  private final Path filePath;
  private int numLines;
  private int samplesPerLine;

  public MetadataReader(Path filePath) {
    this.filePath = filePath;
    readMetadata();
  }

  private void readMetadata() {
    try {
      File file = filePath.toFile();
      ImageInputStream iis = ImageIO.createImageInputStream(file);
      Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);

      if (readers.hasNext()) {

        // pick the first available ImageReader
        ImageReader reader = readers.next();

        // attach source to the reader
        reader.setInput(iis, true);

        // read metadata of first image
        IIOMetadata metadata = reader.getImageMetadata(0);

        String[] names = metadata.getMetadataFormatNames();
        int length = names.length;
        for (String name : names) {
          LOG.info("Format name: " + name);
          displayMetadata(metadata.getAsTree(name));
        }
      }
    } catch (Exception e) {
      LOG.error("Error {}", e);
    }
  }

  private void displayMetadata(Node root) {
    displayMetadata(root, 0);
  }

  private void indent(int level) {
    for (int i = 0; i < level; i++)
      LOG.info("    ");
  }

  private void displayMetadata(Node node, int level) {
    // print open tag of element
    indent(level);
    LOG.info("<" + node.getNodeName());
    NamedNodeMap map = node.getAttributes();
    if (map != null) {

      // print attribute values
      int length = map.getLength();
      for (int i = 0; i < length; i++) {
        Node attr = map.item(i);
        LOG.info(" " + attr.getNodeName() + "=\"" + attr.getNodeValue() + "\"");
      }
    }

    Node child = node.getFirstChild();
    if (child == null) {
      // no children, so close element and return
      LOG.info("/>");
      return;
    }

    // children, so close current tag
    LOG.info(">");
    while (child != null) {
      // print children recursively
      displayMetadata(child, level + 1);
      child = child.getNextSibling();
    }

    // print close tag of element
    indent(level);
    LOG.info("</" + node.getNodeName() + ">");
  }
}
