package ru.task.tracker.manager;

import com.google.gson.Gson;
import ru.task.tracker.manager.tasks.Epic;
import ru.task.tracker.manager.tasks.Subtask;
import ru.task.tracker.manager.tasks.Task;
import ru.task.tracker.server.KVTaskClient;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.task.tracker.server.KVTaskClient.PORT;

public class HttpTaskManager extends FileBackedTasksManager {
    private final HttpClient kvServerClient = HttpClient.newHttpClient();
    private final String API_TOKEN;
    private final Gson gson;

    public HttpTaskManager() {
        super(null);
        URI uri = URI.create("http://localhost:" + PORT + "/register");
        gson = new Gson();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("Content-type", "application/json")
                .uri(uri)
                .build();

        HttpResponse<String> apiToken = null;
        try {
            apiToken = kvServerClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        API_TOKEN = apiToken.body();

    }

    public static void main(String[] args) {
        try {
            new KVTaskClient().start();
            HttpTaskManager manager = new HttpTaskManager();

            int taskId1 = manager.createTask(new Task("task1", "description1"));
            int taskId2 = manager.createTask(new Task("task2", "description2"));

            System.out.println(manager.getTaskById(taskId1));
            System.out.println(manager.getTaskById(taskId2));


            System.out.println(manager.getHistoryManager().getHistory());

            HttpTaskManager manager2 = HttpTaskManager.loadFromServer();
            System.out.println("Tasks in 2 manager: " + manager2.getAllTasks());

            System.out.println("history " + manager2.getHistoryManager().getHistory());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static HttpTaskManager loadFromServer() {
        HttpTaskManager httpTaskManager = new HttpTaskManager();
        URI tasksUri = URI.create("http://localhost:" + PORT + "/load/tasks?API_TOKEN=" + httpTaskManager.API_TOKEN);
        URI epicsUri = URI.create("http://localhost:" + PORT + "/load/epics?API_TOKEN=" + httpTaskManager.API_TOKEN);
        URI subtsaksUri = URI.create("http://localhost:" + PORT + "/load/subtasks?API_TOKEN=" + httpTaskManager.API_TOKEN);
        URI historyUri = URI.create("http://localhost:" + PORT + "/load/history?API_TOKEN=" + httpTaskManager.API_TOKEN);

        HttpRequest requestTasks = HttpRequest.newBuilder()
                .GET()
                .uri(tasksUri)
                .build();

        HttpRequest requestEpics = HttpRequest.newBuilder()
                .GET()
                .uri(epicsUri)
                .build();

        HttpRequest requestSubtasks = HttpRequest.newBuilder()
                .GET()
                .uri(subtsaksUri)
                .build();

        HttpRequest requestHistory = HttpRequest.newBuilder()
                .GET()
                .uri(historyUri)
                .build();

        try {
            HttpResponse<String> responseTasks = httpTaskManager.kvServerClient.send(
                    requestTasks,
                    HttpResponse.BodyHandlers.ofString());

            HttpResponse<String> responseEpics = httpTaskManager.kvServerClient.send(
                    requestEpics,
                    HttpResponse.BodyHandlers.ofString());

            HttpResponse<String> responseSubtasks = httpTaskManager.kvServerClient.send(
                    requestSubtasks,
                    HttpResponse.BodyHandlers.ofString());

            HttpResponse<String> responseHistory = httpTaskManager.kvServerClient.send(
                    requestHistory,
                    HttpResponse.BodyHandlers.ofString());


            Map<Integer, Task> allTasks = new HashMap<>();

            String jsonTasks = responseTasks.body();
            Task[] tasks = httpTaskManager.gson.fromJson(jsonTasks, Task[].class);
            if (tasks != null) {
                for (Task task : tasks) {
                    allTasks.put(task.getId(), task);
                    httpTaskManager.tasks.put(task.getId(), task);
                    httpTaskManager.sortedTasks.add(task);
                }
            }

            String jsonEpics = responseEpics.body();
            Epic[] epics = httpTaskManager.gson.fromJson(jsonEpics, Epic[].class);
            if (epics != null) {
                for (Epic epic : epics) {
                    allTasks.put(epic.getId(), epic);
                    httpTaskManager.epics.put(epic.getId(), epic);
                }
            }

            String jsonSubtasks = responseSubtasks.body();
            Subtask[] subtasks = httpTaskManager.gson.fromJson(jsonSubtasks, Subtask[].class);
            if (subtasks != null) {
                for (Subtask subtask : subtasks) {
                    httpTaskManager.subtasks.put(subtask.getId(), subtask);
                    allTasks.put(subtask.getId(), subtask);
                    httpTaskManager.sortedTasks.add(subtask);
                }
            }

            String jsonHistory = responseHistory.body();
            StringBuilder builder = new StringBuilder(jsonHistory);
            builder.deleteCharAt(0).deleteCharAt(builder.length()-1);
            String[] history = builder.toString().split(",");
            if (history.length > 0) {
                for (int i = history.length-1; i >= 0; i--) {
                    if (allTasks.containsKey(Integer.parseInt(history[i]))) {
                        httpTaskManager.getHistoryManager().add(allTasks.get(Integer.parseInt(history[i])));
                    }
                }
            }

            if (!allTasks.isEmpty()) {
                int maxId = 0;
                for (int id : allTasks.keySet()) {
                    if (id > maxId) {
                        maxId = id;
                    }
                }
                httpTaskManager.id = maxId;
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return httpTaskManager;
    }

    @Override
    protected void save() {
        URI tasksUri = URI.create("http://localhost:" + PORT + "/save/tasks?API_TOKEN=" + API_TOKEN);
        URI epicsUri = URI.create("http://localhost:" + PORT + "/save/epics?API_TOKEN=" + API_TOKEN);
        URI subtsaksUri = URI.create("http://localhost:" + PORT + "/save/subtasks?API_TOKEN=" + API_TOKEN);
        URI historyUri = URI.create("http://localhost:" + PORT + "/save/history?API_TOKEN=" + API_TOKEN);

        String bodyTasks = "";
        if (!getAllTasks().isEmpty()) {
            bodyTasks = gson.toJson(getAllTasks());
        }
        HttpRequest requestTasks = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(bodyTasks))
                .uri(tasksUri)
                .build();

        String bodyEpics = "";
        if (!getAllEpics().isEmpty()) {
            bodyEpics = gson.toJson(getAllEpics());
        }
        HttpRequest requestEpics = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(bodyEpics))
                .uri(epicsUri)
                .build();

        String bodySuubtasks = "";
        if (!getAllSubtasks().isEmpty()) {
            bodySuubtasks = gson.toJson(getAllSubtasks());
        }
        HttpRequest requestSubtasks = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(bodySuubtasks))
                .uri(subtsaksUri)
                .build();

        String bodyHistory = "";
        if (!getHistoryManager().getHistory().isEmpty()) {
            bodyHistory = gson.toJson(InMemoryHistoryManager.historyToString(getHistoryManager()));
            System.out.println(bodyHistory);
        }
        HttpRequest requestHistory = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(bodyHistory))
                .uri(historyUri)
                .build();

        try {
            kvServerClient.send(requestTasks, HttpResponse.BodyHandlers.ofString());
            kvServerClient.send(requestEpics, HttpResponse.BodyHandlers.ofString());
            kvServerClient.send(requestSubtasks, HttpResponse.BodyHandlers.ofString());
            kvServerClient.send(requestHistory,HttpResponse.BodyHandlers.ofString());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }


}
