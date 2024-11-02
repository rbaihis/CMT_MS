package tn.seif.ecommerce.external.producer.notification;

import tn.seif.ecommerce.payment.dto.PaymentConfirmation;

public interface NotificationPublisherService {
    void sendPaymentNotification(PaymentConfirmation confirmation);
}
