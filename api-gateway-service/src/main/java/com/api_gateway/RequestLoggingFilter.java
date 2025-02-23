package com.api_gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class RequestLoggingFilter implements GlobalFilter {
    
    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        
        // Logga basic request info
        logger.info("Request Path: {}", request.getPath());
        logger.info("Request Method: {}", request.getMethod());
        logger.info("Request Headers: {}", request.getHeaders());

        // Logga request body
        /*
        if (request.getBody() != null) {
            return DataBufferUtils.join(request.getBody())
                .flatMap(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    String body = new String(bytes);
                    logger.info("Request Body: {}", body);
                    
                    // Återskapa request body eftersom den konsumerades vid läsning
                    DataBuffer bodyDataBuffer = exchange.getResponse().bufferFactory().wrap(bytes);
                    ServerHttpRequest mutatedRequest = request.mutate()
                        .body(Mono.just(bodyDataBuffer))
                        .build();
                    
                    return chain.filter(exchange.mutate().request(mutatedRequest).build())
                        .then(Mono.fromRunnable(() -> {
                            logger.info("Response Status: {}", exchange.getResponse().getStatusCode());
                            logger.info("Response Headers: {}", exchange.getResponse().getHeaders());
                        }));
                });
        }
        */

        return chain.filter(exchange)
            .then(Mono.fromRunnable(() -> {
                logger.info("Response Status: {}", exchange.getResponse().getStatusCode());
                logger.info("Response Headers: {}", exchange.getResponse().getHeaders());
            }));
    }
}