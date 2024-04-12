package org.example.Service.Managers;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс AuditLog представляет собой журнал действий, в котором записываются действия пользователей.
 * Журнал содержит список записанных действий в виде строк.
 */
public class AuditLog {
    private static List<String> actions = new ArrayList<>(); // Список записанных действий.

    /**
     * Записывает действие в журнал.
     * @param action Действие для записи.
     */
    public static void logAction(String action) {
        actions.add(action);
    }

    /**
     * Получает список всех записанных действий.
     * @return Список записанных действий.
     */
    public static List<String> getActions() {
        return actions;
    }

    /**
     * Очищает журнал действий, удаляя все записи.
     */
    public static void clearLog() {
        actions.clear();
    }
}
