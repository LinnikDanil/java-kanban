package ru.task.tracker.manager.tasks;

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
        type = TypeOfTasks.TASK;
    }

    /**
     * Конструктор для обновления таска и сабтаска
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

    @Override
    public String toString() {
        return String.format("%d,%s,%s,%s,%s,",getId(), getType().toString(),getName(),
                getStatus().toString(),getDescription());
    }
}
