package tn.seif.ecommerce.external.producer.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import tn.seif.ecommerce.order.dto.OrderConfirmation;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderPublisherKafkaIMP implements OrderPublisher{

    private final KafkaTemplate<String, OrderConfirmation> kafkaTemplate;
    @Value("${notification-service.message.destination}")
    private  String orderTopic;
    @Override
    public void sendOrderConfirmation(OrderConfirmation orderConfirmation){

        log.info("sending order confirmation...");
        Message<OrderConfirmation> message = MessageBuilder
                .withPayload(orderConfirmation)
                .setHeader(KafkaHeaders.TOPIC,orderTopic)
                .build();

        kafkaTemplate.send(message);
    }
}
