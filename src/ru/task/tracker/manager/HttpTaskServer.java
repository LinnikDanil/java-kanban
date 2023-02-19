package ru.task.tracker.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import ru.task.tracker.manager.tasks.Task;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

public class HttpTaskServer {
    private static final int CODE200 = 200;
    private static final int PORT = 8080;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private TaskManager taskManagerForHandlers;
    private Gson gsonForHandlers;

    public HttpTaskServer() throws IOException {
        taskManagerForHandlers = FileBackedTasksManager.loadFromFile(new File("dataTasks.csv"));

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
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
            String requestPath = exchange.getRequestURI().getPath();

            Endpoints endpoint = getEndpoint(requestPath, requestMethod);
            switch (endpoint) {
                case GET_ALL_TASKS:
                    String allTasks = gson.toJson(taskManager.getPrioritizedTasks());
                    writeResponse(exchange, allTasks, CODE200);
                    break;
                case GET_SUBTASK_EPIC_ID:
                    int id = getTaskId(exchange).get();
                    String subtasksByEpic = gson.toJson(taskManager.getSubtaskById(id));
                    writeResponse(exchange, subtasksByEpic, CODE200);
                    break;
//                case GET_ALL_TASKS:
//                    String allTasks = gson.toJson(taskManager.getPrioritizedTasks());
//                    writeResponse(exchange, allTasks, CODE200);
//                    break;
//                        GET_TASK,
//                        GET_TASK_ID,
//                        POST_TASK,
//                        DELETE_TASK,
//                        DELETE_TASK_ID,
//
//                        GET_EPIC,
//                        GET_EPIC_ID,
//                        POST_EPIC,
//                        DELETE_EPIC,
//                        DELETE_EPIC_ID,
//
//                        GET_SUBTASK,
//                        GET_SUBTASK_ID,
//                        POST_SUBTASK,
//                        DELETE_SUBTASK,
//                        DELETE_SUBTASK_ID,
//                        GET_SUBTASK_EPIC_ID,
//
//                        GET_TASKS_HISTORY,
//
//                        UNKNOWN
            }
        }
    }

    private Optional<Integer> getTaskId(HttpExchange exchange) {
        String[] pathParts = exchange.getRequestURI().getPath().split("/");
        try {
            return Optional.of(Integer.parseInt(pathParts[pathParts.length-1]));
        } catch (NumberFormatException exception) {
            return Optional.empty();
        }
    }

    private Endpoints getEndpoint(String requestPath, String requestMethod) {
        String[] pathParts = requestPath.split("/");

        System.out.println(pathParts.toString());

        if ("GET".equals(requestMethod)) {

            if (pathParts.length == 2 && pathParts[1].equals("tasks")) {
                return Endpoints.GET_ALL_TASKS;

            } else if (pathParts.length == 3 && pathParts[2].equals("history")) {
                return Endpoints.GET_TASKS_HISTORY;

            } else if (pathParts.length == 5 && pathParts[5].equals("?id")) {
                return Endpoints.GET_SUBTASK_EPIC_ID;
            }
        } else if ("POST".equals(requestMethod)) {
            return null;
        } else if ("DELETE".equals(requestMethod)) {
            return null;
        }
        return Endpoints.UNKNOWN;
    }

    private void writeResponse(HttpExchange exchange,
                               String responseString,
                               int responseCode) throws IOException {
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
