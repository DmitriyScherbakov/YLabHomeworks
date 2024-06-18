package org.example.Services;

import lombok.RequiredArgsConstructor;
import org.example.Input.InputManager;
import org.example.Repository.WorkoutTypeRepositoryImpl;
import org.example.Entity.TrainingType;
import org.example.Repository.WorkoutTypeRepository;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class WorkoutTypeService {

    WorkoutTypeRepository workoutTypeRepository = new WorkoutTypeRepositoryImpl();
    InputManager inputManager = new InputManager();

    public void createNewWorkoutType() {
        String newType = inputManager.readString("Enter new training type: ");
        Set<TrainingType> workoutTypes = workoutTypeRepository.getWorkoutTypes();
        boolean typeExists = false;
        for (TrainingType type : workoutTypes) {
            if (type.getType().equalsIgnoreCase(newType)) {
                typeExists = true;
                break;
            }
        }
        if (!typeExists) {
            // Создание нового экземпляра TrainingType с указанием имени
            TrainingType newTrainingType = new TrainingType();
            newTrainingType.setType(newType); // Установка имени нового типа тренировки
            workoutTypeRepository.addNewWorkoutType(newTrainingType);
            System.out.println("New training type \"" + newType + "\" added.");
        } else {
            System.out.println("Training type already exists.");
        }
    }

    public void showWorkoutTypes() {
        Set<TrainingType> workoutTypes = workoutTypeRepository.getWorkoutTypes();
        if (workoutTypes.isEmpty()) {
            System.out.println("No workout types found.");
        } else {
            System.out.println("Available workout types:");
            for (TrainingType type : workoutTypes) {
                System.out.println(type.getType());
            }
        }
    }

    public Set<String> getWorkoutTypes() {
        Set<TrainingType> workoutTypes = workoutTypeRepository.getWorkoutTypes();
        Set<String> typeNames = new HashSet<>();
        for (TrainingType type : workoutTypes) {
            typeNames.add(type.getType());
        }
        return typeNames;
    }
}
