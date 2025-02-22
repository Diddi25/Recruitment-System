package com.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("advertisement_service", r -> r
						.path("/api/advertisements/**", "/api/advertisements") // Lägg till /api/advertisements utan wildcard
						.filters(f -> f.rewritePath("/api/advertisements(?:/(?<remaining>.*))?", "/api/v1/advertisements/${remaining}"))
						.uri("http://localhost:8082"))
				.route("identification_service", r -> r
						.path("/api/auth/**", "/api/auth")
						.filters(f -> f.rewritePath("/api/auth(?:/(?<remaining>.*))?", "/api/auth/${remaining}"))
						.uri("http://localhost:8083"))
				.build();
	}

}