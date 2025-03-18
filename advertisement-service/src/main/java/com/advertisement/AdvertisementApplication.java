package com.advertisement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>Main entry point for the Advertisement application.</p>
 *
 * <p>This class is annotated with {@link SpringBootApplication}, which marks it as the primary
 * Spring Boot application class. It enables auto-configuration, component scanning, and
 * configuration properties.</p>
 */
@SpringBootApplication
public class AdvertisementApplication {

    /**
     * Starts the Advertisement application.
     *
     * @param args command-line arguments passed during application startup
     */
    public static void main(String[] args) {
        SpringApplication.run(AdvertisementApplication.class, args);
    }

}
