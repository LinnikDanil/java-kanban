package ru.task.tracker.manager;

import ru.task.tracker.manager.tasks.Epic;
import ru.task.tracker.manager.tasks.Subtask;
import ru.task.tracker.manager.tasks.Task;
import java.util.ArrayList;
import java.util.List;

/**
 * Интерфейс менеджера задач со всей логикой работы, отвечающий за управление классами задач.
 */
 public interface TaskManager {

    /**
     * Метод получения менеждера истории
     * @return
     */
    public HistoryManager getHistoryManager();

    /**
     * Метод получения отсортированного по времени списка всех задач и подзадач
     * @return
     */
    public List<Task> getPrioritizedTasks();

    /**
     * Метод получения списка всех задач
     *
     * @return список всех задач
     */
     ArrayList<Task> getAllTasks();

    /**
     * Метод получения списка всех эпиков
     *
     * @return список всех эпиков
     */
     ArrayList<Epic> getAllEpics();

    /**
     * Метод получения списка всех подзадач
     *
     * @return список всех подзадач
     */
     ArrayList<Subtask> getAllSubtasks();

    /**
     * Метод удаления всех задач
     */
     void clearAllTasks();

    /**
     * Метод удаления всех эпиков
     * Перед удалением эпиков, удаляет все сабтаски
     */
     void clearAllEpics();

    /**
     * Метод удаления всех подзадач
     */
     void clearAllSubtasks();

    /**
     * Получить задачу по id
     *
     * @param taskId
     * @return объект task
     */
     Task getTaskById(int taskId);

    /**
     * Получить эпик по id
     *
     * @param epicId
     * @return объект epic
     */
     Epic getEpicById(int epicId);

    /**
     * Получить задачу по id
     *
     * @param subtaskId
     * @return объект subtask или null, если нет нужного эпика
     */
     Subtask getSubtaskById(int subtaskId);

    /**
     * Создание таска - кладёт объект в таблицу
     *
     * @param task
     * @return taskId
     */
     int createTask(Task task);

    /**
     * Создание эпика - кладёт объект в таблицу
     *
     * @param epic
     * @return epidId
     */
    int createEpic(Epic epic);

    /**
     * Создание сабтаска - кладёт объект в таблицу, добавляет эпик айди в сабтаске и айди сабтаска в эпик
     *
     * @param subtask
     * @return subtaskId
     */
    int createSubtask(Subtask subtask);

    /**
     * обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
     *
     * @param task
     */
     void updateTask(Task task);

    /**
     * обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
     *
     * @param epic
     */
    void updateEpic(Epic epic);

    /**
     * обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра,
     * а также обновляется статус эпика
     *
     * @param subtask
     */
     void updateSubtask(Subtask subtask);

    /**
     * Удаление таска по идентификатору
     *
     * @param taskId
     */
     void removeTaskById(int taskId);

    /**
     * Удаление эпика по идентификатору
     * Перед удалением эпика удаляет все его сабтаски
     *
     * @param epicId
     */
     void removeEpicById(int epicId);

    /**
     * Удаление сабтаска по идентификатору, также удаляет айди из эпика
     *
     * @param subtaskId
     */
     void removeSubtaskById(int subtaskId);

    /**
     * Получение списка всех подзадач определённого эпика.
     *
     * @param epicId
     * @return список сабтасков
     */
     ArrayList<Subtask> getAllSubtaskByEpicId(int epicId);
}
