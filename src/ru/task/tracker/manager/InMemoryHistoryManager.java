package ru.task.tracker.manager;

import ru.task.tracker.manager.tasks.Task;

import java.util.ArrayList;

/**
 * Класс менеджера истории, имплементирующий интерфейс {@link HistoryManager}, отвечает за работу с историей полученных задач.
 */
public class InMemoryHistoryManager implements HistoryManager{

    private static final int HISTORY_SIZE = 10;
    private ArrayList<Task> historyTasks;

    public InMemoryHistoryManager() {
        this.historyTasks = new ArrayList<>();
    }

    @Override
    public void add(Task task) {
        if (historyTasks.size() == HISTORY_SIZE){
            historyTasks.remove(0);
        }
        historyTasks.add(task);
    }

    @Override
    public ArrayList<Task> getHistory() {
        return historyTasks;
    }

}
