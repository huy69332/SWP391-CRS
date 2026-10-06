package com.crs.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBContext {
    
    private static final String DB_NAME = "Rental_Car";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/" + DB_NAME + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Ho_Chi_Minh";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "123";
    
    protected Connection connection;
    
    public DBContext() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(
                    "MySQL Connector/J is not available to the application. Ensure its JAR is in WEB-INF/lib.",
                    e);
        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Unable to connect to Rental_Car at localhost:3306. Check that MySQL is running, "
                    + "the database exists, and the configured credentials are valid.",
                    e);
        }
    }
    
    public Connection getConnection() {
        return connection;
    }
    
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}