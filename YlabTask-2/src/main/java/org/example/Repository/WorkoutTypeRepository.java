package org.example.Repository;

import org.example.Entity.TrainingType;

import java.util.Set;

public interface WorkoutTypeRepository {

    void addNewWorkoutType(TrainingType trainingType);

    Set<TrainingType> getWorkoutTypes();
}
