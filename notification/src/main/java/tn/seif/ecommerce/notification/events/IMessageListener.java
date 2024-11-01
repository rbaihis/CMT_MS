package tn.seif.ecommerce.notification.events;

import jakarta.mail.MessagingException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.Acknowledgment;
import tn.seif.ecommerce.notification.dto.OrderConfirmation;
import tn.seif.ecommerce.notification.dto.PaymentConfirmation;



public interface IMessageListener {

    void ConsumePaymentSuccessEmailNotification(ConsumerRecord<String,PaymentConfirmation> record, Acknowledgment acknowledgment) throws MessagingException;

    void ConsumeOrderSuccessEmailNotification(ConsumerRecord<String,OrderConfirmation> record, Acknowledgment acknowledgment) throws MessagingException;
}

