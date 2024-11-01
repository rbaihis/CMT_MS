package tn.seif.ecommerce.notification.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class PaymentConfirmation {
    private String orderReference;
    private Long amount;
    private String firstName;
    private String lastName;
    private String email;
}
