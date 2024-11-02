package tn.seif.ecommerce.external.api.product;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import tn.seif.ecommerce.exception.BusinessException;
import tn.seif.ecommerce.order.dto.PurchaseRequest;
import tn.seif.ecommerce.order.dto.PurchaseResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
//client call using RestTemplate
public class ProductClientServiceIMP implements ProductClientService {

    @Value("${application.config.product-url}")
    private String product_url;
    private final RestTemplate restTemplate; /*bean config defined*/


    @Recover
    public String recover(RuntimeException e) {
        // Fallback logic

        log.error("Retry  also failed for /api/v1/product/purchase operation");
        throw new BusinessException("Retry  also failed for /api/v1/purchase operation");
    }

    @Override
    @Retryable(
            stateful = true,
            retryFor = {RuntimeException.class},
            maxAttempts = 3, // Number of retry attempts
            backoff = @Backoff(delay = 300 ,multiplier = 2) // 300ms delay between retries
    )
    public List<PurchaseResponse> purchaseProduct(List<PurchaseRequest> requestBody) {

        try {
            //preparing-request
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            HttpEntity<List<PurchaseRequest>> requestEntity = new HttpEntity<>(requestBody, headers);

            //preparing-response
            ParameterizedTypeReference<List<PurchaseResponse>> responseType =
                    new ParameterizedTypeReference<List<PurchaseResponse>>() {};
            return restTemplate.exchange(
                    product_url + "/purchase",
                    HttpMethod.POST,
                    requestEntity,
                    responseType
                ).getBody();

        } catch (HttpClientErrorException e) {
            // Handle 4xx errors
            log.warn("Client failed api/product/purchase: {} " , e.getResponseBodyAsString());
            throw new BusinessException("Bad request: reason: "+ e.getResponseBodyAsString());

        } catch (HttpServerErrorException e) {
            // Handle 5xx errors
            log.error("ClientServer api/product/purchase  failed due to serverSide problem calling : {}" , e.getResponseBodyAsString());
            throw new BusinessException("ServerError: Occurred when reaching purchasingProduct, when creating order , try-again and contact Us if issue persist");
        }


    }



}
