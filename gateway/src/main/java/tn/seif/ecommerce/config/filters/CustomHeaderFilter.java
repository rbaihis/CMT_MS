package tn.seif.ecommerce.config.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Component
@Order(-1) // Ensure this filter is executed early
public class CustomHeaderFilter implements GlobalFilter {
    public final String IDEMPOTENCY_KEY_HEADER= "Idempotency-Key";
    public final String CORRELATION_ID_HEADER= "X-correlation-Id";


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();


            String correlationId = headers.getFirst(CORRELATION_ID_HEADER);
            if (correlationId == null){
                correlationId = UUID.randomUUID().toString();
                log.info("Generated new X-Correlation-Id: {}", correlationId);
            } else {
                log.info("Using existing X-Correlation-Id: {}", correlationId);
            }

            String idempotencyKey = headers.getFirst(IDEMPOTENCY_KEY_HEADER);
            if (idempotencyKey == null){
                idempotencyKey = "not set";
                log.info("X-Idempotency-Key not present, setting to empty string.");
            } else {
                log.info("Using existing X-Idempotency-Key: {}", idempotencyKey);
            }

        // Create a new request with the modified headers
        ServerHttpRequest modifiedRequest = request.mutate()
                    .header(CORRELATION_ID_HEADER, correlationId)
                    .header(IDEMPOTENCY_KEY_HEADER, idempotencyKey)
                .build();


        return chain.filter(exchange);
    }
}
