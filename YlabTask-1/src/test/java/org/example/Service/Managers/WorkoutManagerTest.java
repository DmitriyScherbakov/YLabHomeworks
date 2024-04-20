package org.example.Service.Managers;

import org.example.Input.InputManager;
import org.example.Service.Entity.User;
import org.example.Service.Entity.Workout;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.*;

class WorkoutManagerTest {
    @Mock
    private User mockUser;

    @Mock
    private InputManager mockInputManager;

    private WorkoutManager workoutManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        workoutManager = new WorkoutManager(mockUser);
        workoutManager.inputManager = mockInputManager;
    }

    @Test
    void addWorkoutTest() throws ParseException {
        Date date = new Date();
        when(mockInputManager.readDateFromInput("Enter the workout date (dd.MM.yyyy): ")).thenReturn(date);
        when(mockInputManager.readWorkoutTypeFromInput(anySet())).thenReturn("Running");
        when(mockInputManager.readIntegerFromInput("Enter the workout duration (in minutes): ")).thenReturn(30);
        when(mockInputManager.readIntegerFromInput("Enter the calories burned: ")).thenReturn(200);

        workoutManager.addWorkout();

        verify(mockInputManager).readDateFromInput("Enter the workout date (dd.MM.yyyy): ");
        verify(mockInputManager).readWorkoutTypeFromInput(anySet());
        verify(mockInputManager).readIntegerFromInput("Enter the workout duration (in minutes): ");
        verify(mockInputManager).readIntegerFromInput("Enter the calories burned: ");
        verify(mockUser).addWorkout(any(Workout.class));
    }

    @Test
    void displayWorkoutsTest() {
        List<Workout> workouts = new ArrayList<>();
        workouts.add(new Workout(new Date(), "Running", 30, 200, null));
        when(mockUser.getWorkoutList()).thenReturn(workouts);

        workoutManager.displayWorkouts();

        verify(mockUser).getWorkoutList();
    }

}