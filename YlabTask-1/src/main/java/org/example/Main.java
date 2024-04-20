package org.example;

import org.example.Service.Menu.StartMenu;

import java.io.IOException;
import java.text.ParseException;

/**
 * Класс Main представляет точку входа в приложение.
 * Он инициализирует стартовое меню и обрабатывает запуск основного функционала программы.
 */
public class Main {

    /**
     * Конструктор класса Main.
     */
    public Main() {
    }

    /**
     * Точка входа в приложение
     * @param args аргументы командной строки (не используются)
     * @throws IOException    если возникает ошибка ввода-вывода
     * @throws ParseException если возникает ошибка при парсинге
     */
    public static void main(String[] args) throws IOException, ParseException {
        StartMenu startMenu = new StartMenu();
        startMenu.handleMenu();
    }
}
