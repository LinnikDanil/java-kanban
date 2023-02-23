package ru.task.tracker.manager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.task.tracker.manager.tasks.Subtask;
import ru.task.tracker.manager.tasks.Task;
import ru.task.tracker.server.KVTaskClient;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HttpTaskManagerTest extends TaskManagerTest<HttpTaskManager> {

    KVTaskClient server;

    @Override
    @BeforeEach
    void createTasksForTest() {
        try {
            server = new KVTaskClient();
            server.start();
            testTaskManager = new HttpTaskManager();
            super.createTasksForTest();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void afterEach() {
        server.stop();
    }

    @Test
    void testMethodLoadFromEmptyServer() {
        HttpTaskManager httpTaskManager2 = HttpTaskManager.loadFromServer();

        List<Task> list = Collections.emptyList();
        assertArrayEquals(list.toArray(), httpTaskManager2.getAllTasks().toArray());
        assertArrayEquals(list.toArray(), httpTaskManager2.getAllEpics().toArray());
        assertArrayEquals(list.toArray(), httpTaskManager2.getAllSubtasks().toArray());
        assertArrayEquals(list.toArray(), httpTaskManager2.getHistoryManager().getHistory().toArray());
    }

    @Test
    void testMethodLoadFromServer() {
        int taskId = testTaskManager.createTask(task1);
        int epicId = testTaskManager.createEpic(epic1);
        subtask1.setEpicId(epicId);
        int subtaskId = testTaskManager.createSubtask(subtask1);
        testTaskManager.getTaskById(taskId);
        testTaskManager.getEpicById(epicId);
        testTaskManager.getSubtaskById(subtaskId);

        HttpTaskManager httpTaskManager = HttpTaskManager.loadFromServer();

        assertArrayEquals(testTaskManager.getAllTasks().toArray(), httpTaskManager.getAllTasks().toArray());
        assertArrayEquals(testTaskManager.getAllEpics().toArray(), httpTaskManager.getAllEpics().toArray());
        assertArrayEquals(testTaskManager.getAllSubtasks().toArray(), httpTaskManager.getAllSubtasks().toArray());
        assertArrayEquals(testTaskManager.getHistoryManager().getHistory().toArray(),
                httpTaskManager.getHistoryManager().getHistory().toArray());
    }
}