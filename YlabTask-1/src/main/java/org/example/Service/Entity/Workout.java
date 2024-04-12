package org.example.Service.Entity;

import org.example.Service.Managers.WorkoutTypeManager;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Класс Workout представляет собой сущность тренировки, выполненную пользователем.
 * Каждая тренировка содержит дату, тип тренировки, длительность, количество сожженных калорий
 * и дополнительную информацию.
 */
public class Workout {

    private Date date; // Дата тренировки.
    private String type; // Тип тренировки.
    private int durationMinutes; // Длительность тренировки в минутах.
    private int caloriesBurned; // Количество сожженных калорий.
    private Map<String, String> additionalInfo; // Дополнительная информация о тренировке.
    private WorkoutTypeManager typeManager; // Менеджер типов тренировок.

    /**
     * Создает новую тренировку с указанными параметрами.
     * @param date Дата тренировки.
     * @param type Тип тренировки.
     * @param durationMinutes Длительность тренировки в минутах.
     * @param caloriesBurned Количество сожженных калорий.
     * @param typeManager Менеджер типов тренировок.
     */
    public Workout(Date date, String type, int durationMinutes, int caloriesBurned, WorkoutTypeManager typeManager) {
        this.date = date;
        this.typeManager = typeManager;
        this.setType(type);
        this.durationMinutes = durationMinutes;
        this.caloriesBurned = caloriesBurned;
        this.additionalInfo = new HashMap();
    }

    public Workout() {
    }

    // Геттеры и сеттеры для полей класса.
    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        if (WorkoutTypeManager.containsWorkoutType(type)) {
            this.type = type;
        }
    }

    public int getDurationMinutes() {
        return this.durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public int getCaloriesBurned() {
        return this.caloriesBurned;
    }

    public void setCaloriesBurned(int caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public Map<String, String> getAdditionalInfo() {
        return this.additionalInfo;
    }

    public void setAdditionalInfo(Map<String, String> additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public void addAdditionalInfo(String key, String value) {
        this.additionalInfo.put(key, value);
    }

    public void printWorkoutDetails() {
        System.out.println("Date: " + this.date);
        System.out.println("Workout Type: " + this.type);
        System.out.println("Workout Duration: " + this.durationMinutes + " minutes");
        System.out.println("Calories Burned: " + this.caloriesBurned);
        System.out.println("Additional Information:");
        Iterator<Map.Entry<String, String>> iterator = this.additionalInfo.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }


}
