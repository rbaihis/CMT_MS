package tn.seif.ecommerce.payment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter

public class PaymentResponse {
    private Long id;
    private Double totalAmount;
    private Long paymentMethod;
}
