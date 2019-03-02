package com.cbaeza;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.cbaeza.analyzer.DirectoryResolutionAnalyzer;
import com.cbaeza.analyzer.DirectorySizeAnalyzer;
import com.cbaeza.filters.JpgAndDirectoryFilter;

public class Main {

  public static void main(String[] args) throws IOException {
    Options options = createOptions();
    CommandLineParser parser = new DefaultParser();
    HelpFormatter formatter = new HelpFormatter();
    try {
      CommandLine cmd = parser.parse(options, args);
      String from = cmd.getOptionValue("from");
      String to = cmd.getOptionValue("to");
      String strategy = cmd.getOptionValue("s");
      String copy = cmd.getOptionValue("c");

      // RESOLUTION
      if (from != null && to != null && "RESOLUTION".equalsIgnoreCase(strategy)) {
        String pictureType = cmd.getOptionValue("pT");
        String width = cmd.getOptionValue("width");
        String height = cmd.getOptionValue("height");
        if ("JPG".equalsIgnoreCase(pictureType) || "JPEG".equalsIgnoreCase(pictureType)) {
          handleJpeg(from, to, width, height, copy);
        } else {
          System.out.println("Type: " + pictureType + " not supported");
        }
      }

      // SIZE
      if (from != null && to != null && "SIZE".equalsIgnoreCase(strategy)) {
        String pictureType = cmd.getOptionValue("pT");
        try {
          long size = Long.valueOf(cmd.getOptionValue("size"));
          if ("JPG".equalsIgnoreCase(pictureType) || "JPEG".equalsIgnoreCase(pictureType)) {
            handleJpegWithSize(from, to, size, copy);
          } else {
            System.out.println("Type: " + pictureType + " not supported");
          }
        } catch (NumberFormatException e) {
          System.out.println("Size invalid. Please enter number format");
        }
      }

    } catch (ParseException e) {
      System.out.println(e.getMessage());
      formatter.printHelp("BkpMyPictures", options);
      System.exit(1);
    }
  }

  private static void handleJpegWithSize(String from, String to, long size, String copy) {
    System.out.println(" from: " + from);
    System.out.println(" to: " + to);
    System.out.println(" size: " + size);
    Path path = FileSystems.getDefault().getPath(from);
    DirectorySizeAnalyzer directorySizeAnalyzer = new DirectorySizeAnalyzer(path,
        new JpgAndDirectoryFilter(), false, size);
    directorySizeAnalyzer.printSummary();
    if ("TRUE".equalsIgnoreCase(copy)) {
      directorySizeAnalyzer.copyFiles(to);
    }
  }

  private static void handleJpeg(String from, String to, String width, String height, String copy) {
    if (width != null && height != null) {
      copyJpegFiles(from, to, Integer.valueOf(width), Integer.valueOf(height), copy);
    } else {
      copyJpegFiles(from, to, 2000, 2000, copy);
    }
  }

  private static void copyJpegFiles(String from, String to, int width, int height, String copy) {
    System.out.println(" from: " + from);
    System.out.println(" to: " + to);
    System.out.println(" width: " + width);
    System.out.println(" height: " + height);
    Path path = FileSystems.getDefault().getPath(from);
    DirectoryResolutionAnalyzer directoryResolutionAnalyzer = new DirectoryResolutionAnalyzer(path,
        new JpgAndDirectoryFilter(), width, height,
        false);
    directoryResolutionAnalyzer.printSummary();
    if ("TRUE".equalsIgnoreCase(copy)) {
      directoryResolutionAnalyzer.copyFiles(to);
    }
  }

  private static Options createOptions() {
    Options options = new Options();

    Option from = new Option("f", "from", true, "Absolute path origin where are locate the pictures");
    from.setRequired(true);
    options.addOption(from);

    Option to = new Option("to", "to", true, "Absolute path which you want to copy the pictures");
    to.setRequired(true);
    options.addOption(to);

    Option type = new Option("s", "strategy", true, "Type of strategy to use. Supported: SIZE, RESOLUTION");
    type.setRequired(true);
    options.addOption(type);

    Option width = new Option("w", "width", true,
        "Expected minimum width of the picture. Default value is 2000 pixels");
    width.setRequired(false);
    options.addOption(width);

    Option height = new Option("h", "height", true,
        "Expected minimum height of the picture. Default value is 2000 pixels");
    height.setRequired(false);
    options.addOption(height);

    Option pictureType = new Option("pT", "pictureType", true,
        "Type of picture to find. Default value is JPG/JPEG");
    pictureType.setRequired(false);
    options.addOption(pictureType);

    Option size = new Option("size", "size", true,
        "Minimal size of the picture to retrieve in bytes");
    size.setRequired(false);
    options.addOption(size);

    Option copy = new Option("c", "copy", true,
        "Confirm copy files. Set TRUE");
    copy.setRequired(false);
    options.addOption(copy);

    return options;
  }
}
