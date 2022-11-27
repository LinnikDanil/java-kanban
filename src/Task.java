import java.util.Objects;

/**
 * Класс, отвечающий за создание задач.
 * Также является родителем для подклассов {@link Subtask} и {@link Epic}
 */
public class Task {
    final static String[] STATUSES = new String[]{"NEW", "IN_PROGRESS", "DONE"};
    private String name; //название задачи
    private String description; //описание задачи
    private int id;
    private String status;  /*NEW — задача только создана, но к её выполнению ещё не приступили.
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
        this.status = STATUSES[0];
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(int numberOfStatus) {
        this.status = STATUSES[numberOfStatus];
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

    public String getStatus() {
        return status;
    }

    //Используется для сравнения статусов в эпике (вроде бы)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(name, task.name) && Objects.equals(description, task.description) && Objects.equals(status, task.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, id, status);
    }

    //Используется для вывода в мейне
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
