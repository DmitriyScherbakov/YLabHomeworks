package org.example.Service.Entity;

import org.example.Service.Managers.WorkoutTypeManager;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WorkoutTest {
    @Test
    void testConstructor() {
        Date date = new Date();
        WorkoutTypeManager typeManager = mock(WorkoutTypeManager.class);

        Workout workout = new Workout(date, "Running", 30, 200, typeManager);

        assertEquals(date, workout.getDate());
        assertEquals("Running", workout.getType());
        assertEquals(30, workout.getDurationMinutes());
        assertEquals(200, workout.getCaloriesBurned());
        assertNotNull(workout.getAdditionalInfo());
        assertTrue(workout.getAdditionalInfo().isEmpty());
    }

    @Test
    void testSetTypeWithValidType() {
        Workout workout = new Workout();
        WorkoutTypeManager typeManager = mock(WorkoutTypeManager.class);
        when(typeManager.containsWorkoutType("Running")).thenReturn(true);

        workout.setType("Running");

        assertEquals("Running", workout.getType());
    }

    @Test
    void testSetTypeWithInvalidType() {
        Workout workout = new Workout();
        WorkoutTypeManager typeManager = mock(WorkoutTypeManager.class);
        when(typeManager.containsWorkoutType("InvalidType")).thenReturn(false);

        workout.setType("InvalidType");

        assertNull(workout.getType());
    }

    @Test
    void testAddAdditionalInfo() {
        Workout workout = new Workout();
        workout.setAdditionalInfo(new HashMap<>());

        workout.addAdditionalInfo("Location", "Gym");

        assertEquals(1, workout.getAdditionalInfo().size());
        assertEquals("Gym", workout.getAdditionalInfo().get("Location"));
    }
}