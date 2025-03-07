package com.identification_service.exception;

/**
 * Exception thrown when validation of input data fails.
 */

public class ValidationFailedException extends RuntimeException {

    /**
     * Creates a new exception with a given message.
     *
     * @param message Description of the validation failure.
     */
    public ValidationFailedException(String message) {
        super(message);
    }
}