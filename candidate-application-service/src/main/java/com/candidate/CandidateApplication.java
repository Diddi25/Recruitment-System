package com.candidate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Main class for the Candidate Application microservice.
 * This class bootstraps the Spring Boot application.
 */
@SpringBootApplication
public class CandidateApplication {

    /**
     * The entry point of the Candidate Application.
     * Initializes and starts the Spring Boot application.
     *
     * @param args Command-line arguments passed during application startup.
     */
    public static void main(String[] args) {
        SpringApplication.run(CandidateApplication.class, args);
    }

}
