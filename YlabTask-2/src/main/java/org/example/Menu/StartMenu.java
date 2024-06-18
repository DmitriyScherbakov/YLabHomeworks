package org.example.Menu;

import lombok.RequiredArgsConstructor;
import org.example.Input.InputManager;
import org.example.Repository.UserRepository;
import org.example.Repository.UserRepositoryImpl;
import org.example.Authentification.Authorization;
import org.example.Authentification.Registration;
import org.example.Entity.User;
import org.example.Services.UserService;
import org.example.Services.WorkoutService;

import java.text.ParseException;
import java.util.Optional;

/**
 * Класс StartMenu представляет главное меню при запуске приложения, где пользователь может
 * выполнить вход, зарегистрироваться или закрыть приложение.
 */
@RequiredArgsConstructor
public class StartMenu {

    InputManager inputManager; // Менеджер ввода.
    Registration registration; // Менеджер регистрации пользователей.
    Authorization authorization; // Менеджер авторизации пользователей.
    UserService userService;

    WorkoutService workoutService;
    UserRepository userRepository;

    /**
     * Обрабатывает главное меню приложения.
     *
     * @throws ParseException Исключение, возникающее при ошибке парсинга.
     */
    public void handleMenu() throws ParseException {
        this.inputManager = new InputManager();
        this.userRepository = new UserRepositoryImpl();
        this.userService = new UserService(userRepository);
        this.registration = new Registration(this.inputManager, this.userService);
        this.registration.createAdmin();
        this.authorization = new Authorization(this.userService);

        while (true) {
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
                    System.out.println("Invalid choice.");
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
     *
     * @throws ParseException Исключение, возникающее при ошибке парсинга.
     */
    private void authorizationProcess() throws ParseException {
        String userLogin = this.inputManager.readString("Input login: ");
        String userPass = this.inputManager.readString("Input password: ");
        Optional<User> user = this.authorization.authenticate(userLogin, userPass);
        user.ifPresent(this::checkStatus); // Если пользователь присутствует, передаем его в метод checkStatus
    }

    /**
     * Проверяет статус пользователя и переходит к соответствующему меню.
     *
     * @param loggedInUser Пользователь.
     * @throws ParseException Исключение, возникающее при ошибке парсинга.
     */
    public void checkStatus(User loggedInUser) {
        try {
            switch (loggedInUser.getRole()) {
                case 0:
                    System.out.println("Banned");
                    return;
                case 1:
                    // Аутентификация пользователя
                    WorkoutMenu workoutMenu = new WorkoutMenu(loggedInUser);
                    workoutMenu.menu();
                    break;
                case 2:
                    AdminMenu adminMenu = new AdminMenu(this.inputManager, userService, loggedInUser);
                    adminMenu.handleMenu();
            }
        } catch (ParseException e) {
            System.err.println("Error parsing input: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
