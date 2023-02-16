package ru.task.tracker.manager.tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Класс, отвечающий за создание епиков (задач с подзадачами).
 * Является дочерним классом {@link Task}
 */
public class Epic extends Task {
    private LocalDateTime endTime;
    private final ArrayList<Integer> subtasks;

    /**
     * Конструктор - создание объекта
     * @param name
     * @param description
     */
    public Epic(String name, String description) {
        super(name, description);
        subtasks = new ArrayList<>();
        type = TypeOfTasks.EPIC;
    }

    /**
     * Конструктор - обновление объекта
     * @param id
     * @param name
     * @param description
     */
    public Epic(int id, String name, String description) {
        super(id, name, description, StatusesOfTask.NEW);
        subtasks = new ArrayList<>();
        type = TypeOfTasks.EPIC;
    }

    /**
     * Конструктор - загрузка эпика из файла
     * @param id
     * @param name
     * @param description
     * @param statusesOfTask
     */
    public Epic(int id, String name, String description, StatusesOfTask statusesOfTask) {
        super(id, name, description, statusesOfTask);
        subtasks = new ArrayList<>();
        type = TypeOfTasks.EPIC;
    }
    public Epic(int id, String name, String description,
                StatusesOfTask statusesOfTask, LocalDateTime startTime, Duration duration) {
        super(id, name, description, statusesOfTask, startTime, duration);
        subtasks = new ArrayList<>();
        type = TypeOfTasks.EPIC;
    }

    public void addSubtask(int SubtaskId) {
        subtasks.add(SubtaskId);
    }

    public ArrayList<Integer> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(ArrayList<Integer> subtasks) {
        for(int subtask : subtasks){
            this.subtasks.add(subtask);
        }
    }

    public void removeSubtask(Integer subtaskId){
        subtasks.remove(subtaskId);
    }

    public void clearSubtasks(){
        subtasks.clear();
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status='" + getStatus() + '\'' +
                ", startTime='" + getStartTime().toString() + '\'' +
                ", duration='" + getDuration().toString() + '\'' +
                "subtasks=" + subtasks +
                '}';
    }
}
