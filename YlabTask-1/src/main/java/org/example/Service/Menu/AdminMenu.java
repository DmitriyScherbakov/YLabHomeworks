package org.example.Service.Menu;

import org.example.Input.InputManager;
import org.example.Service.Entity.User;
import org.example.Service.Managers.AuditLog;
import org.example.Service.Managers.WorkoutTypeManager;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Класс AdminMenu представляет меню администратора, где можно управлять пользователями, тренировками,
 * добавлять новые типы тренировок и просматривать аудит действий пользователей.
 */
public class AdminMenu {

    private InputManager inputManager; // Менеджер ввода.
    private Map<String, User> users; // Список пользователей.
    private User currentUser; // Текущий пользователь (администратор).
    private WorkoutTypeManager workoutTypeManager; // Менеджер типов тренировок.

    /**
     * Конструктор класса AdminMenu.
     * @param inputManager Менеджер ввода.
     * @param currentUser Текущий пользователь (администратор).
     * @param users Список пользователей.
     */
    public AdminMenu(InputManager inputManager, User currentUser, Map<String, User> users) {
        this.inputManager = inputManager;
        this.users = users;
        this.currentUser = currentUser;
    }

    /**
     * Обрабатывает главное меню администратора.
     * @throws ParseException Исключение, возникающее при ошибке парсинга.
     */
    public void handleMenu() throws ParseException {
        this.workoutTypeManager = new WorkoutTypeManager();

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
                    this.addNewTrainingType();
                    break;
                case 4:
                    this.displayAuditLog();
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
     * Добавляет новый тип тренировки.
     */
    void addNewTrainingType() {
        String newType = this.inputManager.readString("Enter new training type: ");
        if (!WorkoutTypeManager.containsWorkoutType(newType)) {
            WorkoutTypeManager.addWorkoutType(newType);
            System.out.println("New training type \"" + newType + "\" added.");
        } else {
            System.out.println("Training type added before");
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
        UserMenu userMenu = new UserMenu(this.inputManager, this.users);
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

    /**
     * Отображает аудит действий пользователей.
     */
    void displayAuditLog() {
        System.out.println("----- Audit Log -----");
        List<String> actions = AuditLog.getActions();
        if (actions.isEmpty()) {
            System.out.println("No actions recorded yet.");
        } else {
            for (String action : actions) {
                System.out.println(action);
            }
        }
    }
}
