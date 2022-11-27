import java.util.HashMap;

/**
 * Класс, отвечающий за создание епиков (задач с подзадачами).
 * Является дочерним классом {@link Task}
 */
public class Epic extends Task {
    private HashMap<Integer, Subtask> subtasks;

    public Epic(String name, String description) {
        super(name, description);
        subtasks = new HashMap<>();
    }

    public void putSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
    }

    public HashMap<Integer, Subtask> getSubtasks() {
        if (this != null) {
            return subtasks;
        }
        return null;
    }

    /**
     * метод обновления статуса эпика
     * -если у эпика нет подзадач или все они имеют статус NEW, то статус должен быть NEW.
     * -если все подзадачи имеют статус DONE, то и эпик считается завершённым — со статусом DONE.
     * -во всех остальных случаях статус должен быть IN_PROGRESS.
     *
     * @return статус
     */
    public String updateStatus() {
        int numberSubtasks = subtasks.size();
        int i = 0;
        for (Subtask s : subtasks.values()) {
            if (s.getStatus().equals(STATUSES[0])) {
                i++;
            }
        }
        if (i == numberSubtasks) {
            setStatus(0);
            return STATUSES[0];
        }
        i = 0;
        for (Subtask s : subtasks.values()) {
            if (s.getStatus().equals(STATUSES[2])) {
                i++;
            }
        }
        if (i == numberSubtasks) {
            setStatus(2);
            return STATUSES[2];
        }
        setStatus(1);
        return STATUSES[1];
    }

    //Используется для вывода в мейне
    @Override
    public String toString() {
        return "Epic{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status='" + getStatus() + '\'' +
                "subtasks=" + subtasks +
                '}';
    }
}
