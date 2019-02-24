package com.cbaeza;

import com.cbaeza.filters.JpgAndDirectoryFilter;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class Main {

  public static void main(String[] args) throws IOException {
    // write your code here
    if(args.length < 3 ){
      displayHelp();
      return;
    }

    String from = args[0];
    String to = args[1];
    if( from != null && from.contains("--from=") && to != null && to.contains("--to=")){
      copyJpegFiles(from.replace("--from=",""), to.replace("--to=",""));
    }
  }

  private static void copyJpegFiles(String from, String to) throws IOException {
    System.out.println(" from: " + from);
    System.out.println(" to: " + to);
    Path path = FileSystems.getDefault().getPath(System.getProperty("user.home") + "/" + from);
    DirectoryAnalyzer directoryAnalyzer = new DirectoryAnalyzer(path, new JpgAndDirectoryFilter(), false);
    List<Path> files = directoryAnalyzer.getRelevantFiles();

    // copy all relevant files
    int i = 0;
    for (Path source : files) {
      System.out.println(source.toString());
      /*Files.copy(source,
          FileSystems.getDefault().getPath( to + "/" +
              "IMG_" + i + ".jpg"),
          StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING); */
      i++;
    } // for
    System.out.println("************************");
    System.out.println(files.size() + " Files found.");
    System.out.println(directoryAnalyzer.getAcumulateFileSizeInBytes() + " Bytes");
    System.out.println(directoryAnalyzer.getAcumulateFileSizeInMegaBytes() + " MB");
    System.out.println(directoryAnalyzer.getAcumulateFileSizeInGigaBytes() + " GB");
    System.out.println("************************");
  }

  private static void displayHelp() {
    System.out.println("-------------------------------------------------------");
    System.out.println(" Usage:");
    System.out.println("    --from=ORIGIN_PATH --to=TARGET_PATH --type=TYPE");
    System.out.println(" ");
    System.out.println(" TYPE: JPG as default");
    System.out.println(" Example: copy all file located under you home Picture dir to the target dir BKP in you home dir");
    System.out.println(" ");
    System.out.println(" java -jar target/BkpMyPictures-1.0-SNAPSHOT-jar-with-dependencies.jar --from=Pictures --to=BKP --type=JPG");
    System.out.println(" ");
  }
}
