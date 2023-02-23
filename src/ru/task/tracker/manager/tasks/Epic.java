package ru.task.tracker.manager.tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Класс, отвечающий за создание епиков (задач с подзадачами).
 * Является дочерним классом {@link Task}
 */
public class Epic extends Task {
    private LocalDateTime endTime;
    private final ArrayList<Integer> subtasks;

    /**
     * Конструктор создания эпика
     *
     * @param name
     * @param description
     */
    public Epic(String name, String description) {
        super(
                name,
                description,
                LocalDateTime.now().plusYears(100),
                Duration.ZERO,
                TypeOfTasks.EPIC
        );
        subtasks = new ArrayList<>();
        endTime = getStartTime();
    }

    /**
     * Конструктор обновления эпика
     *
     * @param id
     * @param name
     * @param description
     */
    public Epic(int id, String name, String description) {
        super(
                id,
                name,
                description,
                StatusesOfTask.NEW,
                LocalDateTime.now().plusYears(100),
                Duration.ZERO,
                TypeOfTasks.EPIC
        );
        subtasks = new ArrayList<>();
        endTime = getStartTime();
    }

    public void addSubtask(int SubtaskId) {
        subtasks.add(SubtaskId);
    }

    public ArrayList<Integer> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(ArrayList<Integer> subtasks) {
        for (int subtask : subtasks) {
            this.subtasks.add(subtask);
        }
    }

    public void removeSubtask(Integer subtaskId) {
        subtasks.remove(subtaskId);
    }

    public void clearSubtasks() {
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
