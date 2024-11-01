package tn.seif.ecommerce.external.producer.notification;

import tn.seif.ecommerce.order.dto.OrderConfirmation;

public interface OrderPublisher {
    void sendOrderConfirmation(OrderConfirmation orderConfirmation);
}
