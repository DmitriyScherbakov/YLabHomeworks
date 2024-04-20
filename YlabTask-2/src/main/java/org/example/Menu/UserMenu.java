package org.example.Menu;

import lombok.RequiredArgsConstructor;
import org.example.Input.InputManager;
import org.example.Entity.User;
import org.example.Services.UserService;

import java.util.List;
import java.util.Objects;

/**
 * Класс UserMenu представляет меню управления пользователями, где администратор может
 * добавлять, просматривать, обновлять статус и удалять пользователей.
 */
@RequiredArgsConstructor
public class UserMenu {

    UserService userService;
    InputManager inputManager;

    public UserMenu(InputManager inputManager, UserService userService) {
        this.inputManager = inputManager;
        this.userService = userService;
    }
    /**
     * Обрабатывает меню управления пользователями.
     */
    public void handleMenu() {
        while (true) {
            displayMenu();
            int choice = inputManager.readIntegerFromInput("");
            switch (choice) {
                case 1:
                    User user = getUserInfo();
                    userService.addUser(user);
                    break;
                case 2:
                    displayAllUsers();
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

    private void displayAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            System.out.println("List of users:");
            for (User user : users) {
                System.out.println(user);
            }
        }
    }

    public User getUserInfo() {
        String username = this.inputManager.readString("Enter user username: ");
        String password = this.inputManager.readString("Enter user password: ");
        int status = this.inputManager.readIntegerFromInput("Enter user status: ");
        return new User(username, password, status);
    }

    /**
     * Выполняет операции обновления или удаления пользователя в зависимости от переданной команды.
     * @param command Команда (update - обновление, delete - удаление).
     */
    public void userToInteract(String command) {
        if(Objects.equals(command, "update")){
            displayAllUsers();
            String username = inputManager.readString("Enter user username: ");
            int role = inputManager.readIntegerFromInput("Enter user new status: ");
            userService.updateUserRole(username, role);
        }
        if(Objects.equals(command, "delete")){
            displayAllUsers();
            int id = inputManager.readIntegerFromInput("Enter user id: ");
            userService.deleteUser(id);
        }
    }
}
