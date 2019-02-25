package com.cbaeza;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

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
      if (from != null && to != null) {
        String type = cmd.getOptionValue("type");
        String width = cmd.getOptionValue("width");
        String height = cmd.getOptionValue("height");
        if ("JPG".equalsIgnoreCase(type) || "JPEG".equalsIgnoreCase(type)) {
          handleJpeg(from, to, width, height);
        } else {
          System.out.println("Type: " + type + " not supported");
        }
      }

    } catch (ParseException e) {
      System.out.println(e.getMessage());
      formatter.printHelp("BkpMyPictures", options);
      System.exit(1);
    }
  }

  private static void handleJpeg(String from, String to, String width, String height) throws IOException {
    if (width != null && height != null) {
      copyJpegFiles(from, to, Integer.valueOf(width), Integer.valueOf(height));
    } else {
      copyJpegFiles(from, to, 2000, 2000);
    }
  }

  private static Options createOptions() {
    Options options = new Options();

    Option from = new Option("f", "from", true, "Path origin where are locate the pictures");
    from.setRequired(true);
    options.addOption(from);

    Option to = new Option("to", "to", true, "Path which you want to copy the pictures");
    to.setRequired(true);
    options.addOption(to);

    Option type = new Option("t", "type", true, "Type of picture to find. Default is JPG");
    type.setRequired(false);
    options.addOption(type);

    Option width = new Option("w", "width", true, "Expected width of th picture. Default are 2000 pixels");
    width.setRequired(false);
    options.addOption(width);

    Option height = new Option("h", "height", true, "Expected height of th picture. Default are 2000 pixels");
    height.setRequired(false);
    options.addOption(height);

    return options;
  }

  private static void copyJpegFiles(String from, String to, int width, int height) throws IOException {
    System.out.println(" from: " + from);
    System.out.println(" to: " + to);
    System.out.println(" width: " + width);
    System.out.println(" height: " + height);
    Path path = FileSystems.getDefault().getPath(System.getProperty("user.home") + "/" + from);
    DirectoryAnalyzer directoryAnalyzer = new DirectoryAnalyzer(path, new JpgAndDirectoryFilter(), width, height,
        false);
    List<Path> files = directoryAnalyzer.getRelevantFiles();

    // copy all relevant files
    int i = 0;
    for (Path source : files) {
      System.out.println(source.toString());
      /*
       * Files.copy(source, FileSystems.getDefault().getPath( to + "/" + "IMG_" + i +
       * ".jpg"), StandardCopyOption.COPY_ATTRIBUTES,
       * StandardCopyOption.REPLACE_EXISTING);
       */
      i++;
    } // for
    System.out.println("************************");
    System.out.println(files.size() + " Files found.");
    System.out.println(directoryAnalyzer.getTotalFileSize() + " Bytes");
    System.out.println(directoryAnalyzer.getTotalFileSizeInMegaBytes() + " MB");
    System.out.println(directoryAnalyzer.getTotalFileSizeInGigaBytes() + " GB");
    System.out.println("************************");
  }
}
