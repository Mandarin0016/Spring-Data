package org.example.orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MyConnector {
    private static Connection connection;
    private static final String jdbcConnectionString = "jdbc:mysql://localhost:3306/";

    private MyConnector() {
    }

    public static void createConnection(String username, String password, String dbName) throws SQLException {
        if (connection != null) {
            return;
        }

        Properties properties = new Properties();
        properties.setProperty("user", username);
        properties.setProperty("password", password);

        connection = DriverManager.getConnection(jdbcConnectionString + dbName, properties);
    }

    public static Connection getConnection() {
        return connection;
    }
}
