package ru.task.tracker.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import ru.task.tracker.manager.FileBackedTasksManager;
import ru.task.tracker.manager.TaskManager;
import ru.task.tracker.manager.tasks.Epic;
import ru.task.tracker.manager.tasks.Subtask;
import ru.task.tracker.manager.tasks.Task;
import ru.task.tracker.server.adapters.DurationAdapter;
import ru.task.tracker.server.adapters.LocalDateTimeAdapter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

public class HttpTaskServer {
    private static final int PORT = 8080;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private TaskManager taskManagerForHandlers;
    private Gson gsonForHandlers;

    public HttpTaskServer() throws IOException {
        taskManagerForHandlers = FileBackedTasksManager.loadFromFile(new File("dataTasks.csv"));

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        gsonBuilder.registerTypeAdapter(Duration.class, new DurationAdapter());
        gsonForHandlers = gsonBuilder.create();

        HttpServer httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new TaskHandler(taskManagerForHandlers, gsonForHandlers));
        httpServer.start();

        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }

    public static void main(String[] args) throws IOException {
        HttpTaskServer http = new HttpTaskServer();
    }

    class TaskHandler implements HttpHandler {
        private TaskManager taskManager;
        private Gson gson;

        public TaskHandler(TaskManager taskManager, Gson gson) {
            this.gson = gson;
            this.taskManager = taskManager;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String requestMethod = exchange.getRequestMethod();
            String requestUri = exchange.getRequestURI().toString();
            String requestPath = exchange.getRequestURI().getPath();
            Endpoints endpoint = getEndpoint(requestUri, requestMethod, requestPath);

            System.out.println("endpoint: " + endpoint.toString());

            switch (endpoint) {
                case GET_TASK:
                case GET_EPIC:
                case GET_SUBTASK:
                case GET_ALL_TASKS:
                    responseTask(exchange, endpoint);
                    break;

                case GET_TASKS_HISTORY:
                    if (!taskManager.historyManager.getHistory().isEmpty()) {
                        String history = gson.toJson(taskManager.historyManager.getHistory());
                        writeResponse(exchange, history, 200);
                    } else writeResponse(exchange, "История пуста.", 404);
                    break;

                case GET_TASK_ID:
                case GET_EPIC_ID:
                case GET_SUBTASK_ID:
                case GET_SUBTASK_EPIC_ID:
                    responseOrDeleteById(exchange, requestUri, endpoint, true);
                    break;

                case POST_TASK:
                case POST_EPIC:
                case POST_SUBTASK:
                    postTask(exchange, endpoint);
                    break;

                case DELETE_TASK_ID:
                case DELETE_EPIC_ID:
                case DELETE_SUBTASK_ID:
                    responseOrDeleteById(exchange, requestUri, endpoint, false);
                    break;

                case DELETE_TASK:
                    taskManager.clearAllTasks();
                    writeResponse(exchange, "Все задачи были удалены.", 200);
                    break;
                case DELETE_EPIC:
                    taskManager.clearAllEpics();
                    writeResponse(exchange, "Все эпики были удалены.", 200);
                    break;
                case DELETE_SUBTASK:
                    taskManager.clearAllSubtasks();
                    writeResponse(exchange, "Все подзадачи были удалены.", 200);
                    break;

                default:
                    writeResponse(exchange, "Проверьте правильность написания запроса", 400);
            }
        }

        private void responseTask(HttpExchange exchange, Endpoints endpoint) throws IOException {
            String response = null;
            if (endpoint.equals(Endpoints.GET_TASK) && !taskManager.getAllTasks().isEmpty()) {
                response = gson.toJson(taskManager.getAllTasks());
            } else if (endpoint.equals(Endpoints.GET_EPIC) && !taskManager.getAllEpics().isEmpty()) {
                response = gson.toJson(taskManager.getAllEpics());
            } else if (endpoint.equals(Endpoints.GET_SUBTASK) &&!taskManager.getAllSubtasks().isEmpty()) {
                response = gson.toJson(taskManager.getAllSubtasks());
            } else if (endpoint.equals(Endpoints.GET_ALL_TASKS) && !taskManager.getPrioritizedTasks().isEmpty()) {
                response = gson.toJson(taskManager.getPrioritizedTasks());
            }

            if (response == null) {
                writeResponse(exchange, "Список задач пуст.", 404);
            } else writeResponse(exchange, response, 200);
        }

        private void responseOrDeleteById(HttpExchange exchange,
                                          String requestUri,
                                          Endpoints endpoint,
                                          boolean isResponse) throws IOException {
            Optional<Integer> optId = getTaskId(requestUri);

            if (!optId.isEmpty()) {
                String gsonTask = "null";
                int id = optId.get();
                String response = "Задача с идентификатором " + id + " пуста или не существует!";

                if (isResponse) {
                    if (endpoint.equals(Endpoints.GET_SUBTASK_EPIC_ID) &&
                            !taskManager.getAllSubtaskByEpicId(id).isEmpty()) {
                        response = gson.toJson(taskManager.getAllSubtaskByEpicId(id));

                    } else {
                        switch (endpoint) {
                            case GET_TASK_ID:
                                gsonTask = gson.toJson(taskManager.getTaskById(id));
                                break;
                            case GET_EPIC_ID:
                                gsonTask = gson.toJson(taskManager.getEpicById(id));
                                break;
                            case GET_SUBTASK_ID:
                                gsonTask = gson.toJson(taskManager.getSubtaskById(id));
                                break;
                        }
                        if (!gsonTask.equals("null")) response = gsonTask;
                    }
                    writeResponse(exchange, response, 200);
                } else {
                    boolean isOk = false;
                    switch (endpoint) {
                        case DELETE_TASK_ID:
                            if (taskManager.getTaskById(id) != null) {
                                taskManager.removeTaskById(id);
                                isOk = true;
                            }
                            break;
                        case DELETE_EPIC_ID:
                            if (taskManager.getEpicById(id) != null) {
                                taskManager.removeEpicById(id);
                                isOk = true;
                            }

                            break;
                        case DELETE_SUBTASK_ID:
                            if (taskManager.getSubtaskById(id) != null) {
                                taskManager.removeSubtaskById(id);
                                isOk = true;
                            }
                            break;
                    }
                    if (isOk) {
                        writeResponse(exchange, "Удаление подзадачи прошло успешно!", 200);
                    } else writeResponse(exchange, "Некорректный идентификатор задачи", 400);
                }
            } else writeResponse(exchange, "Некорректный идентификатор задачи", 400);
        }

        private void postTask(HttpExchange exchange, Endpoints endpoint) throws IOException {
            InputStream is = exchange.getRequestBody();
            String body = new String(is.readAllBytes(), DEFAULT_CHARSET);

            try {
                if (endpoint.equals(Endpoints.POST_TASK)) {
                    Task task = gson.fromJson(body, Task.class);
                    if (taskManager.getTaskById(task.getId()) != null) {
                        taskManager.updateTask(task);
                        writeResponse(exchange, "Задача успешно обновлена", 200);
                    } else {
                        taskManager.createTask(task);
                        writeResponse(exchange, "Задача успешно создана", 200);
                    }

                } else if (endpoint.equals(Endpoints.POST_EPIC)) {
                    Epic epic = gson.fromJson(body, Epic.class);
                    if (taskManager.getEpicById(epic.getId()) != null) {
                        taskManager.updateEpic(epic);
                        writeResponse(exchange, "Эпик успешно обновлен", 200);
                    } else {
                        taskManager.createEpic(epic);
                        writeResponse(exchange, "Эпик успешно создан", 200);
                    }

                } else if (endpoint.equals(Endpoints.POST_SUBTASK)) {
                    Subtask subtask = gson.fromJson(body, Subtask.class);
                    if (taskManager.getSubtaskById(subtask.getId()) != null) {
                        taskManager.updateSubtask(subtask);
                        writeResponse(exchange, "Подзадача успешно обновлена", 200);
                    } else {
                        taskManager.createSubtask(subtask);
                        writeResponse(exchange, "Подзадача успешно создана", 200);
                    }
                }
            } catch (JsonSyntaxException ex) {
                writeResponse(exchange, "Получен некорректный JSON", 400);
            }
        }

        private Optional<Integer> getTaskId(String requestUri) {
            String[] pathParts = requestUri.split("/");
            try {
                return Optional.of(Integer.parseInt(pathParts[pathParts.length - 1].substring(4)));
            } catch (NumberFormatException exception) {
                return Optional.empty();
            }
        }

        private Endpoints getEndpoint(String requestUri, String requestMethod, String requestPath) {
            String[] uriParts = requestUri.split("/");

            if ("GET".equals(requestMethod)) {
                if (requestUri.contains("?id=")) {
                    if (uriParts.length == 4) {
                        if (uriParts[2].equals("task")) {
                            return Endpoints.GET_TASK_ID;

                        } else if (uriParts[2].equals("epic")) {
                            return Endpoints.GET_EPIC_ID;

                        } else if (uriParts[2].equals("subtask")) {
                            return Endpoints.GET_SUBTASK_ID;

                        }
                    } else if (uriParts.length == 5) {
                        return Endpoints.GET_SUBTASK_EPIC_ID;
                    }

                } else if (uriParts.length == 3) {
                    if (uriParts[2].equals("history")) {
                        return Endpoints.GET_TASKS_HISTORY;

                    } else if (uriParts[2].equals("task")) {
                        return Endpoints.GET_TASK;

                    } else if (uriParts[2].equals("epic")) {
                        return Endpoints.GET_EPIC;

                    } else if (uriParts[2].equals("subtask")) {
                        return Endpoints.GET_SUBTASK;
                    }

                } else if (uriParts.length == 2 && uriParts[1].equals("tasks")) {
                    return Endpoints.GET_ALL_TASKS;
                }

            } else if ("POST".equals(requestMethod)) {
                String[] pathParts = requestPath.split("/");

                if (pathParts.length == 3) {
                    if (pathParts[2].equals("task")) {
                        return Endpoints.POST_TASK;

                    } else if (pathParts[2].equals("epic")) {
                        return Endpoints.POST_EPIC;

                    } else if (pathParts[2].equals("subtask")) {
                        return Endpoints.POST_SUBTASK;
                    }
                }

            } else if ("DELETE".equals(requestMethod)) {
                if (uriParts.length == 4 && uriParts[3].contains("?id=")) {
                    if (uriParts[2].equals("task")) {
                        return Endpoints.DELETE_TASK_ID;

                    }
                    else if (uriParts[2].equals("epic")) {
                        return Endpoints.DELETE_EPIC_ID;

                    }
                    else if (uriParts[2].equals("subtask")) {
                        return Endpoints.DELETE_SUBTASK_ID;

                    }
                } else if (uriParts.length == 3) {
                    if (uriParts[2].equals("task")) {
                        return Endpoints.DELETE_TASK;

                    }  else if (uriParts[2].equals("epic")) {
                        return Endpoints.DELETE_EPIC;

                    }  else if (uriParts[2].equals("subtask")) {
                        return Endpoints.DELETE_SUBTASK;
                    }
                }
            }
            return Endpoints.UNKNOWN;
        }

        private void writeResponse(HttpExchange exchange, String responseString, int responseCode) throws IOException {
            if (responseString.isBlank()) {
                exchange.sendResponseHeaders(responseCode, 0);
            } else {
                byte[] bytes = responseString.getBytes(DEFAULT_CHARSET);
                exchange.sendResponseHeaders(responseCode, bytes.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(bytes);
                }
            }
            exchange.close();
        }
    }
}
