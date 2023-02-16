package ru.task.tracker.manager.tasks;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Класс, отвечающий за создание подзадач для класса {@link Epic}.
 * Является дочерним классом {@link Task}
 */
public class Subtask extends Task {

    private Integer epicId; //Знаем, к какому эпику принадлежим

    /**
     * Конструктор - создание объекта
     *
     * @param name
     * @param description
     */
    public Subtask(String name, String description, int epicId) {
        super(name, description);
        setType(TypeOfTasks.SUBTASK);
        this.epicId = epicId;
    }

    /**
     * Конструктор - обновление метода
     * @param id
     * @param name
     * @param description
     * @param status
     * @param epicId
     */
    public Subtask(int id, String name, String description, StatusesOfTask status, int epicId) {
        super(id, name, description, status);
        setType(TypeOfTasks.SUBTASK);
        this.epicId = epicId;
    }

    public Subtask(int id, String name, String description, StatusesOfTask status, int epicId,
                   LocalDateTime startTime, Duration duration) {
        super(id, name, description, status, startTime, duration);
        setType(TypeOfTasks.SUBTASK);
        this.epicId = epicId;
    }

    public Subtask(String name, String description, int epicId, LocalDateTime startTime, Duration duration) {
        super(name, description, startTime, duration);
        setType(TypeOfTasks.SUBTASK);
        this.epicId = epicId;
    }

    public Integer getEpicId() {
        return epicId;
    }

    public void setEpicId(Integer epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status='" + getStatus() + '\'' +
                ", startTime='" + getStartTime().toString() + '\'' +
                ", duration='" + getDuration().toString() + '\'' +
                '}';

    }

    @Override
    public String toCsv() {
        return String.format("%d,%s,%s,%s,%s,%s,%s,%d",
                getId(), getType().toString(),getName(), getStatus().toString(),getDescription(),
                getStartTime().toString(), getDuration().toString(), getEpicId());
    }
}
