package ru.task.tracker.manager;

/**
 * Утилитарный класс, создающий менеджеры
 */
public class Managers {

    /**
     * Метод получения объекта класса TaskManager
     * @return объект класса TaskManager
     */
    public static TaskManager getDefault(){
        return new InMemoryTaskManager();
    }

    /**
     * Метод получения объекта класса HistoryManager
     * @return объект класса HistoryManager
     */
    public static HistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }
}
