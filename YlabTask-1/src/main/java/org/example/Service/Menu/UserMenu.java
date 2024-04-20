package org.example.Service.Menu;

import org.example.Input.InputManager;
import org.example.Service.Entity.User;
import org.example.Service.Managers.UserManager;

import java.util.Map;
import java.util.Objects;

/**
 * Класс UserMenu представляет меню управления пользователями, где администратор может
 * добавлять, просматривать, обновлять статус и удалять пользователей.
 */
public class UserMenu {

    private InputManager inputManager; // Менеджер ввода.
    private Map<String, User> users; // Карта пользователей.
    private UserManager userManager; // Менеджер пользователей.

    /**
     * Конструктор класса UserMenu.
     * @param inputManager Менеджер ввода.
     * @param users Карта пользователей.
     */
    public UserMenu(InputManager inputManager, Map<String, User> users) {
        this.inputManager = inputManager;
        this.users = users;
    }

    /**
     * Обрабатывает меню управления пользователями.
     */
    public void handleMenu() {
        userManager = new UserManager(users, inputManager);
        while (true) {
            displayMenu();
            int choice = inputManager.readIntegerFromInput("");
            switch (choice) {
                case 1:
                    userManager.addUser();
                    break;
                case 2:
                    userManager.displayUsers();
                    break;
                case 3:
                    userToInteract("update");
                    break;
                case 4:
                    userToInteract("delete");
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Отображает меню управления пользователями.
     */
    public void displayMenu() {
        System.out.println("----- User management menu -----");
        System.out.println("1. Add User");
        System.out.println("2. View Users");
        System.out.println("3. Update User Status");
        System.out.println("4. Delete User");
        System.out.println("5. Exit");
    }

    /**
     * Выполняет операции обновления или удаления пользователя в зависимости от переданной команды.
     * @param command Команда (update - обновление, delete - удаление).
     */
    public void userToInteract(String command) {
        if(Objects.equals(command, "update")){
            String username = inputManager.readString("Enter user username: ");
            int status = inputManager.readIntegerFromInput("Enter user new status: ");
            userManager.updateUserStatus(username, status);
        }
        if(Objects.equals(command, "delete")){
            String username = inputManager.readString("Enter user username: ");
            userManager.deleteUser(username);
        }
    }
}
