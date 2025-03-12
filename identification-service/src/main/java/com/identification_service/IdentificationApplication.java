package com.identification_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Identification application.
 */
@SpringBootApplication
public class IdentificationApplication {

    /**
     * The main method that runs the Spring Boot application.
     *
     * @param args command-line arguments passed during the application startup.
     */
    public static void main(String[] args) {
        SpringApplication.run(IdentificationApplication.class, args);
    }
}
