package org.example.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Класс Workout представляет собой сущность тренировки, выполненную пользователем.
 * Каждая тренировка содержит дату, тип тренировки, длительность, количество сожженных калорий
 * и дополнительную информацию.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Workout {

    private int id; // идентификатор тренировки.
    private Date date; // Дата тренировки.
    private String type; // Тип тренировки.
    private int durationMinutes; // Длительность тренировки в минутах.
    private int caloriesBurned; // Количество сожженных калорий.
    private String additionalInfo; // Дополнительная информация о тренировке.
    private int whoCreatedId;


    public Workout(Date date, String type, int durationMinutes, int caloriesBurned, String additionalInfo, int whoCreatedId) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.durationMinutes = durationMinutes;
        this.caloriesBurned = caloriesBurned;
        this.additionalInfo = additionalInfo;
        this.whoCreatedId = whoCreatedId;
    }
}
