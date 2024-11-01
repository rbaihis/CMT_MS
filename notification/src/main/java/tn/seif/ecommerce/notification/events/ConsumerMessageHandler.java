package tn.seif.ecommerce.notification.events;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import tn.seif.ecommerce.context.threadlocal.IdempotencyKeyContextManager;
import tn.seif.ecommerce.email.IEmailService;
import tn.seif.ecommerce.exception.CustomServerErrorException;
import tn.seif.ecommerce.exception.DuplicatedRequestException;
import tn.seif.ecommerce.exception.NotAcceptedFormatIdempotencyKeyException;
import tn.seif.ecommerce.notification.dto.OrderConfirmation;
import tn.seif.ecommerce.notification.dto.PaymentConfirmation;
import tn.seif.ecommerce.notification.entity.IdempotencyPrefixType;
import tn.seif.ecommerce.notification.entity.Notification;
import tn.seif.ecommerce.notification.service.IIdempotencyService;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConsumerMessageHandler {
    private final IEmailService emailAsyncService;
    private final IIdempotencyService idempotencyService;

    //-----------private---------
    private void exceptionHandlingIdempotencyWithRedis(RuntimeException ex, Acknowledgment acknowledgment){
        String keyIdempotent = IdempotencyKeyContextManager.getIdempotencyKey();
        log.warn("Failed to to process message with idempotency key : {} ", keyIdempotent);
        if (ex instanceof CustomServerErrorException) {
            log.error("Caught CustomServerErrorException: {}", ((CustomServerErrorException) ex).getMsg());
        } else if (ex instanceof DuplicatedRequestException) {
            log.warn("Caught DuplicatedRequestException: {}", ((DuplicatedRequestException) ex).getMsg());
            acknowledgment.acknowledge();
        } else if (ex instanceof NotAcceptedFormatIdempotencyKeyException) {
            log.error("Caught NotAcceptedFormatIdempotencyKeyException: {}", ((NotAcceptedFormatIdempotencyKeyException) ex).getMsg());
        }
    }

    //------------public---------
    public void handleOrderNotification(ConsumerRecord<String, OrderConfirmation> record, Acknowledgment acknowledgment) throws MessagingException {
        log.info("handling Consuming orderNotification message .........");

        try {
            idempotencyService.assertIdempotency(86400, IdempotencyPrefixType.ORDER);
        } catch (DuplicatedRequestException | NotAcceptedFormatIdempotencyKeyException | CustomServerErrorException ex ) {
            exceptionHandlingIdempotencyWithRedis(ex , acknowledgment);
            return;
        }


        String keyIdempotent = IdempotencyKeyContextManager.getIdempotencyKey();
        Notification notification = MessageProcessor.mapOrderConfirmationToNotification(record.value());
        if (notification != null){
            emailAsyncService.sendAsyncEmailIdempotent(notification, keyIdempotent);
            acknowledgment.acknowledge();
        }

    }

    public void handlePaymentNotification(ConsumerRecord<String, PaymentConfirmation> record, Acknowledgment acknowledgment) throws MessagingException {
        log.info("handling Consuming paymentNotification message .........");

        try {
            idempotencyService.assertIdempotency(86400, IdempotencyPrefixType.PAYMENT);
        }catch (DuplicatedRequestException | NotAcceptedFormatIdempotencyKeyException | CustomServerErrorException ex) {
            exceptionHandlingIdempotencyWithRedis(ex , acknowledgment );
        }

        String keyIdempotent = IdempotencyKeyContextManager.getIdempotencyKey();
        Notification notification = MessageProcessor.mapPaymentConfirmationToNotification(record.value());
        if (notification != null) {
            emailAsyncService.sendAsyncEmailIdempotent(notification, keyIdempotent);
            acknowledgment.acknowledge();
        }

    }
}
