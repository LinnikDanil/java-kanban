package ru.task.tracker.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.task.tracker.manager.FileBackedTasksManager;
import ru.task.tracker.manager.InMemoryTaskManager;
import ru.task.tracker.manager.tasks.Epic;
import ru.task.tracker.manager.tasks.Subtask;
import ru.task.tracker.manager.tasks.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {
    @TempDir
    private Path directory;
    File file;
    @Override
    @BeforeEach
    void createTasksForTest() {
        try {
            file = Files.createFile(directory.resolve("testData.csv")).toFile();
            testTaskManager = FileBackedTasksManager.loadFromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.createTasksForTest();
    }

    @Test
    void testMethodLoadFromFile() {
        int taskId = testTaskManager.createTask(task1);
        int epicId = testTaskManager.createEpic(epic1);
        int subtaskId = testTaskManager.createSubtask(new Subtask("Test1", "Test1", epicId));
        testTaskManager.getTaskById(taskId);
        testTaskManager.getEpicById(epicId);
        testTaskManager.getSubtaskById(subtaskId);

        FileBackedTasksManager tasksManagerTest2 = FileBackedTasksManager.loadFromFile(file);

        assertArrayEquals(testTaskManager.getAllTasks().toArray(), tasksManagerTest2.getAllTasks().toArray());
        assertArrayEquals(testTaskManager.getAllEpics().toArray(), tasksManagerTest2.getAllEpics().toArray());
        assertArrayEquals(testTaskManager.getAllSubtasks().toArray(), tasksManagerTest2.getAllSubtasks().toArray());
        assertArrayEquals(testTaskManager.historyManager.getHistory().toArray(),
                          tasksManagerTest2.historyManager.getHistory().toArray());
    }
}