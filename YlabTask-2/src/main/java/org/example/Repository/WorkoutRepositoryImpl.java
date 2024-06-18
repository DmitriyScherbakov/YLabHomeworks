package org.example.Repository;

import lombok.RequiredArgsConstructor;
import org.example.Input.InputManager;
import org.example.Database.DBConnection;
import org.example.Entity.User;
import org.example.Entity.Workout;
import org.example.Services.WorkoutTypeService;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
public class WorkoutRepositoryImpl implements WorkoutRepository{
    InputManager inputManager;
    WorkoutTypeService workoutTypeService;

    @Override
    public void save(Workout workout) {
        String sql = "INSERT INTO training_diary.workouts (date, type, duration_minutes, calories_burned, additional_info) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setDate(1, new java.sql.Date(workout.getDate().getTime()));
            statement.setString(2, workout.getType());
            statement.setInt(3, workout.getDurationMinutes());
            statement.setInt(4, workout.getCaloriesBurned());
            statement.setString(5, workout.getAdditionalInfo());
            statement.executeUpdate();

            // Получаем сгенерированный ключ (id созданной тренировки)
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int workoutId = generatedKeys.getInt(1);
                    // Сохраняем связь в таблицу user_workouts
                    saveUserWorkoutRelation(workout.getWhoCreatedId(), workoutId);
                } else {
                    throw new SQLException("Creating workout failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Метод для сохранения связи между пользователем и тренировкой в таблицу user_workouts
    private void saveUserWorkoutRelation(int userId, int workoutId) {
        String sql = "INSERT INTO training_diary.user_workouts (user_id, workout_id) VALUES (?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setInt(2, workoutId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Workout> getAllWorkouts(User user) {
        List<Workout> workouts = new ArrayList<>();

        String sql = "SELECT w.* FROM training_diary.workouts w " +
                "INNER JOIN training_diary.user_workouts uw ON w.id = uw.workout_id " +
                "WHERE uw.user_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    Date date = resultSet.getDate("date");
                    String type = resultSet.getString("type");
                    int durationMinutes = resultSet.getInt("duration_minutes");
                    int caloriesBurned = resultSet.getInt("calories_burned");
                    String additionalInfo = resultSet.getString("additional_info");
                    // Создаем объект тренировки и добавляем его в список
                    Workout workout = new Workout(id, date, type, durationMinutes, caloriesBurned, additionalInfo, user.getId());
                    workouts.add(workout);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return workouts;
    }

    @Override
    public void deleteWorkout(User user, int id) {
        // First, delete the references to this workout from the user_workouts table
        String deleteUserWorkoutSql = "DELETE FROM training_diary.user_workouts WHERE workout_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement deleteUserWorkoutStatement = connection.prepareStatement(deleteUserWorkoutSql)) {
            deleteUserWorkoutStatement.setInt(1, id);
            deleteUserWorkoutStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Then, delete the workout itself
        String deleteWorkoutSql = "DELETE FROM training_diary.workouts WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement deleteWorkoutStatement = connection.prepareStatement(deleteWorkoutSql)) {
            deleteWorkoutStatement.setInt(1, id);
            deleteWorkoutStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateWorkout(Workout updatedWorkout) {
        String sql = "UPDATE training_diary.workouts " +
                "SET date = ?, type = ?, duration_minutes = ?, calories_burned = ?, additional_info = ? " +
                "WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, new java.sql.Date(updatedWorkout.getDate().getTime()));
            statement.setString(2, updatedWorkout.getType());
            statement.setInt(3, updatedWorkout.getDurationMinutes());
            statement.setInt(4, updatedWorkout.getCaloriesBurned());
            statement.setString(5, updatedWorkout.getAdditionalInfo());
            statement.setInt(6, updatedWorkout.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void showStatistics(Date startDate, Date endDate) {
        String sql = "SELECT SUM(calories_burned) AS total_calories_burned FROM training_diary.workouts " +
                "WHERE date >= ? AND date <= ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, new java.sql.Date(startDate.getTime()));
            statement.setDate(2, new java.sql.Date(endDate.getTime()));
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int totalCaloriesBurned = resultSet.getInt("total_calories_burned");
                    System.out.println("Total calories burned between " + startDate + " and " + endDate + ": " + totalCaloriesBurned);
                } else {
                    System.out.println("No workouts found for the specified period.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
