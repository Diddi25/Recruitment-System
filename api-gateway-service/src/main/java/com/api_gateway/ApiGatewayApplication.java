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
						.path("/api/advertisements/**", "/api/advertisements")
						.filters(f -> f
								.rewritePath("/api/advertisements(?:/(?<remaining>.*))?", "/api/v1/advertisements/${remaining}")
								.removeRequestHeader("Cookie") // Ta bort onödiga cookies om de orsakar problem
								.preserveHostHeader() // Behåller den ursprungliga host-headern
						)
						.uri("http://localhost:8082"))
				.route("identification_service", r -> r
						.path("/api/identification/**", "/api/identification")
						.filters(f -> f
								.rewritePath("/api/identification(?:/(?<remaining>.*))?", "/api/v1/identification/${remaining}")
								.removeRequestHeader("Cookie") // Ta bort cookies om nödvändigt
								.preserveHostHeader() // Viktigt för att behålla host-headern
								.filter((exchange, chain) -> { // Anpassad filter för att vidarebefordra Authorization-headern
									if (exchange.getRequest().getHeaders().containsKey("Authorization")) {
										String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
										exchange.getRequest().mutate().header("Authorization", authHeader);
									}
									return chain.filter(exchange);
								})
						)
						.uri("http://localhost:8083"))
				.build();
	}


}