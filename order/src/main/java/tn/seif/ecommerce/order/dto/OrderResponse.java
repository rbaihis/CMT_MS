package tn.seif.ecommerce.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import tn.seif.ecommerce.order.entity.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Setter
@Getter

public class OrderResponse {
    private Long id;
    private String reference;
    private BigDecimal totalAmount;
    private PaymentMethod paymentMethod;
    private String customerId;
    private LocalDateTime createdDate;
}
