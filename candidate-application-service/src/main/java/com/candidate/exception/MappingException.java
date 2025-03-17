package com.candidate.exception;
/**
 * Exception for handling data mapping errors.
 */
public class MappingException extends RuntimeException {
    public MappingException(String message) {
        super(message);
    }
    /**
     * Constructs a new MappingException with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause   the cause of the exception.
     */
    public MappingException(String message, Throwable cause) {
        super(message, cause);
    }

}
