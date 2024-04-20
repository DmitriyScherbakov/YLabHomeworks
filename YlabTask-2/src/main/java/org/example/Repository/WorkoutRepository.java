package org.example.Repository;

import org.example.Entity.User;
import org.example.Entity.Workout;

import java.util.Date;
import java.util.List;

public interface WorkoutRepository {

    void save(Workout workout);

    List<Workout> getAllWorkouts(User user);

    void deleteWorkout(User user, int id);

    void updateWorkout(Workout workout);

    void showStatistics(Date startDate, Date endDate);
}
