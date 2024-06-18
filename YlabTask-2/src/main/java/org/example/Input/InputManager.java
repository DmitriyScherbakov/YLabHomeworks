package org.example.Input;

import org.example.Entity.Workout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Класс InputManager предоставляет методы для считывания ввода с консоли.
 */
public class InputManager {
    private Scanner scanner;

    /**
     * Конструирует объект InputManager с инициализированным Scanner для чтения из System.in.
     */
    public InputManager() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Запрашивает у пользователя дату во введенном формате (например, "dd.MM.yyyy")
     *
     * @return Возвращает объект Date.
     */
    public Date readDateFromInput(String message) throws ParseException {
        System.out.print(message);
        String dateString = this.scanner.nextLine();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        return format.parse(dateString);
    }

    /**
     * Запрашивает у пользователя целое число. В случае ошибки ввода
     * (не целое число) выводит сообщение об ошибке и повторяет запрос.
     *
     * @return Возвращает целое число.
     */
    public int readIntegerFromInput(String message) {
        int number = 0;
        boolean isValidInput = false;

        while (!isValidInput) {
            System.out.print(message);
            String input = this.scanner.nextLine();

            try {
                number = Integer.parseInt(input);
                isValidInput = true;
            } catch (NumberFormatException var6) {
                System.out.println("Error: please enter an integer.");
            }
        }

        return number;
    }

    /**
     * Запрашивает у польвателя строку.
     *
     * @return Возвращает строку.
     */
    public String readString(String message) {
        System.out.print(message);
        return this.scanner.nextLine();
    }

    /**
     * Считывает тип тренировки из ввода консоли с использованием указанного набора типов тренировок.
     *
     * @param trainingTypes Набор доступных типов тренировок.
     * @return Выбранный тип тренировки.
     */
    public String readWorkoutTypeFromInput(Set<String> trainingTypes) {
        while (true) {
            System.out.println("Choose a workout type from the available options:");
            System.out.println("Available workout types:");
            Iterator var2 = trainingTypes.iterator();

            while (var2.hasNext()) {
                String availableType = (String) var2.next();
                System.out.println(availableType);
            }

            String selectedType = this.scanner.nextLine();
            if (trainingTypes.contains(selectedType)) {
                return selectedType;
            }

            System.out.println("Invalid workout type selected. Please choose from the list.");
        }
    }

    /**
     * Считывает дополнительную информацию для тренировки из ввода консоли и добавляет ее к объекту тренировки.
     *
     * @param workout Объект тренировки, к которому нужно добавить дополнительную информацию.
     */
    public void readAdditionalInfo(Workout workout) {
        System.out.println("Adding additional information:");

        while (true) {
            System.out.print("Enter the parameter name (or 'done' to finish): ");
            String key = this.scanner.nextLine();
            if (key.equals("done")) {
                return;
            }

            System.out.print("Enter the parameter value: ");
            String value = this.scanner.nextLine();
            //workout.addAdditionalInfo(key, value);
        }
    }
}
