package ru.task.tracker.manager;

import ru.task.tracker.manager.tasks.Epic;
import ru.task.tracker.manager.tasks.Subtask;
import ru.task.tracker.manager.tasks.Task;
import java.util.ArrayList;

/**
 * Интерфейс менеджера задач со всей логикой работы, отвечающий за управление классами задач.
 */
 interface TaskManager {

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
     ArrayList<Subtask> getAllSubtask();

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
     * @return task
     */
     Task createTask(Task task);

    /**
     * Создание эпика - кладёт объект в таблицу
     *
     * @param epic
     * @return epic
     */
     Epic createEpic(Epic epic);

    /**
     * Создание сабтаска - кладёт объект в таблицу, добавляет эпик айди в сабтаске и айди сабтаска в эпик
     *
     * @param subtask
     * @return subtask
     */
     Subtask createSubtask(Subtask subtask);

    /**
     * обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
     *
     * @param task
     * @return task
     */
     Task updateTask(Task task);

    /**
     * обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
     *
     * @param epic
     * @return epic
     */
     Epic updateEpic(Epic epic);

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
     ArrayList<Integer> getAllSubtaskByEpicId(int epicId);

    /**
     * метод обновления статуса эпика
     * -если у эпика нет подзадач или все они имеют статус NEW, то статус должен быть NEW.
     * -если все подзадачи имеют статус DONE, то и эпик считается завершённым — со статусом DONE.
     * -во всех остальных случаях статус должен быть IN_PROGRESS.
     *
     * @return статус
     */
     String updateStatusEpic(int epicId);

    /**
     * Метод получения нового айди
     *
     * @return айди
     */
     int getNewId();
}
