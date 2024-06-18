package org.example.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс User представляет сущность пользователя системы.
 * Пользователь имеет имя пользователя, пароль, статус и список тренировок.
 */
@Data
@NoArgsConstructor
public class User {

    private int id;
    private String username; // Имя пользователя.
    private String password; // Пароль пользователя.
    private int role; // Статус пользователя.


    public User(int id, String username, String password, int role){
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }
    public User(String username, String password, int role){
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }
}
