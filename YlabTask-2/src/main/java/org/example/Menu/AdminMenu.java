package org.example.Menu;

import lombok.RequiredArgsConstructor;
import org.example.Input.InputManager;
import org.example.Repository.AuditService;
import org.example.Entity.User;
import org.example.Services.UserService;
import org.example.Services.WorkoutTypeService;

import java.text.ParseException;

/**
 * Класс AdminMenu представляет меню администратора, где можно управлять пользователями, тренировками,
 * добавлять новые типы тренировок и просматривать аудит действий пользователей.
 */
@RequiredArgsConstructor
public class AdminMenu {

    InputManager inputManager; // Менеджер ввода.
    WorkoutTypeService workoutTypeService;
    UserService userService;

    AuditService auditService = new AuditService();
    private User currentUser;

    public AdminMenu(InputManager inputManager, UserService userService, User currentUser) {
        this.inputManager = inputManager;
        this.userService = userService;
        this.currentUser = currentUser;
    }

    /**
     * Обрабатывает главное меню администратора.
     * @throws ParseException Исключение, возникающее при ошибке парсинга.
     */
    public void handleMenu() throws ParseException {
        this.workoutTypeService = new WorkoutTypeService();
        while(true) {
            this.displayMenu();
            int choice = this.inputManager.readIntegerFromInput("");
            switch (choice) {
                case 1:
                    this.manageUsers();
                    break;
                case 2:
                    this.manageTrainings();
                    break;
                case 3:
                    workoutTypeService.createNewWorkoutType();
                    break;
                case 4:
                    auditService.printAllAuditRecords();
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
     * Отображает главное меню администратора.
     */
    public void displayMenu() {
        System.out.println("----- Admin Menu -----");
        System.out.println("1. User Management");
        System.out.println("2. Workout Management");
        System.out.println("3. Add new training type");
        System.out.println("4. Audit of user actions");
        System.out.println("5. Exit");
    }


    /**
     * Управление пользователями.
     */
    void manageUsers() {
        UserMenu userMenu = new UserMenu(inputManager, userService);
        userMenu.handleMenu();
    }

    /**
     * Управление тренировками.
     * @throws ParseException Исключение, возникающее при ошибке парсинга.
     */
    void manageTrainings() throws ParseException {
        WorkoutMenu workoutMenu = new WorkoutMenu(this.currentUser);
        workoutMenu.menu();
    }
}
