package tn.seif.ecommerce.external.api.payment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import tn.seif.ecommerce.exception.BusinessException;
import tn.seif.ecommerce.order.dto.PaymentRequest;

@Service
@RequiredArgsConstructor
@Slf4j
//client call using RestTemplate
public class PaymentClientIMP implements PaymentClientService {

    @Value("${application.config.payment-url}")
    private String payment_url;
    private final RestTemplate restTemplate; /*bean config defined*/



    @Recover
    public String recover(RuntimeException e) {
        // Fallback logic

        log.error("RetryAttempts  also failed for /api/v1/payment operation");
        throw new BusinessException("Retry  also failed for /api/v1/payment operation");
    }

    @Retryable(
            stateful = true,
            retryFor = {RuntimeException.class},
            maxAttempts = 3, // Number of retry attempts
            backoff = @Backoff(delay = 300 ,multiplier = 2) // 300ms delay between retries
    )
    @Override
    public Long processPayment(PaymentRequest requestBody) {

        ResponseEntity<Long> responseEntity;
        try {
            //request
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            HttpEntity<PaymentRequest> requestEntity = new HttpEntity<>(requestBody, headers);
            //response
            ParameterizedTypeReference<Long> responseType =
                    new ParameterizedTypeReference<Long>() {
                    };
            responseEntity = restTemplate.exchange(
                    payment_url,
                    HttpMethod.POST,
                    requestEntity,
                    responseType
            );
        } catch (
                HttpClientErrorException e) {
            // Handle 4xx errors
            log.warn("Client failed api/payment: {} ", e.getResponseBodyAsString());
            throw new BusinessException("Bad request: reason: " + e.getResponseBodyAsString());

        } catch (
                HttpServerErrorException e) {
            // Handle 5xx errors
            log.error("ClientServer api/payment  failed due to serverSide problem calling : {}", e.getResponseBodyAsString());
            throw new BusinessException("ServerError: Occurred when reaching payment, when creating order , try-again and contact Us if issue persist");
        }

//        if (responseEntity.getStatusCode().isError())
//            throw new BusinessException("Error Occurred While processing the Payment " + responseEntity.getStatusCode());

        return responseEntity.getBody();

    }

}
