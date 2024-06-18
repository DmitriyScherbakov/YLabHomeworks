package org.example.Authentification;

import lombok.RequiredArgsConstructor;
import org.example.Entity.AuditRecord;
import org.example.Input.InputManager;
import org.example.Repository.AuditService;
import org.example.Entity.User;
import org.example.Services.UserService;

/**
 * Класс Registration предоставляет методы для регистрации новых пользователей и создания администратора.
 */

@RequiredArgsConstructor
public class Registration {

    private final UserService userService;

    private InputManager inputManager;
    AuditService auditService = new AuditService();

    /**
     Создает новый объект Registration. Инициализирует внутренний объект inputManager переданым аргументом.
     */
    public Registration(InputManager inputManager, UserService userService) {
        this.inputManager = inputManager;
        this.userService = userService;
    }

    /**
     * Регистрирует нового пользователя.
     * Пользователь вводит свое имя пользователя и пароль, после чего они добавляются в список пользователей.
     * Если пользователь с таким именем уже существует, выводится сообщение об ошибке.
     */
    public void registerUser() {
        System.out.println("Registration new user:");
        String username = this.inputManager.readString("Input user name: ");
        if (userService.findUserByUsername(username).isEmpty()) {
            String password = inputManager.readString("Input password: ");
            // Создаем нового пользователя
            User user = new User(username, password, 1); // 1 - обычный пользователь
            // Сохраняем пользователя в базе данных
            userService.addUser(user);
            auditService.addAuditRecord(new AuditRecord(user.getId(), "registration", "currentTimestamp"));
            System.out.println("Registration success.");
        } else {
            System.out.println("A user with that name already exists.");
        }

    }

    /**
     * Создает администратора с именем "admin" и паролем "admin".
     */
    public void createAdmin() {
        String adminUsername = "admin";
        if (userService.findUserByUsername(adminUsername).isEmpty()) {
            User user = new User(adminUsername, "admin", 2);
            userService.addUser(user);
            //System.out.println("Admin user created successfully.");
        } else {
            System.out.println("Admin user already exists.");
        }
    }

}
