package tn.seif.ecommerce.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import tn.seif.ecommerce.order.entity.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Setter
@Getter

public class OrderConfirmation {

    String orderReference;
    BigDecimal totalAMount;
    PaymentMethod paymentMethod;
    Long paymentId;
    CustomerResponse customer;
    List<PurchaseResponse> products;
}
