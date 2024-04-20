package org.example.Service.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс User представляет сущность пользователя системы.
 * Пользователь имеет имя пользователя, пароль, статус и список тренировок.
 */
public class User {
    private String username; // Имя пользователя.
    private String password; // Пароль пользователя.
    private int status; // Статус пользователя.
    private List<Workout> workoutList; // Список тренировок пользователя.


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setTrainingList(List<Workout> workoutList) {
        this.workoutList = workoutList;
    }

    /**
     * Создает нового пользователя с указанным именем, паролем и статусом.
     * Инициализирует список тренировок пустым списком.
     * @param username Имя пользователя.
     * @param password Пароль пользователя.
     * @param status Статус пользователя.
     */
    public User(String username, String password, int status) {
        this.username = username;
        this.password = password;
        this.status = status;
        this.workoutList = new ArrayList();
    }

    /**
     * Создает нового пользователя с указанным именем и паролем.
     * Инициализирует список тренировок пустым списком.
     * @param username Имя пользователя.
     * @param password Пароль пользователя.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.workoutList = new ArrayList();
    }

    /**
     * Получает имя пользователя.
     * @return Имя пользователя.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Получает пароль пользователя.
     * @return Пароль пользователя.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Получает статус пользователя.
     * @return Статус пользователя.
     */
    public int getStatus() {
        return this.status;
    }

    /**
     * Получает список тренировок пользователя.
     * @return Список тренировок пользователя.
     */
    public List<Workout> getWorkoutList() {
        return this.workoutList;
    }

    /**
     * Добавляет тренировку в список тренировок пользователя.
     * @param workout Тренировка для добавления.
     */
    public void addWorkout(Workout workout) {
        this.workoutList.add(workout);
    }
}
