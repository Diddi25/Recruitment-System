package com.candidate.exception;
/**
 * Exception for handling data mapping errors.
 */
public class MappingException extends RuntimeException {
    public MappingException(String message) {
        super(message);
    }

}
