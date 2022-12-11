package ru.task.tracker.manager.tasks;

import java.util.ArrayList;

/**
 * Класс, отвечающий за создание епиков (задач с подзадачами).
 * Является дочерним классом {@link Task}
 */
public class Epic extends Task {
    private ArrayList<Integer> subtasks;

    public Epic(String name, String description) {
        super(name, description);
        subtasks = new ArrayList<>();
    }

    public void addSubtask(int SubtaskId) {
        subtasks.add(SubtaskId);
    }

    public ArrayList<Integer> getSubtasks() {
        return subtasks;
    }
    public void removeSubtask(Integer subtaskId){
        subtasks.remove(subtaskId);
    }

    public void clearSubtasks(){
        subtasks.clear();
    }

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
