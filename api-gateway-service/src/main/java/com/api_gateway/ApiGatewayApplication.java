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
				// Route för frontend
				.route("frontend_route", r -> r
						.path("/**")
						.uri("http://localhost:5173"))
				.route("advertisement_service", r -> r
						.path("/api/advertisements/**")
						.filters(f -> f.rewritePath("/api/advertisements/(?<segment>.*)",
								"/api/v1/advertisements/${segment}"))
						.uri("http://localhost:8082"))
				.build();
	}

}
