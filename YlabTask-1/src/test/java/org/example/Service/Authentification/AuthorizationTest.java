package org.example.Service.Authentification;

import org.example.Service.Entity.User;
import org.example.Service.Managers.AuditLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorizationTest {
    @Test
    void testAuthenticateValidUser() {
        Map<String, User> users = new HashMap<>();
        User validUser = new User("username", "password");
        users.put("username", validUser);

        AuditLog auditLog = mock(AuditLog.class);

        Authorization authorization = new Authorization(users);
        assertTrue(authorization.authenticate("username", "password") != null);
        verify(auditLog).logAction("User username logged in.");
    }

    @Test
    void testAuthenticateInvalidUser() {
        Map<String, User> users = new HashMap<>();
        users.put("username", new User("username", "password"));

        AuditLog auditLog = mock(AuditLog.class);

        Authorization authorization = new Authorization(users);

        assertNull(authorization.authenticate("username", "wrong_password"));
        verifyNoInteractions(auditLog);
    }
}
