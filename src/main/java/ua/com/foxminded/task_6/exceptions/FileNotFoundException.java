package ua.com.foxminded.task_6.exceptions;

public class FileNotFoundException extends RuntimeException {
    public FileNotFoundException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
