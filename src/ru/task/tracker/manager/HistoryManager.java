package ru.task.tracker.manager;

import ru.task.tracker.manager.tasks.Task;

import java.util.ArrayList;
import java.util.LinkedList;

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
    void remove(Task task);

    /**
     * Метод получения истории из 10 последних задач
     *
     * @return список из задач
     */
    ArrayList<Task> getHistory();
}


