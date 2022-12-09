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
        Task task1 = new Task("Уборка", "поубираться в комнате");
        taskManager.createTask(task1);
        Task task2 = new Task("Уроки", "Сделать уроки");
        taskManager.createTask(task2);
        Task task3 = new Task("Курс", "Написать тз");
        task3.setId(1);
        task3.setStatus(1);
        taskManager.updateTask(task3);
        System.out.println(taskManager.getAllTasks());
        taskManager.removeTaskById(1);
        System.out.println(taskManager.getAllTasks());
        taskManager.createTask(task1);
        System.out.println(taskManager.getTaskById(3));
        task1.setStatus(2);
        System.out.println(taskManager.getAllTasks());
        taskManager.clearAllTasks();
        System.out.println(taskManager.getAllTasks());

        System.out.println("\n2 - Epics");
        Epic epic1 = new Epic("Уборка в доме", "Поубираться во всех комнатах");
        taskManager.createEpic(epic1);
        Epic epic2 = new Epic("Ремонт", "Ремонт во всех комнатах");
        taskManager.createEpic(epic2);
        System.out.println(taskManager.getAllEpics());
        Epic epic3 = new Epic("Купить продукты", "Купить разные категории продуктов");
        epic3.setId(4);
        taskManager.updateEpic(epic3);
        System.out.println(taskManager.getEpicById(5));
        System.out.println(taskManager.getAllEpics());

        System.out.println("\n3 - Subtasks");
        //manager.clearAllEpics(); //проверка на вылет из приложения, в случае отсутсвия эпиков
        Subtask subtask1 = new Subtask("Сладости", "купить сладости");
        subtask1.setEpicId(4);
        taskManager.createSubtask(subtask1);
        System.out.println(taskManager.getAllSubtask());
        Subtask subtask2 = new Subtask("Мясо", "купить колбасу и курицу");
        subtask2.setEpicId(4);
        taskManager.createSubtask(subtask2);
        Subtask subtask3 = new Subtask("Кухня", "Сделать ремнонт на кухне");
        subtask3.setEpicId(5);
        taskManager.createSubtask(subtask3);
        Subtask subtask4 = new Subtask("Ванна", "Сделать ремонт в ванной");
        subtask4.setEpicId(5);
        taskManager.createSubtask(subtask4);
        System.out.println(taskManager.getAllSubtask());
        System.out.println(taskManager.getAllEpics());
        Subtask subtask5 = new Subtask("Молочка", "Купить молоко и творог");
        subtask5.setEpicId(4);
        subtask5.setId(6);
        taskManager.updateSubtask(subtask5);
        Subtask subtask6 = new Subtask("Зал", "Повесить красивую люстру");
        subtask6.setEpicId(5);
        subtask6.setId(8);
        taskManager.updateSubtask(subtask6);
        System.out.println(taskManager.getAllSubtask());
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getSubtaskById(8));
        System.out.println("3.1");
        System.out.println(taskManager.getAllSubtaskByEpicId(5));

        //manager.clearAllEpics(); //ещё одна проверка на вылеты и неправильное решение задач
        System.out.println("\n4 - remove and clear subtasks");
        taskManager.removeSubtaskById(6);
        System.out.println(taskManager.getAllSubtask());
        System.out.println(taskManager.getAllEpics());
        taskManager.clearAllSubtasks();
        System.out.println(taskManager.getAllSubtask());
        System.out.println(taskManager.getAllEpics());

        System.out.println("\n5 - remove and clear epics");
        System.out.println(taskManager.getAllEpics());
        taskManager.removeEpicById(5);
        System.out.println(taskManager.getAllEpics());
        taskManager.createEpic(epic2);
        System.out.println(taskManager.getAllEpics());
        taskManager.clearAllEpics();
        System.out.println(taskManager.getAllEpics());

        System.out.println("\nПроверка на удаление сабтасков при удалении епиков");
        //при общем удалении было проверено выше
        Epic epic5 = new Epic("Уборка в доме", "Поубираться во всех комнатах");
        taskManager.createEpic(epic5);
        System.out.println(taskManager.getAllEpics());
        Subtask subtask7 = new Subtask("Сладости", "купить сладости");
        subtask7.setEpicId(11);
        taskManager.createSubtask(subtask7);
        Subtask subtask8 = new Subtask("Мясо", "купить колбасу и курицу");
        subtask8.setEpicId(11);
        taskManager.createSubtask(subtask8);
        System.out.println(taskManager.getAllEpics());
        taskManager.removeEpicById(11);
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtask());
        taskManager.createEpic(epic5);
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtask());
        taskManager.removeEpicById(14);
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtask());
        System.out.println();

        System.out.println("Проверка обновления статуса task");
        Task task5 = new Task("Уборка", "поубираться в комнате");
        taskManager.createTask(task5);
        System.out.println(taskManager.getAllTasks());
        Task task6 = new Task("Уроки", "Сделать уроки");
        task6.setStatus(1);
        task6.setId(15);
        taskManager.updateTask(task6);
        System.out.println(taskManager.getAllTasks());
        System.out.println();
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllEpics());
        subtask1.setEpicId(14);
        taskManager.createSubtask(subtask1);
        subtask2.setEpicId(14);
        taskManager.createSubtask(subtask2);
        subtask4.setEpicId(14);
        taskManager.createSubtask(subtask4);

        System.out.println("\nПроверка обновления статусов");
        System.out.println(taskManager.getAllEpics());
        subtask1.setStatus(2);
        taskManager.updateSubtask(subtask1);
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtask());
        subtask2.setStatus(2);
        subtask4.setStatus(1);
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtask());
        taskManager.removeSubtaskById(18);
        taskManager.clearAllSubtasks();
        System.out.println(taskManager.getAllSubtask());
        System.out.println(taskManager.getAllEpics());
        taskManager.createSubtask(subtask4);
        subtask4.setStatus(2);
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtask());

        System.out.println("\nУдалить всё");
        taskManager.clearAllTasks();
        taskManager.clearAllEpics();
        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtask());

        System.out.println("\n\n Проверка истории");
        System.out.println(taskManager.getHistory());

        taskManager.createTask(task1);
        taskManager.createEpic(epic1);
        subtask1.setEpicId(21);
        taskManager.createSubtask(subtask1);

        System.out.println(taskManager.getTaskById(20));
        System.out.println(taskManager.getEpicById(21));
        System.out.println(taskManager.getSubtaskById(22));

        System.out.println("\n " + taskManager.getHistory());

        System.out.println(taskManager.getTaskById(20));
        System.out.println(taskManager.getEpicById(21));
        System.out.println(taskManager.getSubtaskById(22));
        System.out.println(taskManager.getTaskById(20));
        System.out.println(taskManager.getEpicById(21));
        System.out.println(taskManager.getSubtaskById(22));

        System.out.println("\n " + taskManager.getHistory());

        taskManager.createTask(task2);
        System.out.println(taskManager.getTaskById(23));

        System.out.println("\n " + taskManager.getHistory());
    }
}
