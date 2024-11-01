package tn.seif.ecommerce.payment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class PaymentConfirmation {
    private String orderReference;
    private Long paymentId;
    private Long amount;
    private String firstName;
    private String lastName;
    private String email;
}
