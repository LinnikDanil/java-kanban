package ru.task.tracker.manager;

import ru.task.tracker.manager.exceptions.ManagerSaveException;
import ru.task.tracker.manager.tasks.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TaskManager, позволяющий сохранять и использовать данные из файлов для работы с тасками
 */
public class FileBackedTasksManager extends InMemoryTaskManager {
    private final File data_tasks; // файл с данными о тасках

    /**
     * Конструктор создания класса
     *
     * @param file файл с данными
     */
    public FileBackedTasksManager(File file) {
        super();
        data_tasks = file;
    }

    public static void main(String[] args) {
        FileBackedTasksManager fbTaskManager = new FileBackedTasksManager(new File("resources/data_tasks.csv"));

        System.out.println("Test FileBackedTasksManager\n");
        int fbTask1 = fbTaskManager.createTask(new Task("task 1", "test Task 1"));
        int fbTask2 = fbTaskManager.createTask(new Task("task 2", "test Task 2"));
        int fbEpic1 = fbTaskManager.createEpic(new Epic("Epic 1", "test Epic 1"));
        int fbEpic2 = fbTaskManager.createEpic(new Epic("Epic 2", "test Epic 2"));
        int fbSubtask1 = fbTaskManager.createSubtask(new Subtask("Subtask 1", "test subtask 1", fbEpic1));
        int fbSubtask2 = fbTaskManager.createSubtask(new Subtask("Subtask 2", "test subtask 2", fbEpic1));
        int fbSubtask3 = fbTaskManager.createSubtask(new Subtask("Subtask 3", "test subtask 3", fbEpic2));

        System.out.println(fbTaskManager.getAllTasks());
        System.out.println(fbTaskManager.getAllEpics());
        System.out.println(fbTaskManager.getAllSubtasks());
        System.out.println(fbTaskManager.historyManager.getHistory());


        FileBackedTasksManager fbTaskManager2 = FileBackedTasksManager.loadFromFile(
                new File("resources/data_tasks.csv"));

        System.out.println("\n\n Проверяем загрузку информации с файла... ");
        System.out.println(fbTaskManager2.historyManager.getHistory());
        System.out.println(fbTaskManager2.getAllTasks());
        System.out.println(fbTaskManager2.getAllEpics());
        System.out.println(fbTaskManager2.getAllSubtasks());
        int fbTask3 = fbTaskManager2.createTask(new Task("task 3", "Должен быть id = 8"));
        System.out.println(fbTaskManager2.getTaskById(fbTask3));
        System.out.println(fbTaskManager2.historyManager.getHistory());
    }

    /**
     * Метод загрузки данных о тасках и истории из файла
     *
     * @param file файл с данными
     * @return
     */
    public static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        if (file.exists()) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
                List<String> bufferedStrings = new ArrayList<>();
                Map<Integer, Task> allTasks = new HashMap<>();

                while (bufferedReader.ready()) {
                    bufferedStrings.add(bufferedReader.readLine());
                }

