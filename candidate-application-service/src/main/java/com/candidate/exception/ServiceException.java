package com.candidate.exception;

/**
 * Exception thrown when an error occurs in the service layer.
 */
public class ServiceException extends RuntimeException {

    /**
     * Constructs a new ServiceException with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause   the cause of the exception.
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
