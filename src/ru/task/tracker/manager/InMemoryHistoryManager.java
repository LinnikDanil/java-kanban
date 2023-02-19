package ru.task.tracker.manager;

import ru.task.tracker.manager.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Класс менеджера истории, имплементирующий интерфейс {@link HistoryManager}, отвечает за работу с историей полученных задач.
 */
public class InMemoryHistoryManager implements HistoryManager {

    /**
     * Список для хранения истории задач
     */
    private final CustomLinkedList historyTasks;
    /**
     * Хранение узлов кастомного списка
     */
    private final HashMap<Integer, CustomLinkedList.Node> nodes = new HashMap<>();

    /**
     * Конструктор - создание класса
     */
    public InMemoryHistoryManager() {
        this.historyTasks = new CustomLinkedList();
    }

    /**
     * Преобразование истории в строку из айди
     * @param manager
     * @return
     */
    public static String historyToString(HistoryManager manager) {
        return manager.getHistory()
                .stream()
                .map(Task::getId)
                .map(Objects::toString)
                .collect(Collectors.joining(","));
    }
    /**
     * Преобразование строки в список айди тасков из истории
     * @param value
     * @return
     */
    static List<Integer> historyFromString(String value) {
        List<Integer> history = new ArrayList<>();
        if (!value.isBlank()) {
            String[] splitHistory = value.split(",");
            for (String s : splitHistory) {
                history.add(Integer.parseInt(s));
            }
        }
        return history;
    }

    @Override
    public void add(Task task) {
        if (nodes.containsKey(task.getId())) {
            historyTasks.removeNode(nodes.get(task.getId()));
        }
        historyTasks.linkLast(task);
    }

    @Override
    public void remove(int id) {
        if (nodes.containsKey(id)) {
            historyTasks.removeNode(nodes.get(id));
            nodes.remove(id);
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyTasks.getTasks();
    }

    /**
     * Класс создания кастомного двусвязного листа
     */
    public class CustomLinkedList {
        /**
         * Вспомогательный класс для создания узлов списка
         */
        class Node {
            private Task data;
            private Node next;
            private Node prev;

            /**
             * Конструктор вспомогательного класса
             * @param prev
             * @param data
             * @param next
             */
            public Node(Node prev, Task data, Node next) {
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

        /**
         * Положить файл в конец списка
         * @param task
         */
        private void linkLast(Task task) {
            final Node oldTail = tail;
            final Node newNode = new Node(oldTail, task, null);
            tail = newNode;
            if (oldTail == null) {
                head = newNode;
            } else {
                oldTail.next = newNode;
            }
            size++;
            nodes.put(task.getId(), tail);
        }

        /**
         * Удалить узел из списка
         * @param x
         */
        private void removeNode(Node x) {
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

        /**
         * Получить все таски из списка
         * @return
         */
        public ArrayList<Task> getTasks() {
            ArrayList<Task> tasksInHistory = new ArrayList<>();
            for (Node node = tail; node != null; node = node.prev) {
                tasksInHistory.add(node.data);
            }
            return tasksInHistory;
        }
    }
}
