package tn.seif.ecommerce.external.api.customer;

import feign.RequestInterceptor;
import feign.Retryer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import tn.seif.ecommerce.exception.BusinessException;

@Configuration
@Slf4j
public class FeignApiCustomerRetryBackoffConfig {
//    @Bean
//    @Retryable(
//            retryFor = {RuntimeException.class},
//            maxAttempts = 3,
//            backoff = @Backoff(delay = 500, multiplier = 2)
//    )
//    public RequestInterceptor retryInterceptor() {
//        return requestTemplate -> {
//
//        };
//    }


    @Bean
    public Retryer retryer() {
        return new Retryer.Default(500, 2500, 3); // initial interval, max interval, and max attempts
    }

//    @Recover
//    public String recover(RuntimeException e) {
//        // Fallback logic
//
//        log.error("Retry  also failed for GET /api/v1/customer/id operation");
//        throw new BusinessException("Retry  also failed for GET /api/v1/customer/id operation");
//    }
}
