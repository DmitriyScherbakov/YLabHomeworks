package org.example.Service.Managers;

import org.example.Input.InputManager;
import org.example.Service.Entity.User;

import java.util.Iterator;
import java.util.Map;

/**
 * Класс UserManager отвечает за управление пользователями системы, включая их добавление, получение,
 * обновление статуса и удаление.
 */
public class UserManager {

    private Map<String, User> users; // Коллекция пользователей, где ключ - это имя пользователя, а значение - объект User.
    private InputManager inputManager; // Менеджер ввода для взаимодействия с пользователем.

    /**
     * Конструктор класса UserManager.
     * @param users Коллекция пользователей.
     * @param inputManager Менеджер ввода.
     */
    public UserManager(Map<String, User> users, InputManager inputManager) {
        this.users = users;
        this.inputManager = inputManager;
    }

    /**
     * Получает пользователя по его имени.
     * @param username Имя пользователя для поиска.
     * @return Объект пользователя, если найден, иначе null.
     */
    public User getUser(String username) {
        return (User)this.users.get(username);
    }

    /**
     * Получает информацию о пользователе от пользователя через ввод.
     * @return Объект пользователя с введенными данными.
     */
    public User getUserInfo() {
        String username = this.inputManager.readString("Enter user username: ");
        String password = this.inputManager.readString("Enter user password: ");
        int status = this.inputManager.readIntegerFromInput("Enter user status: ");
        return new User(username, password, status);
    }

    /**
     * Добавляет нового пользователя в систему.
     */
    public void addUser() {
        User user = this.getUserInfo();
        this.users.put(user.getUsername(), user);
    }

    /**
     * Отображает список пользователей и их статусы.
     */
    public void displayUsers() {
        System.out.println("User List:");
        if (this.users.isEmpty()) {
            System.out.println("No users available.");
        } else {
            Iterator var1 = this.users.entrySet().iterator();

            while(var1.hasNext()) {
                Map.Entry<String, User> entry = (Map.Entry)var1.next();
                String username = (String)entry.getKey();
                int status = ((User)entry.getValue()).getStatus();
                System.out.println(username + " - Status: " + status);
            }
        }

    }

    /**
     * Обновляет статус пользователя.
     * @param username Имя пользователя.
     * @param newStatus Новый статус пользователя.
     */
    public void updateUserStatus(String username, int newStatus) {
        this.displayUsers();
        User userToUpdate = this.getUser(username);
        if (userToUpdate == null) {
            System.out.println("User not found.");
        } else if (userToUpdate.getStatus() >= 2) {
            System.out.println("Cannot update status of administrator.");
        } else {
            userToUpdate.setStatus(newStatus);
            AuditLog.logAction("User " + username + " status updated to " + newStatus);
        }
    }

    /**
     * Удаляет пользователя из системы.
     * @param username Имя пользователя для удаления.
     */
    public void deleteUser(String username) {
        User userToDelete = this.getUser(username);
        if (userToDelete == null) {
            System.out.println("User not found.");
        } else if (userToDelete.getStatus() >= 2) {
            System.out.println("Cannot update status of administrator.");
        } else {
            this.users.remove(username);
            AuditLog.logAction("User " + username + " deleted.");
        }
    }
}
