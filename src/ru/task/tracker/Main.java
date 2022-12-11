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
        taskManager.updateTask(taskId1, new Task("Курс", "Сдать последний ТЗ"));
        System.out.println(taskManager.getAllTasks());
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
        System.out.println("Создали 1 эпик");
        int epicId1 = taskManager.createEpic(new Epic("Епик 1", "Проверка епика 1"));
        System.out.println("Просмотр эпика по айди");
        System.out.println(taskManager.getEpicById(epicId1));
        System.out.println("Создали 2 эпик");
        int epicId2 = taskManager.createEpic(new Epic("Епик 2", "Проверка эпика 2"));
        System.out.println(taskManager.getAllEpics());
        System.out.println("Обновление эпика");
        taskManager.updateEpic(epicId1, new Epic("Епик 3", "Проверка эпика 3"));
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
        System.out.println(taskManager.getAllSubtask());
        System.out.println("Обновление сабтаска");
        taskManager.updateSubtask(subtaskId1, new Subtask("Сабтаск 3", "Проверка сабтаска 3", epicId1));
        System.out.println(taskManager.getAllSubtask());
        System.out.println("Удалить эпик по айди");
        taskManager.removeSubtaskById(subtaskId1);
        System.out.println(taskManager.getAllSubtask());
        int subtaskId3 = taskManager.createSubtask(new Subtask("Сабтаск 4", "Проверка сабтаска 4", epicId1));
        System.out.println("Проверка обновления статусов епиков");
        taskManager.getSubtaskById(subtaskId2).setStatus(StatusesOfTask.IN_PROGRESS);
        taskManager.updateSubtask(subtaskId2, new Subtask("Сабтаск 5", "Проверка сабтаска 5", epicId1));
        System.out.println(taskManager.getAllSubtask());
        System.out.println(taskManager.getAllEpics());
        taskManager.getSubtaskById(subtaskId3).setStatus(StatusesOfTask.DONE);
        taskManager.updateSubtask(subtaskId3, new Subtask("Сабтаск 4", "Проверка сабтаска 4", epicId1));
        System.out.println(taskManager.getAllSubtask());
        System.out.println(taskManager.getAllEpics());
        taskManager.getSubtaskById(subtaskId2).setStatus(StatusesOfTask.DONE);
        taskManager.updateSubtask(subtaskId2, new Subtask("Сабтаск 5", "Проверка сабтаска 5", epicId1));
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
    }
}
