package ru.task.tracker.manager.tasks;

/**
 * Класс, отвечающий за создание подзадач для класса {@link Epic}.
 * Является дочерним классом {@link Task}
 */
public class Subtask extends Task {

    private Integer epicId; //Знаем, к какому эпику пренадлежим

    /**
     * Конструктор - создание объекта
     *
     * @param name
     * @param description
     */
    public Subtask(String name, String description, int epicId) {
        super(name, description);
        this.epicId = epicId;
        type = TypeOfTasks.SUBTASK;
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
        this.epicId = epicId;
        type = TypeOfTasks.SUBTASK;
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
                '}';

    }

    @Override
    public String toCsv() {
        return String.format("%d,%s,%s,%s,%s,%d",getId(), getType().toString(),getName(), getStatus().toString(),getDescription(), getEpicId());
    }
}
