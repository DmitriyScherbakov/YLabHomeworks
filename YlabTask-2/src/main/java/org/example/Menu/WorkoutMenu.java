package org.example.Menu;

import lombok.RequiredArgsConstructor;
import org.example.Input.InputManager;
import org.example.Entity.User;
import org.example.Services.WorkoutService;

import java.text.ParseException;
import java.util.Date;

/**
 * Класс WorkoutMenu предоставляет интерфейс меню для управления тренировками пользователя.
 * Пользователи могут выполнять различные действия, такие как добавление, просмотр, редактирование или удаление тренировок,
 * а также просматривать статистику, связанную с их тренировочными сессиями.
 */
@RequiredArgsConstructor
public class WorkoutMenu {

    WorkoutService workoutService;
    User currentUser;
    public WorkoutMenu( User currentUser) {
        this.currentUser = currentUser;
    }
    /**
     * Отображает варианты меню и обрабатывает ввод пользователя для выполнения соответствующих действий.
     * @throws ParseException если происходит ошибка при парсинге ввода пользователя в виде даты
     */
    public void menu() throws ParseException {
        WorkoutService workoutService = new WorkoutService(currentUser);
        InputManager inputManager = new InputManager();
        while(true){
            showMenu();
            String choice = inputManager.readString("");
            switch (Integer.parseInt(choice)){
                case 1:
                    workoutService.createNewWorkout(currentUser);
                    break;
                case 2:
                    workoutService.displayWorkouts(currentUser);
                    break;
                case 3:
                    workoutService.updateWorkout(currentUser);
                    break;
                case 4:
                    workoutService.deleteWorkout(currentUser);
                    break;
                case 5:
                    Date startDate = inputManager.readDateFromInput("Enter the start date (dd.MM.yyyy): ");
                    Date endDate = inputManager.readDateFromInput("Enter the end date (dd.MM.yyyy): ");
                    workoutService.showStatistics(startDate, endDate);
                    break;
                case 6:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter 1 or 5.");
                    break;
            }
        }

    }

    /**
     * Отображает варианты меню пользователю.
     */
    public void showMenu() {
        System.out.println("\n----- Menu -----");
        System.out.println("1. Add workout");
        System.out.println("2. View workouts");
        System.out.println("3. Edit workout");
        System.out.println("4. Delete workout");
        System.out.println("5. Statistics");
        System.out.println("6. Logout");
        System.out.print("Select an action: ");
    }
}
