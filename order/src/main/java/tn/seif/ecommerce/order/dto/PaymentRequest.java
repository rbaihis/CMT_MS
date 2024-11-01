package tn.seif.ecommerce.order.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import tn.seif.ecommerce.order.entity.PaymentMethod;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class PaymentRequest {

    @NotNull(message = "orderId is Mandatory")
    private Long orderId;
    @NotNull(message = "orderReference is required ")
    private String orderReference;
    @NotNull(message = "totalAmount is required ")
    private Double totalAmount;
    @NotNull(message = "paymentMethod is required ")
    private PaymentMethod paymentMethod;
    @NotNull(message = "customer object is required ")
    private CustomerResponse customer;



}
