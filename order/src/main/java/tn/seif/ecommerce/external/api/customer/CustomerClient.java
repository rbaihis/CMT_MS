package tn.seif.ecommerce.external.api.customer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tn.seif.ecommerce.order.dto.CustomerResponse;

import java.util.Optional;

@FeignClient(
        name = "customer-service",
        url = "${application.config.customer-url}",
        configuration = FeignApiCustomerRetryBackoffConfig.class // Link to the configuration with retry
)
public interface CustomerClient {
    @GetMapping("/{id}")
    Optional<CustomerResponse> findCustomerById(@PathVariable("id") String id);


}
