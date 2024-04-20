package org.example.Database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    private static final String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS training_diary.users (" +
            "id SERIAL PRIMARY KEY," +
            "username VARCHAR(50) NOT NULL," +
            "password VARCHAR(50) NOT NULL," +
            "role INT NOT NULL" +
            ")";

    private static final String CREATE_WORKOUTS_TABLE = "CREATE TABLE IF NOT EXISTS training_diary.workouts (" +
            "id SERIAL PRIMARY KEY," +
            "date DATE NOT NULL," +
            "type VARCHAR(50) NOT NULL," +
            "duration_minutes INT NOT NULL," +
            "calories_burned INT NOT NULL," +
            "additional_info VARCHAR(100)" +
            ")";

    private static final String CREATE_USER_WORKOUTS_TABLE = "CREATE TABLE IF NOT EXISTS training_diary.user_workouts (" +
            "id SERIAL PRIMARY KEY," +
            "user_id INT NOT NULL," +
            "workout_id INT NOT NULL," +
            "FOREIGN KEY (user_id) REFERENCES training_diary.users(id)," +
            "FOREIGN KEY (workout_id) REFERENCES training_diary.workouts(id)" +
            ")";

    private static final String CREATE_TRAINING_TYPES_TABLE = "CREATE TABLE IF NOT EXISTS training_diary.training_types (" +
            "id SERIAL PRIMARY KEY," +
            "name VARCHAR(50) NOT NULL" +
            ")";

    private static final String CREATE_AUDIT_TABLE = "CREATE TABLE IF NOT EXISTS service_tables.audit (" +
            "id SERIAL PRIMARY KEY," +
            "user_id INT NOT NULL," +
            "action VARCHAR(100) NOT NULL," +
            "timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
            ")";

    public static void initializeDatabase() {
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_USERS_TABLE);
            statement.executeUpdate(CREATE_WORKOUTS_TABLE);
            statement.executeUpdate(CREATE_USER_WORKOUTS_TABLE);
            statement.executeUpdate(CREATE_TRAINING_TYPES_TABLE);
            statement.executeUpdate(CREATE_AUDIT_TABLE);
            //System.out.println("Tables created successfully.");
        } catch (SQLException e) {
            System.out.println("Error initializing database: " + e.getMessage());
        }
    }
}
