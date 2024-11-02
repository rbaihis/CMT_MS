package tn.seif.ecommerce.notification.dto;

import lombok.*;


import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class OrderConfirmation {

    String orderReference;
    BigDecimal totalAMount;
    PaymentMethod paymentMethod;
    Long paymentId;
    Customer customer;
    List<Product> products;
}
