package org.example.Service.Managers;

import org.example.Input.InputManager;
import org.example.Service.Entity.User;
import org.example.Service.Entity.Workout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Consumer;

/**
 * Класс WorkoutTypeManager отвечает за управление типами тренировок.
 */
public class WorkoutTypeManager {

    private static final Set<String> workoutsTypes = new HashSet(); // Множество типов тренировок.

    /**
     * Конструктор класса WorkoutTypeManager.
     */
    public WorkoutTypeManager() {
    }

    /**
     * Добавляет новый тип тренировки.
     * @param type Название типа тренировки.
     */
    public static void addWorkoutType(String type) {
        workoutsTypes.add(type.toLowerCase());
    }

    /**
     * Проверяет, содержит ли список доступных типов тренировок указанный тип.
     * @param type Название типа тренировки.
     * @return true, если тип тренировки содержится в списке, в противном случае - false.
     */
    public static boolean containsWorkoutType(String type) {
        return workoutsTypes.contains(type.toLowerCase());
    }

    /**
     * Получает список доступных типов тренировок.
     * @return Неизменяемое множество типов тренировок.
     */
    public static Set<String> getWorkoutTypes() {
        return Collections.unmodifiableSet(workoutsTypes);
    }

}
