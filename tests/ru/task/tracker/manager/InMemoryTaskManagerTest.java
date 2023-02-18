package ru.task.tracker.manager;

import org.junit.jupiter.api.BeforeEach;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {
    @Override
    @BeforeEach
    void createTasksForTest() {
        testTaskManager = new InMemoryTaskManager();
        super.createTasksForTest();
    }
}