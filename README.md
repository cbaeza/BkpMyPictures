# BkpMyPictures (Work in progress)
Utility tool to find out and extract pictures into directories and copy this pictures to another directory. Useful for example to extract all you pictures from Apple Photo Library directory discarding automatic thumb images produced by this software.

# Build

    $ mvn clean install
    
# Usage

         ____   _            __  __         ____   _        _
        | __ ) | | __ _ __  |  \/  | _   _ |  _ \ (_)  ___ | |_  _   _  _ __   ___  ___
        |  _ \ | |/ /| '_ \ | |\/| || | | || |_) || | / __|| __|| | | || '__| / _ \/ __|
        | |_) ||   < | |_) || |  | || |_| ||  __/ | || (__ | |_ | |_| || |   |  __/\__ \
        |____/ |_|\_\| .__/ |_|  |_| \__, ||_|    |_| \___| \__| \__,_||_|    \___||___/
                     |_|             |___/
                     
       usage: BkpMyPictures
       
        -c,--copy <arg>           Confirm copy files. Set TRUE
        -f,--from <arg>           Absolute path origin where are locate the
                                  pictures
        -h,--height <arg>         Expected minimum height of the picture. Default
                                  value is 2000 pixels
        -pT,--pictureType <arg>   Type of picture to find. Default value is
                                  JPG/JPEG
        -s,--strategy <arg>       Type of strategy to use. Supported: SIZE,
                                  RESOLUTION
        -size,--size <arg>        Minimal size of the picture to retrieve in
                                  bytes
        -to,--to <arg>            Absolute path which you want to copy the
                                  pictures
        -w,--width <arg>          Expected minimum width of the picture. Default
                                  value is 2000 pixels
         
  ## Example 
  
  copy all file located under you home Picture/test dir to the target dir BKP
         
        $ java -jar target/BkpMyPictures-1.0-SNAPSHOT-jar-with-dependencies.jar -f /home/user/Pictures/test -to /home/user/BKP -t JPG -c TRUE
        
  copy all file located under you home Picture/test dir to the target dir BKP with size at least 1 MB
        
        $ java -jar target/BkpMyPictures-1.0-SNAPSHOT-jar-with-dependencies.jar -f /home/user/Pictures/test -to /home/user/BKP -s SIZE -size 1000000 -pT JPG -c TRUE


