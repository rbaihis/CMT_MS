package tn.seif.ecommerce.notification.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.seif.ecommerce.notification.dto.OrderConfirmation;
import tn.seif.ecommerce.notification.dto.PaymentConfirmation;
import tn.seif.ecommerce.notification.entity.Notification;
import tn.seif.ecommerce.notification.entity.NotificationType;

@Slf4j
public class MessageProcessor {


    public static Notification mapPaymentConfirmationToNotification(PaymentConfirmation confirmation){

        return Notification.builder()
                .notificationType(NotificationType.EMAIL_PAYMENT)
                .paymentConfirmation(confirmation)
                .build();
    }

    public static Notification mapOrderConfirmationToNotification(OrderConfirmation confirmation){

        return Notification.builder()
                .notificationType(NotificationType.EMAIL_ORDER)
                .orderConfirmation(confirmation)
                .build();
    }
}
