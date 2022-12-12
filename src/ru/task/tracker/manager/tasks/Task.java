package ru.task.tracker.manager.tasks;

/**
 * Класс, отвечающий за создание задач.
 * Также является родителем для подклассов {@link Subtask} и {@link Epic}
 */
public class Task {
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
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(StatusesOfTask status) {
        this.status = status;
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
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status='" + status + '\'' +
                '}';
    }
}
