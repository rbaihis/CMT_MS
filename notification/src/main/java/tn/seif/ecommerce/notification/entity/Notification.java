package tn.seif.ecommerce.notification.entity;

import lombok.*;
import tn.seif.ecommerce.notification.dto.OrderConfirmation;
import tn.seif.ecommerce.notification.dto.PaymentConfirmation;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Notification {

    private NotificationType notificationType;
    private final LocalDateTime dateTime = LocalDateTime.now();

    private OrderConfirmation orderConfirmation;
    private PaymentConfirmation paymentConfirmation;

}
