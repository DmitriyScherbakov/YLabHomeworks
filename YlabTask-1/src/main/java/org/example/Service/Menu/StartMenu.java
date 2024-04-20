package org.example.Service.Menu;

import org.example.Input.InputManager;
import org.example.Service.Authentification.Authorization;
import org.example.Service.Authentification.Registration;
import org.example.Service.Entity.User;

import java.text.ParseException;

/**
 * Класс StartMenu представляет главное меню при запуске приложения, где пользователь может
 * выполнить вход, зарегистрироваться или закрыть приложение.
 */
public class StartMenu {

    InputManager inputManager; // Менеджер ввода.
    Registration registration; // Менеджер регистрации пользователей.
    Authorization authorizations; // Менеджер авторизации пользователей.

    /**
     * Конструктор класса StartMenu.
     */
    public StartMenu() {
    }

    /**
     * Обрабатывает главное меню приложения.
     * @throws ParseException Исключение, возникающее при ошибке парсинга.
     */
    public void handleMenu() throws ParseException {
        this.inputManager = new InputManager();
        this.registration = new Registration(this.inputManager);
        this.registration.createAdmin();
        this.authorizations = new Authorization(this.registration.getUsers());

        while(true) {
            this.displayMenu();
            int choice = this.inputManager.readIntegerFromInput("");
            switch (choice) {
                case 1:
                    this.authorizationProcess();
                    break;
                case 2:
                    this.registration.registerUser();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please enter 1 or 3.");
            }
        }
    }

    /**
     * Отображает главное меню приложения.
     */
    private void displayMenu() {
        System.out.println("----- Welcome -----");
        System.out.println("1.Authorization");
        System.out.println("2.Registration");
        System.out.println("3.Close app");
    }

    /**
     * Процесс авторизации пользователя.
     * @throws ParseException Исключение, возникающее при ошибке парсинга.
     */
    private void authorizationProcess() throws ParseException {
        String userLogin = this.inputManager.readString("Input login: ");
        String userPass = this.inputManager.readString("Input password: ");
        User user = this.authorizations.authenticate(userLogin, userPass);
        if (user != null) {
            this.checkStatus(user);
        }

    }

    /**
     * Проверяет статус пользователя и переходит к соответствующему меню.
     * @param user Пользователь.
     * @throws ParseException Исключение, возникающее при ошибке парсинга.
     */
    public void checkStatus(User user) throws ParseException {
        switch (user.getStatus()) {
            case 0:
                System.out.println("Banned");
                return;
            case 1:
                WorkoutMenu workoutMenu = new WorkoutMenu(user);
                workoutMenu.menu();
                break;
            case 2:
                AdminMenu adminMenu = new AdminMenu(this.inputManager, user, this.registration.getUsers());
                adminMenu.handleMenu();
        }

    }
}
