package ru.task.tracker.manager.tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.task.tracker.manager.Managers;
import ru.task.tracker.manager.TaskManager;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    private final TaskManager taskManager = Managers.getDefault();
    private int epic;
    private int subtask1;
    private int subtask2;


    @BeforeEach
    void createEpicsForTest() {
        epic = taskManager.createEpic(new Epic("TestEpic1", "DescriptionForEpic1"));
        subtask1 = taskManager.createSubtask(
                new Subtask("TestSubtask1", "DescriptionForSubtask1", epic,
                        LocalDateTime.of(2023, 2, 17, 1, 0),
                        Duration.ofHours(7)));
        subtask2 = taskManager.createSubtask(
                new Subtask("TestSubtask2", "DescriptionForSubtask2", epic,
                        LocalDateTime.of(2023, 2, 16, 5, 0),
                        Duration.ofHours(10)));
    }

    @Test
    void shouldReturnStatusIsNewForEmptyEpic() {
        taskManager.clearAllSubtasks();
        assertEquals(taskManager.getEpicById(epic).getStatus(), StatusesOfTask.NEW);
    }

    @Test
    void shouldReturnStatusIsNewForEpicWithNewSubtasks() {
        assertEquals(taskManager.getEpicById(epic).getStatus(), StatusesOfTask.NEW);
    }

    @Test
    void shouldReturnStatusIsDoneForEpicWithDoneSubtasks() {
        taskManager.updateSubtask(new Subtask(
                subtask1, "TestSubtask1", "DescriptionForSubtask1", StatusesOfTask.DONE, epic));
        taskManager.updateSubtask(new Subtask(
                subtask2, "TestSubtask2", "DescriptionForSubtask2", StatusesOfTask.DONE, epic));
        assertEquals(taskManager.getEpicById(epic).getStatus(), StatusesOfTask.DONE);
    }

    @Test
    void shouldReturnStatusIsInProgressForEpicWithDoneAndNewSubtasks() {
        taskManager.updateSubtask(new Subtask(
                subtask1, "TestSubtask1", "DescriptionForSubtask1", StatusesOfTask.NEW, epic));
        taskManager.updateSubtask(new Subtask(
                subtask2, "TestSubtask2", "DescriptionForSubtask2", StatusesOfTask.DONE, epic));
        assertEquals(taskManager.getEpicById(epic).getStatus(), StatusesOfTask.IN_PROGRESS);
    }

    @Test
    void shouldReturnStatusIsInProgressForEpicWithInProgressSubtasks() {
        taskManager.updateSubtask(new Subtask(
                subtask1, "TestSubtask1", "DescriptionForSubtask1", StatusesOfTask.IN_PROGRESS, epic));
        taskManager.updateSubtask(new Subtask(
                subtask2, "TestSubtask2", "DescriptionForSubtask2", StatusesOfTask.IN_PROGRESS, epic));
        assertEquals(taskManager.getEpicById(epic).getStatus(), StatusesOfTask.IN_PROGRESS);
    }

    @Test
    void testTimeinEpic() {
        assertEquals(
                taskManager.getEpicById(epic).getEndTime(),
                LocalDateTime.of(2023, 2, 17, 8, 0)
        );

        assertEquals(
                taskManager.getEpicById(epic).getStartTime(),
                LocalDateTime.of(2023, 2, 16, 5, 0)
        );

        assertEquals(
                taskManager.getEpicById(epic).getDuration(), Duration.ofHours(17));
    }
}