import java.util.ArrayList;
import java.util.HashMap;

/**
 * Класс приложения со всей логикой работы, отвечающий за управление классами задач.
 */
public class Manager {
    private int id;
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, Epic> epics;

    /**
     * Конструктор - создание нового объекта
     *
     * @see Manager
     */
    public Manager() {
        this.id = 0;
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
    }

    /**
     * Метод получения списка всех задач
     *
     * @return список всех задач
     */
    public ArrayList<Task> getAllTasks() {
        if (!tasks.isEmpty()) {
            ArrayList<Task> tasksList = new ArrayList<>();
            for (Task task : tasks.values()) {
                tasksList.add(task);
            }
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
            ArrayList<Epic> epicsList = new ArrayList<>();
            for (Epic epic : epics.values()) {
                epicsList.add(epic);
            }
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
        if (!epics.isEmpty()) {
            ArrayList<Subtask> subtasksList = new ArrayList<>();
            for (Integer key : epics.keySet()) {
                for (Subtask s : findEpicById(key).getSubtasks().values()) {
                    subtasksList.add(s);
                }
            }
            return subtasksList;
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
        clearAllSubtasks();
        epics.clear();
    }

    /**
     * Метод удаления всех подзадач
     */
    public void clearAllSubtasks() {
        if (!epics.isEmpty()) {
            for (Integer key : epics.keySet()) {
                findEpicById(key).getSubtasks().clear();
            }
        }
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
     * @param epicId
     * @param subtaskId
     * @return объект subtask или null, если нет нужного эпика
     */
    public Subtask getSubtaskById(int epicId, int subtaskId) {
        if (findEpicById(epicId) != null) {
            return findEpicById(epicId).getSubtasks().get(subtaskId);
        }
        return null;
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
     * Создание сабтаска - кладёт объект в таблицу эпика, добавляет эпик айди в сабтаске
     *
     * @param epicId
     * @param subtask
     * @return subtask
     */
    public Subtask createSubtask(int epicId, Subtask subtask) {
        if (findEpicById(epicId) != null) {
            subtask.setId(getNewId());
            subtask.setEpicId(epicId);
            findEpicById(epicId).putSubtask(subtask);
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
        int id = task.getId();
        tasks.put(id, task);
        return task;
    }

    /**
     * обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
     *
     * @param epic
     * @return epic
     */
    public Epic updateEpic(Epic epic) {
        int id = epic.getId();
        epics.put(id, epic);
        return epic;
    }

    /**
     * обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра,
     * а также обновляется статус эпика
     *
     * @param epicId
     * @param subtask
     * @return subtask
     */
    public Subtask updateSubtask(int epicId, Subtask subtask) {
        if (findEpicById(epicId) != null) {
            findEpicById(epicId).putSubtask(subtask);
            epics.get(epicId).updateStatus();
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
        epics.get(epicId).getSubtasks().clear();
        epics.remove(epicId);
    }

    /**
     * Удаление сабтаска по идентификатору
     *
     * @param epicId
     * @param subtaskId
     */
    public void removeSubtaskById(int epicId, int subtaskId) {
        if (findEpicById(epicId) != null) {
            findEpicById(epicId).getSubtasks().remove(subtaskId);
        }
    }

    /**
     * Получение списка всех подзадач определённого эпика.
     *
     * @param epicId
     * @return список сабтасков
     */
    public ArrayList<Subtask> getAllSubtaskByEpicId(int epicId) {
        if (!epics.isEmpty()) {
            ArrayList<Subtask> subtasksList = new ArrayList<>();
            for (Subtask s : findEpicById(epicId).getSubtasks().values()) {
                subtasksList.add(s);
            }
            return subtasksList;
        }
        return null;
    }

    /**
     * Метод поиска эпика по его айди
     *
     * @param epicId
     * @return epic
     */
    public Epic findEpicById(int epicId) {
        if (!epics.isEmpty()) {
            if (epics.containsKey(epicId)) {
                for (Integer key : epics.keySet()) {
                    if (key.equals(epicId)) {
                        return epics.get(key);
                    }
                }
            }
        }
        return null;
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