                if (!bufferedStrings.isEmpty()) {
                    for (int i = 1; i < bufferedStrings.size() - 2; i++) {//Работа с тасками
                        Task task = fileBackedTasksManager.fromString(bufferedStrings.get(i));
                        allTasks.put(task.getId(), task);
                    }

                    List<Integer> history = InMemoryHistoryManager.historyFromString(//Работа с историей
                            bufferedStrings.get(bufferedStrings.size() - 1));

                    for (int element : history) {
                        if (allTasks.containsKey(element)) {
                            fileBackedTasksManager.historyManager.add(allTasks.get(element));
                        }
                    }

                    int maxId = 0;
                    for (int id : allTasks.keySet()){
                        if (id > maxId){
                            maxId = id;
                        }
                    }
                    fileBackedTasksManager.id = maxId;
                }

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return fileBackedTasksManager;
    }

    /**
     * Метод сохранения данных о тасках и истории в файл
     */
    private void save() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(data_tasks, StandardCharsets.UTF_8, false))) {
            bufferedWriter.write("id,type,name,status,description,startTime,duration,epic\n");
            for (Task task : tasks.values()) {
                bufferedWriter.write(task.toCsv() + "\n");
            }
            for (Epic epic : epics.values()) {
                bufferedWriter.write(epic.toCsv() + "\n");
                if (!epic.getSubtasks().isEmpty()) {
                    for (int subId : epic.getSubtasks()) {
                        bufferedWriter.write(subtasks.get(subId).toCsv() + "\n");
                    }
                }
            }
            bufferedWriter.write("\n");
            bufferedWriter.write(InMemoryHistoryManager.historyToString(historyManager));
        } catch (IOException ioException) {
            throw new ManagerSaveException("Ошибка ввода-вывода.");
        }
    }

    /**
     * Создаёт таск из информации со строки
     *
     * @param value строка с данными о таске
     * @return
     */
    private Task fromString(String value) {
        String[] splitValue = value.split(",");

        if (splitValue.length != 0) {
            int id = Integer.parseInt(splitValue[0]);
            TypeOfTasks type = TypeOfTasks.valueOf(splitValue[1]);
            String name = splitValue[2];
            StatusesOfTask status = StatusesOfTask.valueOf(splitValue[3]);
            String description = splitValue[4];
            LocalDateTime startTime = LocalDateTime.parse(splitValue[5]);
            Duration duration = Duration.parse(splitValue[6]);


            switch (type) {
                case TASK:
                    Task task = new Task(id, name, description, status, startTime, duration);
                    tasks.put(id, task);
                    return task;

                case EPIC:
                    Epic epic = new Epic(id, name, description, status, startTime, duration);
                    epics.put(id, epic);
                    return epic;

                case SUBTASK:
                    int epicId = Integer.parseInt(splitValue[7]);
                    Subtask subtask = new Subtask(id, name, description, status, epicId, startTime, duration);
                    subtasks.put(id, subtask);
                    if (epics.containsKey(epicId)) {
                        epics.get(epicId).addSubtask(id);
                    }
                    return subtask;

                default:
                    throw new IllegalArgumentException("Ошибка с типом файла.");
            }
        }
        throw new RuntimeException("Ошибка!");
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> listTasks = super.getAllTasks();
        save();
        return listTasks;
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        ArrayList<Epic> listEpics = super.getAllEpics();
        save();
        return listEpics;
    }

    @Override
    public ArrayList<Subtask> getAllSubtasks() {
        ArrayList<Subtask> listSubtask = super.getAllSubtasks();
        save();
        return listSubtask;
    }

    @Override
    public void clearAllTasks() {
        super.clearAllTasks();
        save();
    }

    @Override
    public void clearAllEpics() {
        super.clearAllEpics();
        save();
    }

    @Override
    public void clearAllSubtasks() {
        super.clearAllSubtasks();
        save();
    }

    @Override
    public Task getTaskById(int taskId) {
        Task task = super.getTaskById(taskId);
        save();
        return task;
    }

    @Override
    public Epic getEpicById(int epicId) {
        Epic epic = super.getEpicById(epicId);
        save();
        return epic;
    }

    @Override
    public Subtask getSubtaskById(int subtaskId) {
        Subtask subtask = super.getSubtaskById(subtaskId);
        save();
        return subtask;
    }

    @Override
    public int createTask(Task task) {
        int taskId = super.createTask(task);
        save();
        return taskId;
    }

    @Override
    public int createEpic(Epic epic) {
        int epicId = super.createEpic(epic);
        save();
        return epicId;
    }

    @Override
    public int createSubtask(Subtask subtask) {
        int subtaskId = super.createSubtask(subtask);
        save();
        return subtaskId;
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void removeTaskById(int taskId) {
        super.removeTaskById(taskId);
        save();
    }

    @Override
    public void removeEpicById(int epicId) {
        super.removeEpicById(epicId);
        save();
    }

    @Override
    public void removeSubtaskById(int subtaskId) {
        super.removeSubtaskById(subtaskId);
        save();
    }

    @Override
    public ArrayList<Subtask> getAllSubtaskByEpicId(int epicId) {
        ArrayList<Subtask> listSubtask = super.getAllSubtaskByEpicId(epicId);
        save();
        return listSubtask;
    }
}
