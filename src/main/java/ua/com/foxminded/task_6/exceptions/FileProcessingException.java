package ua.com.foxminded.task_6.exceptions;


public class FileProcessingException extends RuntimeException {
    public FileProcessingException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public FileProcessingException(String errorMessage) {
        super(errorMessage);
    }
}