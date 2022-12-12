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
    }

    public void setEpicId(int epicId) {

        this.epicId = epicId;
    }
    public Integer getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status='" + getStatus() + '\'' +
                ", epicId='" + getEpicId() + '\'' +
                '}';
    }


}
