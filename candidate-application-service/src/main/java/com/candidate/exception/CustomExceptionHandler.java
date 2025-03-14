package com.candidate.exception;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.Map;

/**
 * Simplified global exception handler for handling key errors in the application.
 */
@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MappingException.class)
    public ResponseEntity<String> handleMappingException(MappingException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<Map<String, String>> handleDatabaseException(DatabaseException ex) {
        log.error("Database error: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "A database error occurred. Please try again later."));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "An unexpected error occurred."));
    }
}