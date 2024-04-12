package org.example.Service.Authentification;

import org.example.Input.InputManager;
import org.example.Service.Entity.User;
import org.example.Service.Managers.AuditLog;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс Registration предоставляет методы для регистрации новых пользователей и создания администратора.
 */
public class Registration {
    private Map<String, User> users = new HashMap();
    private InputManager inputManager;

    /**
     Создает новый объект Registration. Инициализирует внутренний объект inputManager переданым аргументом.
     */
    public Registration(InputManager inputManager) {
        this.inputManager = inputManager;
    }

    /**
     * Регистрирует нового пользователя.
     * Пользователь вводит свое имя пользователя и пароль, после чего они добавляются в список пользователей.
     * Если пользователь с таким именем уже существует, выводится сообщение об ошибке.
     */
    public void registerUser() {
        System.out.println("Registration new user:");
        String username = this.inputManager.readString("Input user name: ");
        if (!this.users.containsKey(username)) {
            String password = this.inputManager.readString("Input password: ");
            this.users.put(username, new User(username, password, 1));
            AuditLog.logAction("New user registered: " + username);
            System.out.println("Registration success.");
        } else {
            System.out.println("A user with that name already exists.");
        }

    }

    /**
     * Создает администратора с именем "admin" и паролем "admin".
     */
    public void createAdmin() {
        this.users.put("admin", new User("admin", "admin", 2));
    }

    /**
     * Метод возвращает копию хранилища, чтобы избежать прямого изменения внутренних данных класса.
     */
    public Map<String, User> getUsers() {
        return this.users;
    }
}
