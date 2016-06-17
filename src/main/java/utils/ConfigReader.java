package utils;

/**
 * Created by apryakhin on 17.06.2016.
 */

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ConfigReader {

    private HashMap<String, String> configurationMap;
    private String file_path;
    private String splitter = "=";

    public ConfigReader(String config_name){
        try {
            String path = new File(".").getCanonicalPath();
            System.out.println(path);
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

    public void setSplitter(String mySplitter){
        splitter = mySplitter;
    }

    /**
     * main method for parsing of configuration file
     */
    public void readConfig(){
        try{
            FileReader reader;
            reader = new FileReader(file_path);
            BufferedReader br = new BufferedReader(reader);

            configurationMap = new HashMap<String, String>();

            for (String line = br.readLine(); line != null; line = br.readLine()) {
                String[] s = line.split(splitter);
                if(s.length > 1)
                    configurationMap.put(s[0], s[1]);
                else
                    configurationMap.put(s[0], "1");
            }
        }
        catch(FileNotFoundException e){
            System.err.println("Config file wasn't found at " + file_path);
        }
        catch (IOException e){
            System.err.println("IOException " + e.getMessage());
        }
    }

    /**
     * create a key-value map for parsed values
     * @param key : key name for lookup
     * @return String
     */
    public String getConfigurationMapItem(String key){
        String item;
        item = configurationMap.get(key);

        return item;
    }

    public void printConfigurationValues() {
        for (Map.Entry<String, String> entry: configurationMap.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}
