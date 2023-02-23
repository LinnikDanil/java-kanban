package ru.task.tracker.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.task.tracker.manager.tasks.Epic;
import ru.task.tracker.manager.tasks.StatusesOfTask;
import ru.task.tracker.manager.tasks.Subtask;
import ru.task.tracker.manager.tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest<T extends TaskManager> {
    protected T testTaskManager;
    protected Task task1;
    protected Task task2;
    protected Epic epic1;
    protected Epic epic2;
    protected Subtask subtask1;
    protected Subtask subtask2;

    @BeforeEach
    void createTasksForTest() {
        testTaskManager.clearAllTasks();
        testTaskManager.clearAllEpics();
        task1 = new Task("TestTask1", "DescriptionForTask1",
                LocalDateTime.of(2023, 01, 01, 01, 1), Duration.ofHours(1));
        task2 = new Task("TestTask2", "DescriptionForTask2",
                LocalDateTime.of(2023, 02, 01, 01, 1), Duration.ofHours(1));
        epic1 = new Epic("TestEpic1", "DescriptionForEpic1");
        epic2 = new Epic("TestEpic2", "DescriptionForEpic2");
        subtask1 = new Subtask("TestSubtask1", "DescriptionForSubtask1", 0,
                LocalDateTime.of(2023, 03, 01, 01, 1), Duration.ofHours(1));
        subtask2 = new Subtask("TestSubtask2", "DescriptionForSubtask2", 0,
                LocalDateTime.of(2023, 04, 01, 01, 1), Duration.ofHours(1));
    }

    void putTasksInTaskManager() {
        testTaskManager.createTask(task1);
        testTaskManager.createTask(task2);
    }

    void putEpicsInEpicManager() {
        testTaskManager.createEpic(epic1);
        testTaskManager.createEpic(epic2);
    }

    void putSubtasksInSubtaskManager() {
        int epic1Id = testTaskManager.createEpic(epic1);
        subtask1.setEpicId(epic1Id);
        subtask2.setEpicId(epic1Id);
        testTaskManager.createSubtask(subtask1);
        testTaskManager.createSubtask(subtask2);
    }

    //getAllTasks
    @Test
    void testMethodGetAllTasks() {
        putTasksInTaskManager();
        List<Task> listOfTasks = List.of(task1, task2);
        assertArrayEquals(listOfTasks.toArray(), testTaskManager.getAllTasks().toArray());
    }

    @Test
    void testMethodGetAllTasksWithEmptyTasks() {
        List<Task> emptyListOfTasks = Collections.emptyList();
        assertArrayEquals(emptyListOfTasks.toArray(), testTaskManager.getAllTasks().toArray());

    }

    //getAllEpics
    @Test
    void testMethodGetAllEpics() {
        putEpicsInEpicManager();
        List<Epic> listOfEpics = List.of(epic1, epic2);
        assertArrayEquals(listOfEpics.toArray(), testTaskManager.getAllEpics().toArray());
    }

    @Test
    void testMethodGetAllEpicsWithEmptyEpics() {
        List<Epic> emptyListOfEpics = Collections.emptyList();
        assertArrayEquals(emptyListOfEpics.toArray(), testTaskManager.getAllEpics().toArray());
    }

    //getAllSubtask
    @Test
    void testMethodGetAllSubtasks() {
        putSubtasksInSubtaskManager();
        List<Subtask> listOfSubtasks = List.of(subtask1, subtask2);
        assertArrayEquals(listOfSubtasks.toArray(), testTaskManager.getAllSubtasks().toArray());
    }

    @Test
    void testMethodGetAllSubtasksWithEmptySubtasks() {
        List<Subtask> emptyListOfSubtasks = Collections.emptyList();
        assertArrayEquals(emptyListOfSubtasks.toArray(), testTaskManager.getAllSubtasks().toArray());
    }

    //clearAllTasks
    @Test
    void testMethodClearAllTasks() {
        putTasksInTaskManager();
        testTaskManager.clearAllTasks();
        List<Task> emptyListOfTasks = Collections.emptyList();
        assertArrayEquals(emptyListOfTasks.toArray(), testTaskManager.getAllTasks().toArray());
    }

    @Test
    void testMethodClearAllTasksWithEmptyTasks() {
        testTaskManager.clearAllTasks();
        List<Task> emptyListOfTasks = Collections.emptyList();
        assertArrayEquals(emptyListOfTasks.toArray(), testTaskManager.getAllTasks().toArray());
    }

    //clearAllEpics
    @Test
    void testMethodClearAllEpics() {
        putEpicsInEpicManager();
        testTaskManager.clearAllEpics();
        List<Epic> emptyListOfEpics = Collections.emptyList();
        assertArrayEquals(emptyListOfEpics.toArray(), testTaskManager.getAllEpics().toArray());
    }

    @Test
    void testMethodClearAllEpicsWithEmptyEpics() {
        testTaskManager.clearAllEpics();
        List<Epic> emptyListOfEpics = Collections.emptyList();
        assertArrayEquals(emptyListOfEpics.toArray(), testTaskManager.getAllEpics().toArray());
    }

    //clearAllTSubtasks();
    @Test
    void testMethodClearAllSubtasks() {
        putSubtasksInSubtaskManager();
        testTaskManager.clearAllSubtasks();
        List<Subtask> listOfSubtasks = Collections.emptyList();
        assertArrayEquals(listOfSubtasks.toArray(), testTaskManager.getAllSubtasks().toArray());
    }

    @Test
    void testMethodClearAllSubtasksWithClearAllEpics() {
        putSubtasksInSubtaskManager();
        testTaskManager.clearAllEpics();
        List<Subtask> listOfSubtasks = Collections.emptyList();
        assertArrayEquals(listOfSubtasks.toArray(), testTaskManager.getAllSubtasks().toArray());
    }

    @Test
    void testMethodClearAllSubtasksWithEmptySubtasks() {
        testTaskManager.clearAllEpics();
        List<Subtask> listOfSubtasks = Collections.emptyList();
        assertArrayEquals(listOfSubtasks.toArray(), testTaskManager.getAllSubtasks().toArray());
    }

    //getTaskById and createTask
    @Test
    void testMethodGetTaskByIdAndMethodCreateTask() {
        Task testTask = new Task("Task1", "TestForGetTaskById");
        int testTaskId = testTaskManager.createTask(testTask);
        assertEquals(testTaskManager.getTaskById(testTaskId), testTask);
    }

    @Test
    void testMethodGetTaskByIdWithWrongIdOrEmptyTasks() {
        assertEquals(testTaskManager.getTaskById(-1), null);
    }

    //getEpicById and createEpic
    @Test
    void testMethodGetEpicByIdAndMethodCreateEpic() {
        Epic testEpic = new Epic("Epic1", "EpicForGetEpicById");
        int testEpicId = testTaskManager.createEpic(testEpic);
        assertEquals(testTaskManager.getEpicById(testEpicId), testEpic);
    }

    @Test
    void testMethodGetEpicByIdWithWrongIdOrEmptyEpics() {
        assertEquals(testTaskManager.getEpicById(-1), null);
    }

    //getSubtaskById andCreateSubtask
    @Test
    void testMethodGetSubtaskByIdAndMethodCreateSubtask() {
        int testEpicId = testTaskManager.createEpic(epic1);
        subtask1.setEpicId(testEpicId);
        int testSubtaskId = testTaskManager.createSubtask(subtask1);
        assertEquals(testTaskManager.getSubtaskById(testSubtaskId), subtask1);
    }

    @Test
    void testMethodGetSubtaskByIdWithWrongIdOrEmptySubtasks() {
        assertEquals(testTaskManager.getSubtaskById(-1), null);
    }

    //updateTask
    @Test
    void testMethodUpdateTask() {
        int idOldTask = testTaskManager.createTask(new Task("TestTaskOld", "TestOld"));
        Task testTaskNew = new Task(idOldTask, "TestTaskNew", "TestNew", StatusesOfTask.NEW);
        testTaskManager.updateTask(testTaskNew);
        assertEquals(testTaskNew, testTaskManager.getTaskById(idOldTask));
    }

    @Test
    void testMethodUpdateTaskWithEmptyTasksOrWrongId() {
        Task testTaskNew = new Task(-1, "TestTaskNew", "TestNew", StatusesOfTask.NEW);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> testTaskManager.updateTask(testTaskNew)
        );
        assertEquals(exception.getMessage(), "Обновление таска невозможно. Id неверный");
    }

    //updateEpic
    @Test
    void testMethodUpdateEpic() {
        int idOldEpic = testTaskManager.createEpic(new Epic("TestEpicOld", "TestOld"));
        Epic testEpicNew = new Epic(idOldEpic, "TestEpicNew", "TestNew");
        testTaskManager.updateEpic(testEpicNew);
        assertEquals(testEpicNew, testTaskManager.getEpicById(idOldEpic));
    }

    @Test
    void testMethodUpdateEpicWithEmptyEpicsOrWrongId() {
        Epic testEpicNew = new Epic(-1, "TestEpicNew", "TestNew");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> testTaskManager.updateEpic(testEpicNew)
        );
        assertEquals(exception.getMessage(), "Обновление эпика невозможно. Id неверный");
    }

    //updateSubtask
    @Test
    void testMethodUpdateSubtask() {
        int idEpic = testTaskManager.createEpic(new Epic("TestEpic", "TestOld"));
        subtask1.setEpicId(idEpic);
        int idOldSubtask = testTaskManager.createSubtask(subtask1);
        Subtask testSubtaskNew = new Subtask(
                idOldSubtask, "TestSubtaskNew", "TestNew", idEpic,
                StatusesOfTask.NEW, LocalDateTime.now(), Duration.ZERO);
        testTaskManager.updateSubtask(testSubtaskNew);
        assertEquals(testSubtaskNew, testTaskManager.getSubtaskById(idOldSubtask));
    }

    @Test
    void testMethodUpdateSubtaskWithEmptySubtasksOrWrongId() {
        Subtask testSubtaskNew = new Subtask(
                -1, "TestSubtaskNew", "TestNew", -1, StatusesOfTask.NEW);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> testTaskManager.updateSubtask(testSubtaskNew)
        );
        assertEquals(exception.getMessage(), "Обновление cабтаска невозможно. Id неверный");
    }

    //removeTaskById
    @Test
    void testMethodRemoveTaskById() {
        int idTask = testTaskManager.createTask(new Task("TestTask", "Test"));
        testTaskManager.removeTaskById(idTask);
        assertEquals(testTaskManager.getTaskById(idTask), null);
    }

    //removeEpicById
    @Test
    void testMethodRemoveEpicById() {
        int idEpic = testTaskManager.createEpic(new Epic("TestEpic", "Test"));
        testTaskManager.removeEpicById(idEpic);
        assertEquals(testTaskManager.getEpicById(idEpic), null);
    }


    //removeSubtaskById
    @Test
    void testMethodRemoveSubtaskById() {
        int idEpic = testTaskManager.createEpic(epic1);
        subtask1.setEpicId(idEpic);
        int idSubtask = testTaskManager.createSubtask(subtask1);
        testTaskManager.removeSubtaskById(idSubtask);
        assertEquals(testTaskManager.getSubtaskById(idSubtask), null);
    }

    //getAllSubtaskByEpicId
    @Test
    void testMethodGetAllSubtaskByEpicId() {
        int epicId = testTaskManager.createEpic(epic1);
        subtask1.setEpicId(epicId);
        subtask2.setEpicId(epicId);
        testTaskManager.createSubtask(subtask1);
        testTaskManager.createSubtask(subtask2);
        List<Subtask> listOfSubtasks = List.of(subtask1, subtask2);
        assertArrayEquals(listOfSubtasks.toArray(), testTaskManager.getAllSubtaskByEpicId(epicId).toArray());
    }

    @Test
    void testMethodGetAllSubtaskByEpicIdWithEmptySubtasks() {
        int epicId = testTaskManager.createEpic(epic1);
        List<Subtask> listOfSubtasks = Collections.emptyList();
        assertArrayEquals(listOfSubtasks.toArray(), testTaskManager.getAllSubtaskByEpicId(epicId).toArray());
    }

    @Test
    void testMethodGetAllSubtaskByEpicIdWithWrongIdEpic() {
        assertDoesNotThrow(() -> testTaskManager.getAllSubtaskByEpicId(-1));
    }
}