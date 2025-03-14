package com.candidate.exception;

/**
 * Exception thrown when a database error occurs.
 */
public class DatabaseException extends RuntimeException {
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
