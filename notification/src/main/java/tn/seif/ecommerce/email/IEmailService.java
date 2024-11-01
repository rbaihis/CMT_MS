package tn.seif.ecommerce.email;

import jakarta.mail.MessagingException;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.scheduling.annotation.Async;
import tn.seif.ecommerce.notification.entity.Notification;

public interface IEmailService {

    @Async
    void sendAsyncEmailIdempotent(Notification notification, String keyIdempotent) throws MessagingException;
}
