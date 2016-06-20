package utils.configuration;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

/**
 * Created by apryakhin on 20.06.2016.
 */
public class Config {

    private HashMap<String, String> map_of_configurations;
    private static Config configuration_instance;

    private Config(){
        map_of_configurations = new HashMap<String, String>();
    }

    /**
     * singleton method
     * @return Config
     */
    public static Config i(){
        if(configuration_instance == null){
            synchronized (Config.class){
                if(configuration_instance == null){
                    configuration_instance = new Config();
                }
            }
        }

        return configuration_instance;
    }

    public boolean addConfig(String configuration_file_name){
        boolean result = false;

        if(map_of_configurations.containsKey(configuration_file_name)){
            System.out.println("Configuration file " + configuration_file_name + " has been already included");
        }
        else {
            ConfigReader cr = new ConfigReader(configuration_file_name);

            map_of_configurations.putAll(cr.readConfig());
            result = true;
        }

        return result;
    }

    /**
     * @param key : key name for lookup
     * @return String
     */
    public String getConfigurationMapItem(String key){
        String item;
        item = map_of_configurations.get(key);

        return item;
    }
}