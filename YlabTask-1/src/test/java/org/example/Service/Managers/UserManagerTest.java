package org.example.Service.Managers;

import org.example.Input.InputManager;
import org.example.Service.Entity.User;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserManagerTest {
    @Test
    void testGetUser() {
        // Подготовка
        Map<String, User> users = new HashMap<>();
        User user = new User("testUser", "password", 1);
        users.put("testUser", user);
        InputManager inputManager = mock(InputManager.class);

        UserManager userManager = new UserManager(users, inputManager);

        // Действие
        User retrievedUser = userManager.getUser("testUser");

        // Проверка
        assertEquals(user, retrievedUser);
    }

    @Test
    void testGetUserInfo() {
        // Подготовка
        InputManager inputManager = mock(InputManager.class);
        when(inputManager.readString("Enter user username: ")).thenReturn("testUser");
        when(inputManager.readString("Enter user password: ")).thenReturn("password");
        when(inputManager.readIntegerFromInput("Enter user status: ")).thenReturn(1);

        UserManager userManager = new UserManager(new HashMap<>(), inputManager);

        // Действие
        User userInfo = userManager.getUserInfo();

        // Проверка
        assertEquals("testUser", userInfo.getUsername());
        assertEquals("password", userInfo.getPassword());
        assertEquals(1, userInfo.getStatus());
    }

    @Test
    void testAddUser() {
        // Подготовка
        Map<String, User> users = new HashMap<>();
        InputManager inputManager = mock(InputManager.class);
        when(inputManager.readString("Enter user username: ")).thenReturn("testUser");
        when(inputManager.readString("Enter user password: ")).thenReturn("password");
        when(inputManager.readIntegerFromInput("Enter user status: ")).thenReturn(1);

        UserManager userManager = new UserManager(users, inputManager);

        // Действие
        userManager.addUser();

        // Проверка
        assertTrue(users.containsKey("testUser"));
    }
}