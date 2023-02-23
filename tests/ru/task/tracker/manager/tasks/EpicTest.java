package ru.task.tracker.manager.tasks;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.task.tracker.manager.FileBackedTasksManager;
import ru.task.tracker.manager.Managers;
import ru.task.tracker.manager.TaskManager;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    private final TaskManager taskManager = new FileBackedTasksManager(new File("dataTasks.csv"));

    private Epic epic;
    private int epicId;
    private Subtask subtask1;
    private int subtaskId1;
    private Subtask subtask2;
    private int subtaskId2;

    @BeforeEach
    void createEpicsForTest() {
        epic = new Epic("TestEpic1", "DescriptionForEpic1");
        epicId = taskManager.createEpic(epic);
        subtask1 = new Subtask("TestSubtask1", "DescriptionForSubtask1", epicId,
                        LocalDateTime.of(2023, 2, 17, 1, 0),
                        Duration.ofHours(7));
        subtask2 = new Subtask("TestSubtask2", "DescriptionForSubtask2", epicId,
                        LocalDateTime.of(2023, 2, 16, 5, 0),
                        Duration.ofHours(10));
        subtaskId1 = taskManager.createSubtask(subtask1);
        subtaskId2 = taskManager.createSubtask(subtask2);
    }

    @AfterEach
    void afterEach() {
        taskManager.clearAllSubtasks();
        taskManager.clearAllEpics();
    }

    @Test
    void shouldReturnStatusIsNewForEmptyEpic() {
        taskManager.clearAllSubtasks();
        assertEquals(taskManager.getEpicById(epicId).getStatus(), StatusesOfTask.NEW);
    }

    @Test
    void shouldReturnStatusIsNewForEpicWithNewSubtasks() {
        assertEquals(taskManager.getEpicById(epicId).getStatus(), StatusesOfTask.NEW);
    }

    @Test
    void shouldReturnStatusIsDoneForEpicWithDoneSubtasks() {
        subtask1.setStatus(StatusesOfTask.DONE);
        subtask2.setStatus(StatusesOfTask.DONE);
        taskManager.updateSubtask(subtask1);
        taskManager.updateSubtask(subtask2);
        assertEquals(taskManager.getEpicById(epicId).getStatus(), StatusesOfTask.DONE);
    }

    @Test
    void shouldReturnStatusIsInProgressForEpicWithDoneAndNewSubtasks() {
        subtask1.setStatus(StatusesOfTask.DONE);
        subtask2.setStatus(StatusesOfTask.NEW);
        taskManager.updateSubtask(subtask1);
        taskManager.updateSubtask(subtask2);
        assertEquals(taskManager.getEpicById(epicId).getStatus(), StatusesOfTask.IN_PROGRESS);
    }

    @Test
    void shouldReturnStatusIsInProgressForEpicWithInProgressSubtasks() {
        subtask1.setStatus(StatusesOfTask.IN_PROGRESS);
        subtask2.setStatus(StatusesOfTask.IN_PROGRESS);
        taskManager.updateSubtask(subtask1);
        taskManager.updateSubtask(subtask2);
        assertEquals(taskManager.getEpicById(epicId).getStatus(), StatusesOfTask.IN_PROGRESS);
    }

    @Test
    void testTimeinEpic() {
        assertEquals(
                taskManager.getEpicById(epicId).getEndTime(),
                LocalDateTime.of(2023, 2, 17, 8, 0)
        );

        assertEquals(
                taskManager.getEpicById(epicId).getStartTime(),
                LocalDateTime.of(2023, 2, 16, 5, 0)
        );

        assertEquals(
                taskManager.getEpicById(epicId).getDuration(), Duration.ofHours(17));
    }
}