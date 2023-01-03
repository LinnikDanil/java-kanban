package ru.task.tracker.manager;

import ru.task.tracker.manager.tasks.Task;

import java.util.List;

/**
 * Интерфейс для управления историей просмотров
 */
public interface HistoryManager {

    /**
     * Метод помечает задачу как просмотренную
     * @param task
     */
    void add(Task task);

    /**
     * Метод удаляет задачу из истории
     * @param task
     */
    void remove(int id);

    /**
     * Метод получения истории из 10 последних задач
     *
     * @return список из задач
     */
    List<Task> getHistory();
}


