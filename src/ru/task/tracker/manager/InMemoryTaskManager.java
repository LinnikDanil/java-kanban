package ru.task.tracker.manager;

import ru.task.tracker.manager.tasks.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Класс менеджера задач, имплементирующий интерфейс {@link TaskManager} со всей логикой работы, отвечающий за управление классами задач.
 */
public class InMemoryTaskManager implements TaskManager {

    protected int id;
    protected final HashMap<Integer, Task> tasks;
    protected final HashMap<Integer, Epic> epics;
    protected final HashMap<Integer, Subtask> subtasks;
    protected final TreeSet<Task> sortedTasks;
    private final HistoryManager historyManager;

    /**
     * Конструктор - создание нового объекта
     *
     * @see InMemoryTaskManager
     */
    public InMemoryTaskManager() {
        this.id = 0;
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subtasks = new HashMap<>();
        this.sortedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime));
        this.historyManager = Managers.getDefaultHistory();
    }

    @Override
    public HistoryManager getHistoryManager() {
        return historyManager;
    }

    @Override
    public List<Task> getPrioritizedTasks() {
        return List.copyOf(sortedTasks);
    }

    private boolean validatorTimeForTask(Task task) {
        if (sortedTasks.isEmpty()) return true;

        LocalDateTime timeStartTask = task.getStartTime();
        LocalDateTime timeEndTask = task.getEndTime();

        for (Task t : sortedTasks) {
            LocalDateTime tempTimeStartTask = t.getStartTime();
            LocalDateTime tempTimeEndTask = t.getEndTime();

            if (timeStartTask.isAfter(tempTimeEndTask) || timeEndTask.isBefore(tempTimeStartTask)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> tasksList = new ArrayList<>(tasks.values());
        return tasksList;
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        ArrayList<Epic> epicsList = new ArrayList<>(epics.values());
        return epicsList;
    }

    @Override
    public ArrayList<Subtask> getAllSubtasks() {
        ArrayList<Subtask> subtaskList = new ArrayList<>(subtasks.values());
        return subtaskList;
    }

    @Override
    public void clearAllTasks() {
        for (Task task : tasks.values()) {
            historyManager.remove(task.getId());
            sortedTasks.remove(task);
        }
        tasks.clear();
    }

    @Override
    public void clearAllEpics() {
        for (Task task : epics.values()) {
            historyManager.remove(task.getId());
        }
        for (Task task : subtasks.values()) {
            historyManager.remove(task.getId());
        }
        subtasks.clear();
        epics.clear();
    }

    @Override
    public void clearAllSubtasks() {
        for (Epic ep : epics.values()) {
            ep.clearSubtasks();
            updateStatusEpic(ep.getId());//Установка статуса - new
            updateLocalDateTimeForEpic(ep.getId());
        }
        for (Task task : subtasks.values()) {
            sortedTasks.remove(task);
            historyManager.remove(task.getId());
        }
        subtasks.clear();
    }

    @Override
    public Task getTaskById(int taskId) {
        if (tasks.containsKey(taskId)) {
            historyManager.add(tasks.get(taskId));
        }
        return tasks.get(taskId);
    }

    @Override
    public Epic getEpicById(int epicId) {
        if (epics.containsKey(epicId)) {
            historyManager.add(epics.get(epicId));
        }
        return epics.get(epicId);
    }

    @Override
    public Subtask getSubtaskById(int subtaskId) {
        if (subtasks.containsKey(subtaskId)) {
            historyManager.add(subtasks.get(subtaskId));
        }
        return subtasks.get(subtaskId);
    }

    @Override
    public int createTask(Task task) {
        if (validatorTimeForTask(task)) {
            task.setId(getNewId());
            tasks.put(task.getId(), task);
            sortedTasks.add(task);
            return task.getId();
        } else throw new RuntimeException("Время вашей задачи совпадает с уже существующей");
    }

    @Override
    public int createEpic(Epic epic) {
        epic.setId(getNewId());
        epics.put(epic.getId(), epic);
        return epic.getId();
    }

    @Override
    public int createSubtask(Subtask subtask) throws IllegalArgumentException {
        if (validatorTimeForTask(subtask)) {
            if (epics.containsKey(subtask.getEpicId())) {
                subtask.setId(getNewId());
                epics.get(subtask.getEpicId()).addSubtask(subtask.getId());
                subtasks.put(subtask.getId(), subtask);
                sortedTasks.add(subtask);
                updateStatusEpic(subtask.getEpicId());
                updateLocalDateTimeForEpic(subtask.getEpicId());
            } else throw new IllegalArgumentException("Создание сабтаска невозможно. Id эпика неверный");
            return subtask.getId();
        } else throw new RuntimeException("Время вашей задачи совпадает с уже существующей");
    }

    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            sortedTasks.remove(tasks.get(task.getId()));
            if (validatorTimeForTask(task)) {
                historyManager.remove(task.getId());
                tasks.put(task.getId(), task);
                sortedTasks.add(task);
            } else throw new RuntimeException("Время вашей задачи совпадает с уже существующей");

        } else throw new IllegalArgumentException("Обновление таска невозможно. Id неверный");
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            epic.setSubtasks(epics.get(epic.getId()).getSubtasks()); //Пермещение сабтасков со старого эпика в новый
            historyManager.remove(epic.getId());
            epics.put(epic.getId(), epic);
            updateStatusEpic(epic.getId());
            updateLocalDateTimeForEpic(epic.getId());
        } else throw new IllegalArgumentException("Обновление эпика невозможно. Id неверный");
    }

    @Override
    public void updateSubtask(Subtask subtask) throws IllegalArgumentException {
        if (subtasks.containsKey(subtask.getId())) {
            if (epics.containsKey(subtask.getEpicId())) {
                sortedTasks.remove(subtasks.get(subtask.getId()));
                if (validatorTimeForTask(subtask)) {
                    historyManager.remove(subtask.getId());
                    subtasks.put(subtask.getId(), subtask);
                    sortedTasks.add(subtask);
                    updateStatusEpic(subtask.getEpicId());
                    updateLocalDateTimeForEpic(subtask.getEpicId());
                } else throw new RuntimeException("Время вашей подзадачи совпадает с уже существующей");
            } else throw new IllegalArgumentException("Обновление сабтаска невозможно. Id эпика неверный");
        } else throw new IllegalArgumentException("Обновление cабтаска невозможно. Id неверный");
    }

    @Override
    public void removeTaskById(int taskId) {
        historyManager.remove(taskId);
        sortedTasks.remove(tasks.get(taskId));
        tasks.remove(taskId);
    }

    @Override
    public void removeEpicById(int epicId) {
        if (!subtasks.isEmpty()) {
            for (int idSubtask : epics.get(epicId).getSubtasks()) {
                historyManager.remove(idSubtask);
                subtasks.remove(idSubtask);
            }
        }
        epics.get(epicId).clearSubtasks();
        historyManager.remove(epicId);
        epics.remove(epicId);
    }

    @Override
    public void removeSubtaskById(int subtaskId) {
        if (subtasks.containsKey(subtaskId)) {
            int epicId = subtasks.get(subtaskId).getEpicId();
            if (epics.containsKey(epicId)) {
                sortedTasks.remove(subtasks.get(subtaskId));
                epics.get(epicId).removeSubtask(subtaskId); //удаление айди из списка в эпике
            }
            updateStatusEpic(epicId);
            updateLocalDateTimeForEpic(epicId);
        }
        historyManager.remove(subtaskId);
        subtasks.remove(subtaskId);
    }

    @Override
    public ArrayList<Subtask> getAllSubtaskByEpicId(int epicId) {
        ArrayList<Subtask> subtasksInEpic = new ArrayList<>();
        if (epics.containsKey(epicId)) {
            for (int i : epics.get(epicId).getSubtasks()) {
                subtasksInEpic.add(subtasks.get(i));
                historyManager.add(subtasks.get(i));
            }
        }
        return subtasksInEpic;
    }

    /**
     * метод обновления статуса эпика
     * -если у эпика нет подзадач или все они имеют статус NEW, то статус должен быть NEW.
     * -если все подзадачи имеют статус DONE, то и эпик считается завершённым — со статусом DONE.
     * -во всех остальных случаях статус должен быть IN_PROGRESS.
     *
     * @return статус
     */
    protected StatusesOfTask updateStatusEpic(int epicId) {
        if (!epics.isEmpty() && epics.containsKey(epicId)) {
            int numberSubtasks = epics.get(epicId).getSubtasks().size();
            int counterStatusNew = 0;
            int counterStatusDone = 0;
            for (int key : epics.get(epicId).getSubtasks()) {
                if (subtasks.get(key).getStatus().equals(StatusesOfTask.NEW)) {
                    counterStatusNew++;
                }
                if (subtasks.get(key).getStatus().equals(StatusesOfTask.DONE)) {
                    counterStatusDone++;
                }
            }
            if (counterStatusNew == numberSubtasks) {
                epics.get(epicId).setStatus(StatusesOfTask.NEW);
                return StatusesOfTask.NEW;
            } else if (counterStatusDone == numberSubtasks) {
                epics.get(epicId).setStatus(StatusesOfTask.DONE);
                return StatusesOfTask.DONE;
            }
            epics.get(epicId).setStatus(StatusesOfTask.IN_PROGRESS);
            return StatusesOfTask.IN_PROGRESS;
        }
        return null;
    }

    protected void updateLocalDateTimeForEpic(int epicId) {
        Duration sumDuration = Duration.ZERO;
        LocalDateTime startTime = LocalDateTime.MAX;
        LocalDateTime endTime = LocalDateTime.MIN;
        Epic tempEpic = epics.get(epicId);
        if (!tempEpic.getSubtasks().isEmpty()) {
            for (int idSubtask : tempEpic.getSubtasks()) {
                Subtask tempSubtask = subtasks.get(idSubtask);
                //StartTime
                if (tempSubtask.getStartTime() != null && tempSubtask.getStartTime().isBefore(startTime)) {
                    startTime = tempSubtask.getStartTime();
                }
                //EndTime
                if (tempSubtask.getEndTime() != null & tempSubtask.getEndTime().isAfter(endTime)) {
                    endTime = tempSubtask.getEndTime();
                }
                //Duration
                sumDuration = sumDuration.plus(tempSubtask.getDuration());
            }
            tempEpic.setDuration(sumDuration);
            tempEpic.setStartTime(startTime);
            tempEpic.setEndTime(endTime);
        } else {
            tempEpic.setDuration(Duration.ZERO);
            tempEpic.setStartTime(LocalDateTime.now().plusYears(100));
            tempEpic.setEndTime(tempEpic.getStartTime());
        }
    }

    /**
     * Метод получения нового айди
     *
     * @return айди
     */
    private int getNewId() {
        return ++id;
    }


}
