package com.candidate.exception;


/**
 * Exception for handling failures in external service calls.
 * Thrown when a microservice or external API call fails.
 */
public class ExternalServiceException extends RuntimeException {
    public ExternalServiceException(String message) {
        super(message);
    }

    public ExternalServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}