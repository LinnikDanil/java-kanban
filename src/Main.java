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
            manager.updateTask(task3);
            System.out.println(manager.getAllTasks());
            manager.removeTaskById(1);
            System.out.println(manager.getAllTasks());
            manager.createTask(task1);
            System.out.println(manager.getTaskById(3));
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
            epic3.setStatus(1);
            manager.updateEpic(epic3);
            System.out.println(manager.getAllEpics());
            epic2.setStatus(2);
            System.out.println(manager.getEpicById(5));

            System.out.println("\n3 - Subtasks");
            //manager.clearAllEpics(); //проверка на вылет из приложения, в случае отсутсвия эпиков
            Subtask subtask1 = new Subtask("Сладости", "купить сладости");
            manager.createSubtask(4, subtask1);
            Subtask subtask2 = new Subtask("Мясо", "купить колбасу и курицу");
            manager.createSubtask(4, subtask2);
            Subtask subtask3 = new Subtask("Кухня", "Сделать ремнонт на кухне");
            manager.createSubtask(5, subtask3);
            Subtask subtask4 = new Subtask("Ванна", "Сделать ремонт в ванной");
            manager.createSubtask(5, subtask4);
            System.out.println(manager.getAllSubtask());
            System.out.println(manager.getAllEpics());
            Subtask subtask5 = new Subtask("Молочка", "Купить молоко и творог");
            subtask5.setId(6);
            manager.updateSubtask(4, subtask5);
            Subtask subtask6 = new Subtask("Зал", "Повесить красивую люстру");
            subtask6.setId(8);
            manager.updateSubtask(5, subtask6);
            System.out.println(manager.getAllSubtask());
            System.out.println(manager.getAllEpics());
            System.out.println(manager.getSubtaskById(5, 8));
            System.out.println("3.1");
            System.out.println(manager.getAllSubtaskByEpicId(5));

            //manager.clearAllEpics(); ещё одна проверка на вылеты и неправильное решение задач
            System.out.println("\n4 - remove and clear subtasks");
            manager.removeSubtaskById(4, 6);
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
            manager.createSubtask(11, subtask7);
            Subtask subtask8 = new Subtask("Мясо", "купить колбасу и курицу");
            manager.createSubtask(11, subtask8);
            System.out.println(manager.getAllEpics());
            manager.removeEpicById(11);
            System.out.println(manager.getAllEpics());
            System.out.println(manager.getAllSubtask());
            manager.createEpic(epic5);
            System.out.println(manager.getAllEpics());
            System.out.println(manager.getAllSubtask());
            manager.removeEpicById(14);


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
            manager.createEpic(epic1);
            manager.createEpic(epic2);
            //epic1.setStatus(1);
            System.out.println(manager.getAllEpics());

            manager.createSubtask(16, subtask1);
            manager.createSubtask(16, subtask2);
            manager.createSubtask(16, subtask3);

            System.out.println(manager.getAllEpics());
            subtask1.setStatus(2);
            subtask2.setStatus(2);
            subtask3.setStatus(1);

            manager.updateSubtask(16, subtask1);
            manager.updateSubtask(16, subtask2);
            manager.updateSubtask(16, subtask3);
            System.out.println(manager.getAllEpics());
            subtask3.setStatus(2);
            manager.updateSubtask(16, subtask3);
            System.out.println(manager.getAllEpics());
        }//Проверки
    }
}
