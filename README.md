# BkpMyPictures (Work in progress)
Utility tool to perform picture backups without override anytime

# Build

    $ mvn clean install
    
# Usage

        usage: BkpMyPictures
         -f,--from <arg>     Path origin where are locate the pictures
         -h,--height <arg>   Expected height of th picture. Default are 2000
                             pixels
         -t,--type <arg>     Type of picture to find. Default is JPG
         -to,--to <arg>      Path which you want to copy the pictures
         -w,--width <arg>    Expected width of th picture. Default are 2000 pixels
         
        TYPE: JPG as default
        
         Example: copy all file located under you home Picture dir to the target dir BKP in you home dir
         
        $ java -jar target/BkpMyPictures-1.0-SNAPSHOT-jar-with-dependencies.jar -f=Pictures -to=BKP -t=JPG

