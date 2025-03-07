package com.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

/**
 * Main class for the API Gateway application.
 * This class initializes the Spring Boot application and defines routing rules for services.
 */

@SpringBootApplication
public class ApiGatewayApplication {

	/**
	 * Main method to start the Spring Boot application.
	 *
	 * @param args command-line arguments
	 */

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	/**
	 * Configures route mappings for API requests to different microservices.
	 *
	 * @param builder the RouteLocatorBuilder used to define routing rules
	 * @return a RouteLocator containing the defined routes
	 */

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("advertisement_service", r -> r
						.path("/api/advertisements/**", "/api/advertisements")
						.filters(f -> f.rewritePath("/api/advertisements(?:/(?<remaining>.*))?", "/api/v1/advertisements/${remaining}"))
						.uri("http://localhost:8082"))
				.route("identification_service", r -> r
						.path("/api/auth/**", "/api/auth")
						.filters(f -> f.rewritePath("/api/auth(?:/(?<remaining>.*))?", "/api/auth/${remaining}"))
						.uri("http://localhost:8083"))
				.build();
	}

}