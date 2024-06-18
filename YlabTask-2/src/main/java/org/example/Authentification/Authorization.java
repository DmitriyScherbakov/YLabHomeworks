package org.example.Authentification;

import lombok.RequiredArgsConstructor;
import org.example.Entity.AuditRecord;
import org.example.Repository.AuditService;
import org.example.Entity.User;
import org.example.Services.UserService;

import java.util.Optional;

/**
 * Класс Authorization предоставляет методы для авторизаии пользователей.
 */
@RequiredArgsConstructor
public class Authorization {

    private final UserService userService;
    AuditService auditService = new AuditService();

    /**
     * Аутентификация пользователя по его имени пользователя и паролю.
     * @param username Имя пользователя.
     * @param password Пароль пользователя.
     * @return Объект типа User, если аутентификация успешна, или null,
     * если введены неверное имя пользователя или пароль.
     */
    public Optional<User> authenticate(String username, String password) {
        Optional<User> authenticatedUser = userService.userAuthorization(username, password);
        if (authenticatedUser.isPresent()) {
            System.out.println("Authorization is successful.");
            auditService.addAuditRecord(new AuditRecord(authenticatedUser.get().getId(), "login", "currentTimestamp"));
            return authenticatedUser;
        } else {
            System.out.println("Invalid username or password.");
            return Optional.empty();
        }
    }

}
