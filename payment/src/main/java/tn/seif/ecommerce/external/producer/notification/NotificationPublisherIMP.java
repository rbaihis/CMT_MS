package tn.seif.ecommerce.external.producer.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import tn.seif.ecommerce.payment.dto.PaymentConfirmation;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationPublisherIMP implements NotificationPublisherService {

    @Value("${notification-service.message.destination}")
    private String topic;

    private final KafkaTemplate<String, PaymentConfirmation> template;

    @Override
    public void sendPaymentNotification(PaymentConfirmation confirmation){
        log.info("sending payment notification confirmation");

        Message<PaymentConfirmation> message = MessageBuilder
                .withPayload(confirmation)
                .setHeader(KafkaHeaders.TOPIC,topic)
                .build();

        template.send(message);
    }

}
