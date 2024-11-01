package tn.seif.ecommerce.config.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/*
    FIlter not used just example
 */

@Component
@Order(0)
@Slf4j
//todo in app.properties : (add the filter to either [routes.id.filters |or| spring.cloud.gateway.default-filters= CorrelationIdAndIdempotencyKeyFilter])
public class CorrelationIdAndIdempotencyKeyFilter extends AbstractGatewayFilterFactory<CorrelationIdAndIdempotencyKeyFilter.Config> {

    public CorrelationIdAndIdempotencyKeyFilter() {
        super(Config.class);
    }

    public final String IDEMPOTENCY_KEY_HEADER= "X-Idempotency-Key";
    public final String CORRELATION_ID_HEADER= "X-correlation-Id";

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

//            ServerHttpRequest request = exchange.getRequest();
//            HttpHeaders headers = request.getHeaders();


            // do what u want

            // Create a new request with the modified headers
//            ServerHttpRequest modifiedRequest = request.mutate()
//                    .header(CORRELATION_ID_HEADER, correlationId)
//                    .header(IDEMPOTENCY_KEY_HEADER, idempotencyKey)
//                    .build();


            return chain.filter(
                    exchange.mutate()
//                    .request(modifiedRequest)
                    .build()
            );

        };
    }

    public static class Config {
        // do your configuration for custom usage in application.properties for filter args , example to set values for header as example
        // to not be dependent on static names (just an example)
    }



}
