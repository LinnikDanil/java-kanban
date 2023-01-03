package ru.task.tracker;

import ru.task.tracker.manager.*;
import ru.task.tracker.manager.tasks.*;

/**
 * Основной класс приложения
 *
 * @author Линник Данил
 * @version 1.0
 */
public class Main {

    public static void main(String[] args) {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();

        System.out.println("\n1 - Tasks");
        System.out.println("Создали 1 таск");
        int taskId1 = taskManager.createTask(new Task("Уборка", "Сделать домашнюю уборку"));
        System.out.println("Просмотр таска по айди");
        System.out.println(taskManager.getTaskById(taskId1));
        System.out.println("Создали 2 таск");
        int taskId2 = taskManager.createTask(new Task("Готовка", "Приготовить суп"));
        System.out.println(taskManager.getAllTasks());
        System.out.println("Обновление Таска");
        taskManager.updateTask(new Task(taskId1, "Курс", "Сдать последний ТЗ", StatusesOfTask.NEW));
        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.historyManager.getHistory());
        System.out.println("Удалить таск по айди");
        taskManager.removeTaskById(taskId1);
        System.out.println(taskManager.getAllTasks());
        int taskId3 = taskManager.createTask(new Task("Код", "Проверка работы таск менеджера"));
        System.out.println("Обновление статусов");
        taskManager.getTaskById(taskId2).setStatus(StatusesOfTask.IN_PROGRESS);
        taskManager.getTaskById(taskId3).setStatus(StatusesOfTask.DONE);
        System.out.println(taskManager.getAllTasks());
        System.out.println("Удалить все таски");
        taskManager.clearAllTasks();
        System.out.println(taskManager.getAllTasks());

