package tn.seif.ecommerce.notification.events;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import tn.seif.ecommerce.email.IEmailService;
import tn.seif.ecommerce.notification.dto.OrderConfirmation;
import tn.seif.ecommerce.notification.dto.PaymentConfirmation;
import tn.seif.ecommerce.notification.entity.Notification;
import tn.seif.ecommerce.notification.entity.NotificationType;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageListener implements IMessageListener {
    private final ConsumerMessageHandler messageHandler;

    @Override
    @KafkaListener(topics = "${notification-service.message.queue-payment}")
    public void ConsumePaymentSuccessEmailNotification(ConsumerRecord<String,PaymentConfirmation> record, Acknowledgment acknowledgment) throws MessagingException {
        messageHandler.handlePaymentNotification(record,acknowledgment);
    }

//--------------------------
    @Override
    @KafkaListener(topics = "${notification-service.message.queue-order}")
    public void ConsumeOrderSuccessEmailNotification(ConsumerRecord<String,OrderConfirmation> record, Acknowledgment acknowledgment) throws MessagingException {
        messageHandler.handleOrderNotification(record,acknowledgment);
    }

}
