package org.example.Repository;

import org.example.Database.DBConnection;
import org.example.Entity.TrainingType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class WorkoutTypeRepositoryImpl implements WorkoutTypeRepository{


    @Override
    public void addNewWorkoutType(TrainingType trainingType) {
        String sql = "INSERT INTO training_diary.training_types (name) VALUES (?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, trainingType.getType());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding new workout type: " + e.getMessage());
        }
    }

    @Override
    public Set<TrainingType> getWorkoutTypes() {
        Set<TrainingType> workoutTypes = new HashSet<>();
        String sql = "SELECT * FROM training_diary.training_types";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                TrainingType trainingType = new TrainingType(id, name);
                workoutTypes.add(trainingType);
            }
        } catch (SQLException e) {
            System.out.println("Error getting workout types: " + e.getMessage());
        }
        return workoutTypes;
    }
}
