package ru.task.tracker.tests;

import org.junit.jupiter.api.BeforeEach;
import ru.task.tracker.manager.InMemoryTaskManager;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {
    @Override
    @BeforeEach
    void createTasksForTest() {
        testTaskManager = new InMemoryTaskManager();
        super.createTasksForTest();
    }
}