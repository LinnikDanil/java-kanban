package ru.task.tracker.manager.tasks;

import java.util.ArrayList;

/**
 * Класс, отвечающий за создание епиков (задач с подзадачами).
 * Является дочерним классом {@link Task}
 */
public class Epic extends Task {

    private final ArrayList<Integer> subtasks;

    /**
     * Конструктор - создание объекта
     * @param name
     * @param description
     */
    public Epic(String name, String description) {
        super(name, description);
        subtasks = new ArrayList<>();
        type = TypeOfTasks.EPIC;
    }

    /**
     * Конструктор - обновление объекта
     * @param id
     * @param name
     * @param description
     */
    public Epic(int id, String name, String description) {
        super(id, name, description, StatusesOfTask.NEW);
        subtasks = new ArrayList<>();
        type = TypeOfTasks.EPIC;
    }

    /**
     * Конструктор - загрузка эпика из файла
     * @param id
     * @param name
     * @param description
     * @param statusesOfTask
     */
    public Epic(int id, String name, String description, StatusesOfTask statusesOfTask) {
        super(id, name, description, statusesOfTask);
        subtasks = new ArrayList<>();
        type = TypeOfTasks.EPIC;
    }

    public void addSubtask(int SubtaskId) {
        subtasks.add(SubtaskId);
    }

    public ArrayList<Integer> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(ArrayList<Integer> subtasks) {
        for(int subtask : subtasks){
            this.subtasks.add(subtask);
        }
    }

    public void removeSubtask(Integer subtaskId){
        subtasks.remove(subtaskId);
    }

    public void clearSubtasks(){
        subtasks.clear();
    }

}
