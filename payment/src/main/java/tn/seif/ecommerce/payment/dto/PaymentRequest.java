package tn.seif.ecommerce.payment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import tn.seif.ecommerce.payment.entity.PaymentMethod;

@Builder
@Setter
@Getter

public class PaymentRequest {
    private Long id;
    @NotNull(message = "orderId is Mandatory")
    private Long orderId;
    @NotNull(message = "orderReference is required ")
    private String orderReference;
    @NotNull(message = "totalAmount is required ")
    private Double totalAmount;
    @NotNull(message = "paymentMethod is required ")
    private PaymentMethod paymentMethod;
    @NotNull(message = "customer object is required ")
    private Customer customer;
}
