package org.example.Service.Authentification;

import org.example.Service.Entity.User;
import org.example.Service.Managers.AuditLog;

import java.util.Map;

/**
 * Класс Authorization предоставляет методы для авторизаии пользователей.
 */
public class Authorization {

    private Map<String, User> users;

    /**
     * Создает объект Authorization с указанным набором пользователей.
     * @param users Map, содержащая пользователей и их имена в качестве ключей.
     */
    public Authorization(Map<String, User> users) {
        this.users = users;
    }

    /**
     * Поиск пользователя по его имени пользователя.
     * @param username Имя пользователя для поиска.
     * @return Объект типа User, соответствующий указанному имени пользователя, или null,
     * если пользователь не найден.
     */
    public User findUser(String username) {
        return (User)this.users.get(username);
    }

    /**
     * Аутентификация пользователя по его имени пользователя и паролю.
     * @param username Имя пользователя.
     * @param password Пароль пользователя.
     * @return Объект типа User, если аутентификация успешна, или null,
     * если введены неверное имя пользователя или пароль.
     */
    public User authenticate(String username, String password) {
        User user = this.findUser(username);
        if (user != null && user.getPassword().equals(password)) {
            System.out.println("Authorization is successful.");
            AuditLog.logAction("User " + username + " logged in.");
            return user;
        } else {
            System.out.println("Invalid username or password.");
            return null;
        }
    }
}
