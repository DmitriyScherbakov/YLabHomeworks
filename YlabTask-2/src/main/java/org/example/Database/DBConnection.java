package org.example.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/TrainingDiary";

    private static final String USER_NAME = "DmitriyScherbakov";

    private static final String PASSWORD = "scherbakov";

    public DBConnection(){
    }


    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            //System.out.println("The connection to the database is established.");
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
            throw e;
        }
        return connection;
    }
}
