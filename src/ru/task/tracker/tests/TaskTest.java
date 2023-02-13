package ru.task.tracker.tests;

import org.junit.jupiter.api.Test;
import ru.task.tracker.manager.Managers;
import ru.task.tracker.manager.TaskManager;
import ru.task.tracker.manager.tasks.StatusesOfTask;
import ru.task.tracker.manager.tasks.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    TaskManager taskManager = Managers.getDefault();
    @Test
    void addNewTask() {
        Task task = new Task("Test addNewTask", "Test addNewTask description");
        final int taskId = taskManager.createTask(task);

        final Task savedTask = taskManager.getTaskById(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");


    }
}