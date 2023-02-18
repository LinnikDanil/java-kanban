package ru.task.tracker.manager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.task.tracker.manager.tasks.Subtask;
import ru.task.tracker.manager.tasks.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

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
            super.createTasksForTest();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void afterEach(){
        testTaskManager.clearAllTasks();
        testTaskManager.clearAllEpics();
        testTaskManager.clearAllSubtasks();
        file.delete();
    }

    @Test
    void testTreeSet(){
        int t1 = testTaskManager.createTask(new Task("Test1", "Test1",
                LocalDateTime.of(2020, 1, 1, 1, 1), Duration.ofHours(1)));
        int t2 = testTaskManager.createTask(new Task("Test2", "Test2",
                LocalDateTime.of(2019, 1, 1, 1, 1), Duration.ofHours(1)));
        int t3 = testTaskManager.createTask(new Task("Test3", "Test3",
                LocalDateTime.of(2014, 1, 1, 1, 1), Duration.ofHours(1)));
        int t4 = testTaskManager.createTask(new Task("Test4", "Test4",
                LocalDateTime.of(2030, 1, 1, 1, 1), Duration.ofHours(1)));
        List<Task> list = List.of(
                testTaskManager.getTaskById(t3),
                testTaskManager.getTaskById(t2),
                testTaskManager.getTaskById(t1),
                testTaskManager.getTaskById(t4)
        );
        assertArrayEquals(testTaskManager.getPrioritizedTasks().toArray(), list.toArray());
    }

    @Test
    void testMethodLoadFromEmptyFile() {
        FileBackedTasksManager tasksManagerTest2 = FileBackedTasksManager.loadFromFile(file);

        List<Task> list = Collections.emptyList();
        assertArrayEquals(list.toArray(), tasksManagerTest2.getAllTasks().toArray());
        assertArrayEquals(list.toArray(), tasksManagerTest2.getAllEpics().toArray());
        assertArrayEquals(list.toArray(), tasksManagerTest2.getAllSubtasks().toArray());
        assertArrayEquals(list.toArray(), tasksManagerTest2.historyManager.getHistory().toArray());
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