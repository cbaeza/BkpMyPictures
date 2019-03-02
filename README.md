# BkpMyPictures (Work in progress)
Utility tool to perform picture backups without override anytime

# Build

    $ mvn clean install
    
# Usage

       usage: BkpMyPictures
       
        -f,--from <arg>           Path origin where are locate the pictures
        -h,--height <arg>         Expected minimum height of the picture. Default
                                  value is 2000 pixels
        -pT,--pictureType <arg>   Type of picture to find. Default value is
                                  JPG/JPEG
        -s,--strategy <arg>       Type of strategy to use. Supported: SIZE,
                                  RESOLUTION
        -size,--size <arg>        Minimal size of the picture to retrieve in
                                  bytes
        -to,--to <arg>            Path which you want to copy the pictures
        -w,--width <arg>          Expected minimum width of the picture. Default
                                  value is 2000 pixels


         
  ## Example 
  
  copy all file located under you home Picture/test dir to the target dir BKP
         
        $ java -jar target/BkpMyPictures-1.0-SNAPSHOT-jar-with-dependencies.jar -f Pictures/test -to BKP -t JPG
        
  copy all file located under you home Picture/test dir to the target dir BKP with size at least 1 MB
        
        java -jar target/BkpMyPictures-1.0-SNAPSHOT-jar-with-dependencies.jar -f Pictures/test -to BKP -s SIZE -size 1000000 -pT JPG


