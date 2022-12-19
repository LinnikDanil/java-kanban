package ru.task.tracker.manager;

import ru.task.tracker.manager.tasks.Task;

import java.util.LinkedList;

/**
 * Класс менеджера истории, имплементирующий интерфейс {@link HistoryManager}, отвечает за работу с историей полученных задач.
 */
public class InMemoryHistoryManager implements HistoryManager{

    private static final int HISTORY_SIZE = 10;
    private final LinkedList<Task> historyTasks;

    public InMemoryHistoryManager() {
        this.historyTasks = new LinkedList<>();
    }

    @Override
    public void add(Task task) {
        if (historyTasks.size() == HISTORY_SIZE){
            historyTasks.removeLast();
        }
        historyTasks.addFirst(task);
    }

    @Override
    public LinkedList<Task> getHistory() {
        return historyTasks;
    }

}
