package tn.seif.ecommerce.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import tn.seif.ecommerce.order.entity.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderRequest {
    Integer id;
    String reference;
    @Positive(message = "order amount should be positive")
    BigDecimal amount;
    @NotNull(message = "Payment Method should be precised")
    PaymentMethod paymentMethod;
    @NotNull(message = "customer should be present")
    @NotEmpty(message = "customer should be present")
    @NotBlank(message = "customer should be present")
    String customerId;
    @NotEmpty(message = "you should at least purchase at least one")
    List<PurchaseRequest> products;

}
