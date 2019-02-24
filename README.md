# BkpMyPictures (Work in progress)
Utility tool to perform picture backups without override anytime

# Build

    $ mvn clean install
    
# Usage

        Usage:
            --from=ORIGIN_PATH --to=TARGET_PATH --type=TYPE
         
        TYPE: JPG as default
        
         Example: copy all file located under you home Picture dir to the target dir BKP in you home dir
         
        $ java -jar target/BkpMyPictures-1.0-SNAPSHOT-jar-with-dependencies.jar --from=Pictures --to=BKP --type=JPG

    
# Current supported filters
JPG as default
