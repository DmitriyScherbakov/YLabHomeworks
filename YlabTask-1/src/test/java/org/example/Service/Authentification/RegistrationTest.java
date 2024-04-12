package org.example.Service.Authentification;

import org.example.Input.InputManager;
import org.example.Service.Entity.User;
import org.example.Service.Managers.AuditLog;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegistrationTest {
    @Test
    void testRegisterUserSuccess() {
        InputManager inputManager = mock(InputManager.class);
        when(inputManager.readString("Input user name: ")).thenReturn("newuser");
        when(inputManager.readString("Input password: ")).thenReturn("password");

        Map<String, User> users = new HashMap<>();
        AuditLog auditLog = mock(AuditLog.class);

        Registration registration = new Registration(inputManager);

        registration.registerUser();

        assertTrue(registration.getUsers().containsKey("newuser"));
        verify(auditLog).logAction("New user registered: newuser");
        System.out.println("Registration success.");
    }

    @Test
    void testRegisterUserAlreadyExists() {

        InputManager inputManager = mock(InputManager.class);
        when(inputManager.readString("Input user name: ")).thenReturn("existinguser");

        Map<String, User> users = new HashMap<>();
        users.put("existinguser", new User("existinguser", "password", 1));

        AuditLog auditLog = mock(AuditLog.class);

        Registration registration = new Registration(inputManager);

        assertFalse(registration.getUsers().containsKey("existinguser"));
        System.out.println("A user with that name already exists.");

        verify(auditLog, never()).logAction(anyString());
    }
}