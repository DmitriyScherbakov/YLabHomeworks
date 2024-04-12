package org.example.Service.Managers;

import org.example.Input.InputManager;
import org.example.Service.Entity.User;
import org.example.Service.Entity.Workout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Consumer;

/**
 * Класс WorkoutManager отвечает за управление тренировками пользователей, включая добавление,
 * отображение, редактирование и удаление тренировок.
 */
public class WorkoutManager {

    private final User currentUser; // Текущий пользователь, для которого управляются тренировки.
    WorkoutTypeManager workoutTypeManager = new WorkoutTypeManager(); // Менеджер типов тренировок.
    InputManager inputManager = new InputManager(); // Менеджер ввода данных.

    /**
     * Конструктор класса WorkoutManager.
     * @param currentUser Текущий пользователь.
     */
    public WorkoutManager(User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Добавляет новую тренировку для текущего пользователя.
     * @throws ParseException Если происходит ошибка при разборе даты.
     */
    public void addWorkout() throws ParseException {
        System.out.println("Adding a new workout:");
        Date date = this.inputManager.readDateFromInput("Enter the workout date (dd.MM.yyyy): ");
        String type = this.inputManager.readWorkoutTypeFromInput(WorkoutTypeManager.getWorkoutTypes());
        int duration = this.inputManager.readIntegerFromInput("Enter the workout duration (in minutes): ");
        int calories = this.inputManager.readIntegerFromInput("Enter the calories burned: ");
        Workout workout = new Workout(date, type, duration, calories, this.workoutTypeManager);
        this.inputManager.readAdditionalInfo(workout);
        this.currentUser.addWorkout(workout);
        System.out.println("New workout added:");
        workout.printWorkoutDetails();
        AuditLog.logAction("User " + currentUser.getUsername() + " added a new workout.");
    }

    /**
     * Отображает список тренировок текущего пользователя.
     */
    public void displayWorkouts() {
        System.out.println("\nWorkout List:");
        if (this.currentUser.getWorkoutList().isEmpty()) {
            System.out.println("No workouts available.");
        } else {
            Collections.sort(this.currentUser.getWorkoutList(), Comparator.comparing(Workout::getDate).reversed());
            for (int i = 0; i < this.currentUser.getWorkoutList().size(); ++i) {
                System.out.print(i + 1 + ". ");
                this.currentUser.getWorkoutList().get(i).printWorkoutDetails();
            }
        }
    }

    /**
     * Удаляет выбранную тренировку из списка тренировок текущего пользователя.
     */
    public void deleteWorkout() {
        System.out.println("Deleting a workout:");
        if (this.currentUser.getWorkoutList().isEmpty()) {
            System.out.println("No workouts to delete.");
        } else {
            System.out.println("Choose the workout to delete:");
            this.displayWorkouts();
            int choice = this.inputManager.readIntegerFromInput("Enter the workout number to delete: ");
            if (choice >= 1 && choice <= this.currentUser.getWorkoutList().size()) {
                Workout deletedWorkout = this.currentUser.getWorkoutList().remove(choice - 1);
                System.out.println("Workout deleted:");
                deletedWorkout.printWorkoutDetails();
            } else {
                System.out.println("Invalid choice. Workout not deleted.");
            }
        }
    }

    /**
     * Позволяет редактировать выбранную тренировку текущего пользователя.
     * @throws ParseException Если происходит ошибка при разборе даты.
     */
    public void editWorkout() throws ParseException {
        System.out.println("Editing a workout:");
        if (this.currentUser.getWorkoutList().isEmpty()) {
            System.out.println("No workouts to edit.");
        } else {
            System.out.println("Choose the workout to edit:");
            this.displayWorkouts();
            int choice = this.inputManager.readIntegerFromInput("Enter the workout number to edit: ");
            if (choice >= 1 && choice <= this.currentUser.getWorkoutList().size()) {
                Workout workoutToEdit = this.currentUser.getWorkoutList().get(choice - 1);
                System.out.println("Enter new values for the workout (leave blank to keep unchanged):");
                editDateWorkoutField("Workout date (" + workoutToEdit.getDate() + "): ", workoutToEdit::setDate);
                editIntegerWorkoutField("Workout duration (" + workoutToEdit.getDurationMinutes() + " minutes): ", workoutToEdit::setDurationMinutes);
                editIntegerWorkoutField("Calories burned (" + workoutToEdit.getCaloriesBurned() + "): ", workoutToEdit::setCaloriesBurned);
                System.out.println("Editing additional information (leave blank to keep unchanged):");
                String selectedType;
                do {
                    System.out.println("Choose a workout type from the available options");
                    System.out.println("Available workout types:");
                    for (String availableType : WorkoutTypeManager.getWorkoutTypes()) {
                        System.out.println(availableType);
                    }
                    selectedType = this.inputManager.readString("");
                    if (WorkoutTypeManager.getWorkoutTypes().contains(selectedType)) {
                        workoutToEdit.setType(selectedType);
                        Map<String, String> additionalInfo = workoutToEdit.getAdditionalInfo();
                        additionalInfo.forEach((key, value) -> {
                            String newValue = this.inputManager.readString(key + " (" + value + "): ");
                            if (!newValue.isEmpty()) {
                                additionalInfo.put(key, newValue);
                            }
                        });
                        System.out.println("Workout successfully edited");
                        workoutToEdit.printWorkoutDetails();
                        return;
                    }
                    if (!selectedType.isEmpty()) {
                        System.out.println("Invalid workout type selected. Please choose from the list.");
                    }
                } while (!selectedType.isEmpty());
            } else {
                System.out.println("Invalid choice. Workout not edited.");
            }
        }
    }

    private void editIntegerWorkoutField(String prompt, Consumer<Integer> setter) throws ParseException {
        String input = this.inputManager.readString(prompt);
        if (!input.isEmpty()) {
            setter.accept(Integer.parseInt(input));
        }
    }

    private void editDateWorkoutField(String prompt, Consumer<Date> setter) throws ParseException {
        String inputString = this.inputManager.readString(prompt);
        if (!inputString.isEmpty()) {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Date input = format.parse(inputString);
            setter.accept(input);
        }
    }

    /**
     * Отображает общее количество сожженных калорий за указанный временной период.
     * @param startDate Начальная дата временного периода.
     * @param endDate Конечная дата временного периода.
     */
    public void displayCaloriesBurnedByTimePeriod(Date startDate, Date endDate) {
        int totalCaloriesBurned = 0;
        for (Workout workout : this.currentUser.getWorkoutList()) {
            Date workoutDate = workout.getDate();
            if (workoutDate.after(startDate) && workoutDate.before(endDate)) {
                totalCaloriesBurned += workout.getCaloriesBurned();
            }
        }
        System.out.println("Total calories burned between " + startDate + " and " + endDate + ": " + totalCaloriesBurned);
    }
}
