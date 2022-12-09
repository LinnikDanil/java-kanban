package ru.task.tracker.manager;

/**
 * Утилитарный класс, создающий менеджеры задач
 */
public class Managers {
    /**
     * Метод получения объекта класса TaskManager
     * @return объект класса TaskManager
     */

    public static TaskManager getDefault(){
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }
}
