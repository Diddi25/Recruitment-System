package com.candidate.exception;

/**
 * Exception thrown when a requested resource (e.g., a candidate application) is not found.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructs a new ResourceNotFoundException with a specific message.
     *
     * @param message The error message describing the missing resource.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
