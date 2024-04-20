package org.example.Service.Menu;

import org.example.Input.InputManager;
import org.example.Service.Entity.User;
import org.example.Service.Managers.WorkoutManager;

import java.text.ParseException;
import java.util.Date;

/**
 * Класс WorkoutMenu предоставляет интерфейс меню для управления тренировками пользователя.
 * Пользователи могут выполнять различные действия, такие как добавление, просмотр, редактирование или удаление тренировок,
 * а также просматривать статистику, связанную с их тренировочными сессиями.
 */
public class WorkoutMenu {

    private User currentUser;

    /**
     * Конструирует объект WorkoutMenu с указанным пользователем.
     * @param currentUser пользователь, для которого отображается меню
     */
    public WorkoutMenu(User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Отображает варианты меню и обрабатывает ввод пользователя для выполнения соответствующих действий.
     * @throws ParseException если происходит ошибка при парсинге ввода пользователя в виде даты
     */
    public void menu() throws ParseException {
        WorkoutManager workoutManager = new WorkoutManager(currentUser);
        InputManager inputManager = new InputManager();
        while(true){
            showMenu();
            String choice = inputManager.readString("");
            switch (Integer.parseInt(choice)){
                case 1:
                    workoutManager.addWorkout();
                    break;
                case 2:
                    workoutManager.displayWorkouts();
                    break;
                case 3:
                    workoutManager.editWorkout();
                    break;
                case 4:
                    workoutManager.deleteWorkout();
                    break;
                case 5:
                    Date startDate = inputManager.readDateFromInput("Enter the start date (dd.MM.yyyy): ");
                    Date endDate = inputManager.readDateFromInput("Enter the end date (dd.MM.yyyy): ");
                    workoutManager.displayCaloriesBurnedByTimePeriod(startDate, endDate);
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
