package ru.task.tracker.manager;

import ru.task.tracker.manager.tasks.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager{

    private ArrayList<Task> historyTasks;

    public InMemoryHistoryManager() {
        this.historyTasks = new ArrayList<>();
    }

    @Override
    public void add(Task task) {
        if (historyTasks.size() == 10){
            historyTasks.remove(0);
        }
        historyTasks.add(task);
    }

    @Override
    public ArrayList<Task> getHistory() {
        return historyTasks;
    }

}
