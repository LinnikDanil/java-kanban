package ru.task.tracker.manager.exceptions;

/**
 * Класс собственного исключения для работы с файлами в FileBackedTasksManager
 */
public class ManagerSaveException extends RuntimeException{

    public ManagerSaveException(String message){
        super(message);
    }
}
