package utils.configuration;

import java.io.*;
import java.util.HashMap;

/**
 * Created by apryakhin on 20.06.2016.
 */
class ConfigReader {

    private HashMap<String, String> configurationMap;
    private String file_path;
    private String splitter = "=";

    ConfigReader(String config_name){
        try {
            String path = new File(".").getCanonicalPath();
            String FileSeparator = (String) System.getProperty("file.separator");

            StringBuilder sb = new StringBuilder();
            sb.append(path)
                    .append(FileSeparator).append("config").append(FileSeparator).append(config_name).append(".config");

            file_path = sb.toString();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    void setSplitter(String mySplitter){
        splitter = mySplitter;
    }

    /**
     * main method for parsing of configuration file
     */
    HashMap<String, String> readConfig(){
        try{
            FileReader reader;
            reader = new FileReader(file_path);
            BufferedReader br = new BufferedReader(reader);

            configurationMap = new HashMap<String, String>();

            for (String line = br.readLine(); line != null; line = br.readLine()) {
                // all lines started with # marked as comments
                if(!line.startsWith("#")){
                    String[] s = line.split(splitter);
                    if(s.length > 1)
                        configurationMap.put(s[0], s[1]);
                    else
                        configurationMap.put(s[0], "1");
                }
            }
        }
        catch(FileNotFoundException e){
            System.err.println("Config file wasn't found at " + file_path);
        }
        catch (IOException e){
            System.err.println("IOException " + e.getMessage());
        }

        return configurationMap;
    }
}
