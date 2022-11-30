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
    public Subtask(String name, String description) {
        super(name, description);
    }

    public void setEpicId(int epicId) {

        this.epicId = epicId;
    }
    public Integer getEpicId() {
        return epicId;
    }

    //Используется для вывода в мейне
    @Override
    public String toString() {
        return "Subtask{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status='" + getStatus() + '\'' +
                '}';
    }


}
