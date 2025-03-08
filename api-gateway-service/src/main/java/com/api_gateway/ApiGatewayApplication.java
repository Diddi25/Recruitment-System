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
						.filters(f -> f
								.rewritePath("/api/advertisements(?:/(?<remaining>.*))?", "/api/v1/advertisements/${remaining}")
								.removeRequestHeader("Cookie") // Ta bort onödiga cookies om de orsakar problem
								.preserveHostHeader() // Behåller den ursprungliga host-headern
								.filter((exchange, chain) -> { // Anpassad filter för att vidarebefordra Authorization-headern
									if (exchange.getRequest().getHeaders().containsKey("Authorization")) {
										String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
										exchange.getRequest().mutate().header("Authorization", authHeader);
									}
									return chain.filter(exchange);
								})
								.setResponseHeader("Access-Control-Allow-Origin", "http://localhost:5173")
						)
						.uri("http://localhost:8082"))
				.route("identification_service", r -> r
						.path("/api/identification/**", "/api/identification")
						.filters(f -> f
								.rewritePath("/api/identification(?:/(?<remaining>.*))?", "/api/v1/identification/${remaining}")
								.filter((exchange, chain) -> { // Anpassad filter för att vidarebefordra Authorization-headern
									if (exchange.getRequest().getHeaders().containsKey("Authorization")) {
										String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
										exchange.getRequest().mutate().header("Authorization", authHeader);
									}
									return chain.filter(exchange);
								})
								.setResponseHeader("Access-Control-Allow-Origin", "http://localhost:5173")
						)
						.uri("http://localhost:8083"))
				.route("candidate_application_service", r -> r
						.path("/api/applications/**", "/api/applications") // Matches all requests to CandidateApplicationController
						.filters(f -> f
								.rewritePath("/api/applications(?:/(?<remaining>.*))?", "/api/applications/${remaining}")
								.filter((exchange, chain) -> { // Anpassad filter för att vidarebefordra Authorization-headern
									if (exchange.getRequest().getHeaders().containsKey("Authorization")) {
										String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
										exchange.getRequest().mutate().header("Authorization", authHeader);
									}
									return chain.filter(exchange);
								})
								.setResponseHeader("Access-Control-Allow-Origin", "http://localhost:5173")
						)
						.uri("http://localhost:8084")) // Forward requests to Candidate Application Service at port 8084
				.build();
	}

}