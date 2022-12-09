package ru.task.tracker.manager;

/**
 * Утилитарный класс, создающий менеджеры задач
 */
public class Managers {
    public static TaskManager getDefault(){
        return new InMemoryTaskManager();
    }
}
