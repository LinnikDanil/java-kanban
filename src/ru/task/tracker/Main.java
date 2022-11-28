package ru.task.tracker;
import ru.task.tracker.manager.*;
import ru.task.tracker.manager.tasks.*;
/**
 * Основной класс приложения
 * @author Линник Данил
 * @version 1.0
 */
public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();
        {
            System.out.println("\n1 - Tasks");
            Task task1 = new Task("Уборка", "поубираться в комнате");
            manager.createTask(task1);
            Task task2 = new Task("Уроки", "Сделать уроки");
            manager.createTask(task2);
            Task task3 = new Task("Курс", "Написать тз");
            task3.setId(1);
            task3.setStatus(1);
            manager.updateTask(task3);
            System.out.println(manager.getAllTasks());
            manager.removeTaskById(1);
            System.out.println(manager.getAllTasks());
            manager.createTask(task1);
            System.out.println(manager.getTaskById(3));
            task1.setStatus(2);
            System.out.println(manager.getAllTasks());
            manager.clearAllTasks();
            System.out.println(manager.getAllTasks());

            System.out.println("\n2 - Epics");
            Epic epic1 = new Epic("Уборка в доме", "Поубираться во всех комнатах");
            manager.createEpic(epic1);
            Epic epic2 = new Epic("Ремонт", "Ремонт во всех комнатах");
            manager.createEpic(epic2);
            System.out.println(manager.getAllEpics());
            Epic epic3 = new Epic("Купить продукты", "Купить разные категории продуктов");
            epic3.setId(4);
            manager.updateEpic(epic3);
            System.out.println(manager.getEpicById(5));
            System.out.println(manager.getAllEpics());


            System.out.println("\n3 - Subtasks");
            //manager.clearAllEpics(); //проверка на вылет из приложения, в случае отсутсвия эпиков
            Subtask subtask1 = new Subtask("Сладости", "купить сладости");
            subtask1.setEpicId(4);
            manager.createSubtask(subtask1);
            System.out.println(manager.getAllSubtask());
            Subtask subtask2 = new Subtask("Мясо", "купить колбасу и курицу");
            subtask2.setEpicId(4);
            manager.createSubtask(subtask2);
            Subtask subtask3 = new Subtask("Кухня", "Сделать ремнонт на кухне");
            subtask3.setEpicId(5);
            manager.createSubtask(subtask3);
            Subtask subtask4 = new Subtask("Ванна", "Сделать ремонт в ванной");
            subtask4.setEpicId(5);
            manager.createSubtask(subtask4);
            System.out.println(manager.getAllSubtask());
            System.out.println(manager.getAllEpics());
            Subtask subtask5 = new Subtask("Молочка", "Купить молоко и творог");
            subtask5.setEpicId(4);
            subtask5.setId(6);
            manager.updateSubtask(subtask5);
            Subtask subtask6 = new Subtask("Зал", "Повесить красивую люстру");
            subtask6.setEpicId(5);
            subtask6.setId(8);
            manager.updateSubtask(subtask6);
            System.out.println(manager.getAllSubtask());
            System.out.println(manager.getAllEpics());
            System.out.println(manager.getSubtaskById(8));
            System.out.println("3.1");
            System.out.println(manager.getAllSubtaskByEpicId(5));

            //manager.clearAllEpics(); ещё одна проверка на вылеты и неправильное решение задач
            System.out.println("\n4 - remove and clear subtasks");
            manager.removeSubtaskById(6);
            System.out.println(manager.getAllSubtask());
            System.out.println(manager.getAllEpics());
            manager.clearAllSubtasks();
            System.out.println(manager.getAllSubtask());
            System.out.println(manager.getAllEpics());

            System.out.println("\n5 - remove and clear epics");
            System.out.println(manager.getAllEpics());
            manager.removeEpicById(5);
            System.out.println(manager.getAllEpics());
            manager.createEpic(epic2);
            System.out.println(manager.getAllEpics());
            manager.clearAllEpics();
            System.out.println(manager.getAllEpics());

            System.out.println("\nПроверка на удаление сабтасков при удалении епиков");
            //при общем удалении было проверено выше
            Epic epic5 = new Epic("Уборка в доме", "Поубираться во всех комнатах");
            manager.createEpic(epic5);
            System.out.println(manager.getAllEpics());
            Subtask subtask7 = new Subtask("Сладости", "купить сладости");
            subtask7.setEpicId(11);
            manager.createSubtask(subtask7);
            Subtask subtask8 = new Subtask("Мясо", "купить колбасу и курицу");
            subtask8.setEpicId(11);
            manager.createSubtask(subtask8);
            System.out.println(manager.getAllEpics());
            manager.removeEpicById(11);
            System.out.println(manager.getAllEpics());
            System.out.println(manager.getAllSubtask());
            manager.createEpic(epic5);
            System.out.println(manager.getAllEpics());
            System.out.println(manager.getAllSubtask());
            manager.removeEpicById(14);
            System.out.println(manager.getAllEpics());
            System.out.println(manager.getAllSubtask());


            System.out.println();
            System.out.println("Проверка обновления статуса task");
            Task task5 = new Task("Уборка", "поубираться в комнате");

            manager.createTask(task5);
            System.out.println(manager.getAllTasks());
            Task task6 = new Task("Уроки", "Сделать уроки");
            task6.setStatus(1);
            task6.setId(15);
            manager.updateTask(task6);
            System.out.println(manager.getAllTasks());

            System.out.println();
            System.out.println("Проверка обновления Epics");
            System.out.println(manager.getAllEpics());
            System.out.println(manager.getAllEpics());

            subtask1.setEpicId(14);
            manager.createSubtask(subtask1);
            subtask2.setEpicId(14);
            manager.createSubtask(subtask2);
            subtask4.setEpicId(14);
            manager.createSubtask(subtask4);

            System.out.println(manager.getAllEpics());
            subtask1.setStatus(2);
            subtask2.setStatus(2);
            subtask4.setStatus(1);

            manager.updateSubtask(subtask1);
            manager.updateSubtask( subtask2);
            manager.updateSubtask( subtask4);
            System.out.println(manager.getAllEpics());
            manager.createEpic(epic1);
            subtask4.setStatus(2);
            manager.updateSubtask(subtask4);
            System.out.println(manager.getAllEpics());


            System.out.println("\nУдалить всё");
            manager.clearAllTasks();
            manager.clearAllEpics();

            System.out.println(manager.getAllTasks());
            System.out.println(manager.getAllEpics());
            System.out.println(manager.getAllSubtask());
        }//Проверки
    }
}
