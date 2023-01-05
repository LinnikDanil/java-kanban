package ru.task.tracker.manager;

import ru.task.tracker.manager.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Класс менеджера истории, имплементирующий интерфейс {@link HistoryManager}, отвечает за работу с историей полученных задач.
 */
public class InMemoryHistoryManager implements HistoryManager {

    private final CustomLinkedList historyTasks;
    private final HashMap<Integer, CustomLinkedList.Node> nodes = new HashMap<>();

    public InMemoryHistoryManager() {
        this.historyTasks = new CustomLinkedList();
    }

    @Override
    public void add(Task task) {
        if (nodes.containsKey(task.getId())){
            historyTasks.removeNode(nodes.get(task.getId()));
        }
        historyTasks.linkLast(task);
    }

    @Override
    public void remove(int id) {
        if (nodes.containsKey(id)){
            historyTasks.removeNode(nodes.get(id));
            nodes.remove(id);
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyTasks.getTasks();
    }

    public class CustomLinkedList{
        class Node {
            public Task data;
            public Node next;
            public Node prev;

            public Node(Node prev, Task data, Node next){
                this.data = data;
                this.next = next;
                this.prev = prev;
            }
        }

        /**
         * Указатель на первый элемент списка. Он же first
         */
        private Node head;

        /**
         * Указатель на последний элемент списка. Он же last
         */
        private Node tail;

        private int size = 0;

        private void linkLast(Task task){
            final Node oldTail = tail;
            final Node newNode = new Node(oldTail, task, null);
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

        private void removeNode(Node x){
            final Node next = x.next;
            final Node prev = x.prev;

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
            for (Node node = tail; node != null; node = node.prev){
                tasksInHistory.add(node.data);
            }
            return tasksInHistory;
        }
    }
}
