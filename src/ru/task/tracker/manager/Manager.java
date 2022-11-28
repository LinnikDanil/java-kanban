package ru.task.tracker.manager;

import ru.task.tracker.manager.tasks.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Класс приложения со всей логикой работы, отвечающий за управление классами задач.
 */
public class Manager {
    private int id;
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, Epic> epics;
    private HashMap<Integer, Subtask> subtasks;

    /**
     * Конструктор - создание нового объекта
     *
     * @see Manager
     */
    public Manager() {
        this.id = 0;
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subtasks = new HashMap<>();
    }

    /**
     * Метод получения списка всех задач
     *
     * @return список всех задач
     */
    public ArrayList<Task> getAllTasks() {
        if (!tasks.isEmpty()) {
            ArrayList<Task> tasksList = new ArrayList<>(tasks.values());
            return tasksList;
        }
        return null;
    }

    /**
     * Метод получения списка всех эпиков
     *
     * @return список всех эпиков
     */
    public ArrayList<Epic> getAllEpics() {
        if (!epics.isEmpty()) {
            ArrayList<Epic> epicsList = new ArrayList<>(epics.values());
            return epicsList;
        }
        return null;

    }

    /**
     * Метод получения списка всех подзадач
     *
     * @return список всех подзадач
     */
    public ArrayList<Subtask> getAllSubtask() {
        if (!subtasks.isEmpty()) {
            ArrayList<Subtask> subtaskList = new ArrayList<>(subtasks.values());
            return subtaskList;
        }
        return null;
    }

    /**
     * Метод удаления всех задач
     */
    public void clearAllTasks() {
        tasks.clear();
    }

    /**
     * Метод удаления всех эпиков
     * Перед удалением эпиков, удаляет все сабтаски
     */
    public void clearAllEpics() {
        subtasks.clear();
        epics.clear();
    }

    /**
     * Метод удаления всех подзадач
     */
    public void clearAllSubtasks() {
        for (Epic ep : epics.values()){
            ep.clearSubtasks();
        }
        subtasks.clear();
    }

    /**
     * Получить задачу по id
     *
     * @param taskId
     * @return объект task
     */
    public Task getTaskById(int taskId) {
        return tasks.get(taskId);
    }

    /**
     * Получить эпик по id
     *
     * @param epicId
     * @return объект epic
     */
    public Epic getEpicById(int epicId) {
        return epics.get(epicId);
    }

    /**
     * Получить задачу по id
     *
     * @param subtaskId
     * @return объект subtask или null, если нет нужного эпика
     */
    public Subtask getSubtaskById(int subtaskId) {
        return subtasks.get(subtaskId);
    }

    /**
     * Создание таска - кладёт объект в таблицу
     *
     * @param task
     * @return task
     */
    public Task createTask(Task task) {
        task.setId(getNewId());
        tasks.put(task.getId(), task);
        return task;
    }

    /**
     * Создание эпика - кладёт объект в таблицу
     *
     * @param epic
     * @return epic
     */
    public Epic createEpic(Epic epic) {
        epic.setId(getNewId());
        epics.put(epic.getId(), epic);
        return epic;
    }

    /**
     * Создание сабтаска - кладёт объект в таблицу, добавляет эпик айди в сабтаске и айди сабтаска в эпик
     *
     * @param subtask
     * @return subtask
     */
    public Subtask createSubtask(Subtask subtask) {
        if (!epics.isEmpty()){
            subtask.setId(getNewId());
            epics.get(subtask.getEpicId()).addSubtask(subtask.getId());
            subtasks.put(subtask.getId(), subtask);
            return subtask;
        }
        return null;
    }

    /**
     * обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
     *
     * @param task
     * @return task
     */
    public Task updateTask(Task task) {
        tasks.put(task.getId(), task);
        return task;
    }

    /**
     * обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
     *
     * @param epic
     * @return epic
     */
    public Epic updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
        return epic;
    }

    /**
     * обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра,
     * а также обновляется статус эпика
     *
     * @param subtask
     * @return subtask
     */
    public Subtask updateSubtask(Subtask subtask) {
        if (!epics.isEmpty()){
            subtasks.put(subtask.getId(), subtask);
            updateStatusEpic(subtask.getEpicId());
            return subtask;
        }
        return null;
    }

    /**
     * Удаление таска по идентификатору
     *
     * @param taskId
     */
    public void removeTaskById(int taskId) {
        tasks.remove(taskId);
    }

    /**
     * Удаление эпика по идентификатору
     * Перед удалением эпика удаляет все его сабтаски
     *
     * @param epicId
     */
    public void removeEpicById(int epicId) {
        if (!subtasks.isEmpty()){
            for (int idSubtask : epics.get(epicId).getSubtasks()) {
                subtasks.remove(idSubtask);
            }
            epics.get(epicId).clearSubtasks();
            epics.remove(epicId);
        }
    }

    /**
     * Удаление сабтаска по идентификатору, также удаляет айди из эпика
     *
     * @param subtaskId
     */
    public void removeSubtaskById(int subtaskId) {
        if (!epics.isEmpty()){
            epics.get(subtasks.get(subtaskId).getEpicId()).removeSubtask(subtaskId); //удаление айди из списка в эпике
            subtasks.remove(subtaskId);
        }
    }

    /**
     * Получение списка всех подзадач определённого эпика.
     *
     * @param epicId
     * @return список сабтасков
     */
    public ArrayList<Integer> getAllSubtaskByEpicId(int epicId) {
        if (!epics.isEmpty()) {
            ArrayList subtasksInEpic = new ArrayList<>();
            for (int i : epics.get(epicId).getSubtasks()){
                subtasksInEpic.add(subtasks.get(i));
            }
            return subtasksInEpic;
        }
        return null;
    }

    public String updateStatusEpic(int epicId) {
        int numberSubtasks = subtasks.size();
        int i = 0;
        for (Subtask s : subtasks.values()) {
            if (s.getStatus().equals(Task.STATUSES[0])) {
                i++;
            }
        }
        if (i == numberSubtasks) {
            epics.get(epicId).setStatus(0);
            return Epic.STATUSES[0];
        }
        i = 0;
        for (Subtask s : subtasks.values()) {
            if (s.getStatus().equals(Epic.STATUSES[2])) {
                i++;
            }
        }
        if (i == numberSubtasks) {
            epics.get(epicId).setStatus(2);
            return Epic.STATUSES[2];
        }
        epics.get(epicId).setStatus(1);
        return Epic.STATUSES[1];
    }

    /**
     * Метод получения нового айди
     *
     * @return айди
     */
    public int getNewId() {
        return ++id;
    }
}
