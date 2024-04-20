package org.example.Service.Entity;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class UserTest {
    @Test
    void testConstructorWithStatus() {
        User user = new User("username", "password", 1);

        assertEquals("username", user.getUsername());
        assertEquals("password", user.getPassword());
        assertEquals(1, user.getStatus());
        assertNotNull(user.getWorkoutList());
        assertTrue(user.getWorkoutList().isEmpty());
    }

    @Test
    void testConstructorWithoutStatus() {
        User user = new User("username", "password");

        assertEquals("username", user.getUsername());
        assertEquals("password", user.getPassword());
        assertEquals(0, user.getStatus());
        assertNotNull(user.getWorkoutList());
        assertTrue(user.getWorkoutList().isEmpty());
    }

    @Test
    void testSettersAndGetters() {
        User user = new User("username", "password");

        user.setUsername("newUsername");
        user.setPassword("newPassword");
        user.setStatus(2);

        assertEquals("newUsername", user.getUsername());
        assertEquals("newPassword", user.getPassword());
        assertEquals(2, user.getStatus());
    }

    @Test
    void testAddWorkout() {
        User user = new User("username", "password");
        Workout workout = mock(Workout.class);

        user.addWorkout(workout);

        List<Workout> workoutList = user.getWorkoutList();
        assertEquals(1, workoutList.size());
        assertTrue(workoutList.contains(workout));
    }

}