        System.out.println("\n2 - Epics");
        System.out.println(taskManager.historyManager.getHistory());
        System.out.println("Создали 1 эпик");
        int epicId1 = taskManager.createEpic(new Epic("Епик 1", "Проверка епика 1"));
        System.out.println("Просмотр эпика по айди");
        System.out.println(taskManager.getEpicById(epicId1));
        System.out.println("Создали 2 эпик");
        int epicId2 = taskManager.createEpic(new Epic("Епик 2", "Проверка эпика 2"));
        System.out.println(taskManager.getAllEpics());
        System.out.println("Обновление эпика");
        int subtaskId0 = taskManager.createSubtask(new Subtask(0, "1", "1", StatusesOfTask.IN_PROGRESS, epicId1));
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtask());
        taskManager.updateEpic(new Epic(epicId1, "Епик 3", "Проверка эпика 3"));
        System.out.println(taskManager.getAllEpics());
        System.out.println("Удалить эпик по айди");
        taskManager.removeEpicById(epicId1);
        System.out.println(taskManager.getAllEpics());
        int epicId3 = taskManager.createEpic(new Epic("Епик 4", "Проверка эпика 4"));
        System.out.println(taskManager.getAllEpics());
        System.out.println("Удалить все эпики");
        taskManager.clearAllEpics();
        System.out.println(taskManager.getAllEpics());

        System.out.println("\n3 - Subtasks");
        epicId1 = taskManager.createEpic(new Epic("Епик 1", "Проверка епика 1"));
        System.out.println("Создали сабтаск");
        int subtaskId1 = taskManager.createSubtask(new Subtask("Сабтаск 1", "Проверка сабтаска 1", epicId1));
        System.out.println("Вывод сабтаска по айди");
        System.out.println(taskManager.getSubtaskById(subtaskId1));
        int subtaskId2 = taskManager.createSubtask(new Subtask("Сабтаск 2", "Проверка сабтаска 2", epicId1));
        System.out.println("Создали 2 сабтаска");
        System.out.println(taskManager.getAllSubtask());
        System.out.println("Обновление сабтаска");
        taskManager.updateSubtask(new Subtask(subtaskId1, "Сабтаск 3", "Проверка сабтаска 3", StatusesOfTask.NEW, epicId1));
        System.out.println(taskManager.getAllSubtask());
        System.out.println("Удалить эпик по айди");
        taskManager.removeSubtaskById(subtaskId1);
        System.out.println(taskManager.getAllSubtask());
        int subtaskId3 = taskManager.createSubtask(new Subtask("Сабтаск 4", "Проверка сабтаска 4", epicId1));
        System.out.println("Проверка обновления статусов епиков");
        taskManager.updateSubtask(new Subtask(subtaskId2, "Сабтаск 5", "Проверка сабтаска 5", StatusesOfTask.IN_PROGRESS, epicId1));
        System.out.println(taskManager.getAllSubtask());
        System.out.println(taskManager.getAllEpics());
        taskManager.updateSubtask(new Subtask(subtaskId3, "Сабтаск 4", "Проверка сабтаска 4", StatusesOfTask.DONE, epicId1));
        System.out.println(taskManager.getAllSubtask());
        System.out.println(taskManager.getAllEpics());
        taskManager.updateSubtask(new Subtask(subtaskId2, "Сабтаск 5", "Проверка сабтаска 5", StatusesOfTask.DONE, epicId1));
        System.out.println(taskManager.getAllSubtask());
        System.out.println(taskManager.getAllEpics());
        System.out.println("Удалить все сабтаски");
        taskManager.clearAllSubtasks();
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtask());

        System.out.println("\nПроверка на удаление сабтасков при удалении епиков");
        subtaskId2 = taskManager.createSubtask(new Subtask("Сабтаск 2", "Проверка сабтаска 2", epicId1));
        subtaskId3 = taskManager.createSubtask(new Subtask("Сабтаск 4", "Проверка сабтаска 4", epicId1));
        taskManager.clearAllEpics();
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtask());
        epicId1 = taskManager.createEpic(new Epic("Епик 1", "Проверка епика 1"));
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtask());
        taskManager.clearAllEpics();

        System.out.println("\nПроверка истории");
        System.out.println(taskManager.historyManager.getHistory());
        int epicTest1 = taskManager.createEpic(new Epic("Test 1", "Testing 1"));
        int epicTest2 = taskManager.createEpic(new Epic("Test 2", "Testing 2"));
        int subtaskTest1 = taskManager.createSubtask(new Subtask("subtask 1", "subtaskDescription 1", epicTest1));
        int subtaskTest2 = taskManager.createSubtask(new Subtask("subtask 2", "subtaskDescription 2", epicTest1));
        int subtaskTest3 = taskManager.createSubtask(new Subtask("subtask 3", "subtaskDescription 3", epicTest1));
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtask());

        System.out.println(taskManager.getEpicById(epicTest1));
        System.out.println(taskManager.getEpicById(epicTest2));
        System.out.println(taskManager.getSubtaskById(subtaskTest1));
        System.out.println(taskManager.getSubtaskById(subtaskTest2));
        System.out.println(taskManager.getSubtaskById(subtaskTest3));

        System.out.println("\nИстория после первого запроса");
        System.out.println(taskManager.historyManager.getHistory());

        System.out.println("\nИстория при разных вызовах задач");
        taskManager.getSubtaskById(subtaskTest1);
        System.out.println(taskManager.historyManager.getHistory());
        taskManager.getEpicById(epicTest2);
        System.out.println(taskManager.historyManager.getHistory());
        taskManager.getSubtaskById(subtaskTest3);
        System.out.println(taskManager.historyManager.getHistory());
        taskManager.getSubtaskById(subtaskTest2);
        System.out.println(taskManager.historyManager.getHistory());
        taskManager.getEpicById(epicTest1);
        System.out.println(taskManager.historyManager.getHistory());

        System.out.println("\nУдалили сабтаск2 из истории");
        taskManager.historyManager.remove(new Subtask(subtaskTest2, "asd", "asd", StatusesOfTask.NEW, epicTest1));
        System.out.println(taskManager.historyManager.getHistory());

        System.out.println("История после удаления эпика с подзадачами");
        taskManager.removeEpicById(epicTest1);
        System.out.println(taskManager.historyManager.getHistory());

        taskManager.clearAllEpics();
        System.out.println("Удалили все эпики");
        System.out.println(taskManager.historyManager.getHistory());
    }
}
