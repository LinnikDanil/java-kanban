package ru.task.tracker.manager.tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

/**
 * Класс, отвечающий за создание задач.
 * Также является родителем для подклассов {@link Subtask} и {@link Epic}
 */
public class Task {
    protected TypeOfTasks type;
    private String name; //название задачи
    private String description; //описание задачи
    private int id;
    private StatusesOfTask status;  /*NEW — задача только создана, но к её выполнению ещё не приступили.
     *IN_PROGRESS — над задачей ведётся работа.
     *DONE — задача выполнена.*/
    private Duration duration; //Продолжительность задачи
    private LocalDateTime startTime; //Дата, когда предполагается приступить к выполнению задачи

    /**
     * Конструктор - создание нового объекта
     *
     * @param name
     * @param description
     */
    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = StatusesOfTask.NEW;
        this.duration = Duration.ZERO;
        this.startTime = LocalDateTime.of(1970, 1, 1, 0, 0);
        type = TypeOfTasks.TASK;
    }

    /**
     * Конструктор для обновления таска и сабтаска
     *
     * @param id
     * @param name
     * @param description
     * @param status
     */
    public Task(int id, String name, String description, StatusesOfTask status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.duration = Duration.ZERO;
        this.startTime = LocalDateTime.of(1970, 1, 1, 0, 0);
        type = TypeOfTasks.TASK;
    }

    public Task(int id, String name, String description, StatusesOfTask status,
                LocalDateTime startTime, Duration duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.duration = duration;
        this.startTime = startTime;
        type = TypeOfTasks.TASK;
    }
    /**
     * Конструктор для создания таска с временем
     *
     * @param name
     * @param description
     * @param startTime
     * @param duration
     */
    public Task(String name, String description, LocalDateTime startTime, Duration duration) {
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.duration = duration;
        this.status = StatusesOfTask.NEW;
        type = TypeOfTasks.TASK;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(StatusesOfTask status) {
        this.status = status;
    }

    public TypeOfTasks getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public StatusesOfTask getStatus() {
        return status;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setType(TypeOfTasks type) {
        this.type = type;
    }

    public LocalDateTime getEndTime() {
        LocalDateTime endTime = startTime.plus(duration);
        return endTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null && this.getClass() != obj.getClass()) return false;
        Task otherTask = (Task) obj;
        return Objects.equals(id, otherTask.id) &&
                Objects.equals(name, otherTask.name) &&
                Objects.equals(description, otherTask.description) &&
                Objects.equals(status, otherTask.status) &&
                Objects.equals(type, otherTask.type);
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status='" + status + '\'' +
                ", startTime='" + startTime.toString() + '\'' +
                ", duration='" + duration.toString() + '\'' +
                '}';
    }

    public String toCsv() {
        //id,type,name,status,description,startTime,duration,epic
        return String.format("%d,%s,%s,%s,%s,%s,%s", getId(), getType().toString(), getName(),
                getStatus().toString(), getDescription(), getStartTime().toString(), getDuration().toString());
    }
}
