package utils;

import utils.configuration.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by apryakhin on 17.06.2016.
 */
public class DataBase {
    private String connectionString;
    private Connection connection;
    private static volatile DataBase instance;

    private DataBase(){
        try {
            Config c = Config.i();
            c.addConfig(getClass().getSimpleName().toLowerCase());

            String dbhost = c.getConfigurationMapItem("db.host");
            String dbport = c.getConfigurationMapItem("db.port");
            String dbname = c.getConfigurationMapItem("db.name");
            String dbpass = c.getConfigurationMapItem("db.pass");
            String dbuser = c.getConfigurationMapItem("db.user");

            connectionString = "jdbc:mysql://" + dbhost + ":" + dbport + "/" + dbname + "?" + dbuser + "=" + dbpass;
            connection = DriverManager.getConnection(connectionString);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * main singleton method
     * @return
     */
    public static DataBase i(){
        if(instance == null){
            synchronized (DataBase.class){
                if(instance == null){
                    instance = new DataBase();
                }
            }
        }

        return instance;
    }

    public Connection getConnection(){
        return connection;
    }
}
