package com.show.tree.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private final String propertyFile = "resources/application.properties";
    private Connection connection;


    private DBConnection() {
    }

    public Connection getConnection()  {
        if (connection != null) {
            return connection;
        }
        try {
            Properties prop = extracted();
            String user = prop.getProperty("user");
            String password = prop.getProperty("password");
            String url = prop.getProperty("url");
            Class.forName("com.postgresql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (IOException e ){

        } catch (SQLException e ){

        } catch (ClassNotFoundException e ){

        }
        return connection;
    }

    private Properties extracted() throws IOException {
        Properties prop = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertyFile);
        if (inputStream != null) {
            prop.load(inputStream);
        } else {
            throw new FileNotFoundException("property file '" + propertyFile + "' not found in the classpath");
        }
        return prop;
    }

    private void destroy() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
