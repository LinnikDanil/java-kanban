package ru.task.tracker.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.task.tracker.manager.HistoryManager;
import ru.task.tracker.manager.Managers;
import ru.task.tracker.manager.tasks.Epic;
import ru.task.tracker.manager.tasks.StatusesOfTask;
import ru.task.tracker.manager.tasks.Subtask;
import ru.task.tracker.manager.tasks.Task;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {
    HistoryManager historyManager;

    @BeforeEach
    void beforeEach() {
        historyManager = Managers.getDefaultHistory();
    }

    @Test
    void getEmptyHistory() {
        List<Task> emptyList = Collections.emptyList();
        assertArrayEquals(historyManager.getHistory().toArray(), emptyList.toArray());
        Task task = new Task(1, "test1", "test1", StatusesOfTask.NEW);
        historyManager.add(task);
        historyManager.remove(task.getId());
        assertArrayEquals(historyManager.getHistory().toArray(), emptyList.toArray());
    }

    @Test
    void getHistoryWithTwoElements() {
        Task task = new Task(1, "test1", "test1", StatusesOfTask.NEW);
        Epic epic = new Epic(2,"test2", "test2", StatusesOfTask.NEW);
        historyManager.add(task);
        historyManager.add(epic);
        LinkedList<Task> listOfTasks = new LinkedList<>();
        listOfTasks.add(epic);
        listOfTasks.add(task);
        assertArrayEquals(historyManager.getHistory().toArray(), listOfTasks.toArray());
    }

    @Test
    void removeElementsInHistory() {
        Task task = new Task(1, "test1", "test1", StatusesOfTask.NEW);
        Epic epic = new Epic(2,"test2", "test2", StatusesOfTask.NEW);
        Subtask subtask = new Subtask(3, "test3", "test3", StatusesOfTask.NEW, epic.getId());
        historyManager.add(task);
        historyManager.add(epic);
        historyManager.add(subtask);
        List<Task> list = List.of(subtask, task);
        historyManager.remove(epic.getId());
        assertArrayEquals(historyManager.getHistory().toArray(), list.toArray()); // Середина
        historyManager.add(epic);
        historyManager.remove(task.getId());
        list = List.of(epic, subtask);
        assertArrayEquals(historyManager.getHistory().toArray(), list.toArray()); //Конец
        historyManager.add(task);
        historyManager.remove(task.getId());
        assertArrayEquals(historyManager.getHistory().toArray(), list.toArray()); //Начало
    }

    @Test
    void checkAddSecondElementWithOneId() {
        Task task = new Task(1, "test1", "test1", StatusesOfTask.NEW);
        Epic epic = new Epic(2,"test2", "test2", StatusesOfTask.NEW);
        historyManager.add(task);
        historyManager.add(epic);
        LinkedList<Task> listOfTasks = new LinkedList<>();
        listOfTasks.add(task);
        listOfTasks.add(epic);
        historyManager.add(task);
        assertArrayEquals(historyManager.getHistory().toArray(), listOfTasks.toArray());
    }

    @Test
    void removeEmptyHistoryOrWrongId() {
        assertDoesNotThrow(() -> historyManager.remove(-1));
    }
}