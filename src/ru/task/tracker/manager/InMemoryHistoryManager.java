package ru.task.tracker.manager;

import ru.task.tracker.manager.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Класс менеджера истории, имплементирующий интерфейс {@link HistoryManager}, отвечает за работу с историей полученных задач.
 */
public class InMemoryHistoryManager implements HistoryManager {

    private final HashMap<Integer, Node<Task>> nodes;
    private final ArrayList<Task> historyTasks;

    public InMemoryHistoryManager() {
        this.historyTasks = new ArrayList<>();
        this.nodes = new HashMap<>();
    }

    @Override
    public void add(Task task) {
        if (nodes.containsKey(task.getId())){
            removeNode(nodes.get(task.getId()));
        }
        linkLast(task);
    }

    @Override
    public void remove(Task task) {
        if (nodes.containsKey(task.getId())){
            removeNode(nodes.get(task.getId()));
            nodes.remove(task.getId());
        }
    }

    @Override
    public ArrayList<Task> getHistory() {
        historyTasks.clear();
        historyTasks.addAll(getTasks());
        return historyTasks;
    }
    class Node<E> {
        public E data;
        public Node<E> next;
        public Node<E> prev;

        public Node(Node<E> prev, E data, Node<E> next){
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    /**
     * Указатель на первый элемент списка. Он же first
     */
    private Node<Task> head;

    /**
     * Указатель на последний элемент списка. Он же last
     */
    private Node<Task> tail;

    private int size = 0;

    private void linkLast(Task task){
        final Node<Task> oldTail = tail;
        final Node<Task> newNode = new Node<>(oldTail, task, null);
        tail = newNode;
        if (oldTail == null){
            head = newNode;
        }
        else{
            oldTail.next = newNode;
        }
        size++;
        nodes.put(task.getId(), tail);
    }

    private void removeNode(Node<Task> x){
        final Node<Task> next = x.next;
        final Node<Task> prev = x.prev;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.data = null;
        size--;
    }
    public ArrayList<Task> getTasks(){
        ArrayList<Task> tasksInHistory = new ArrayList<>();
        for (Node<Task> node = tail; node != null; node = node.prev){
            tasksInHistory.add(node.data);
        }
        return tasksInHistory;
    }
}
