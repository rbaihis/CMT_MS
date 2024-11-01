package tn.seif.ecommerce.email;



import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.util.HashMap;
import java.util.Map;

import tn.seif.ecommerce.notification.entity.IdempotencyPrefixType;
import tn.seif.ecommerce.notification.entity.Notification;
import tn.seif.ecommerce.notification.entity.NotificationType;
import tn.seif.ecommerce.notification.service.IIdempotencyService;


@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService implements IEmailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final IIdempotencyService idempotencyService;

    @Value("${email-service.sentfrom}")
    private  String sentFrom;


    public void orderMimeHelperSet(MimeMessageHelper mimeHelper, Notification notification, Context context)throws MessagingException{
        //template key:value variables
        Map<String, Object> variables= new HashMap<>();
        String fullName= notification.getOrderConfirmation().getCustomer().getFirstName()+" "+notification.getOrderConfirmation().getCustomer().getLastName();
        variables.put("customerName", fullName);
        variables.put("orderReference",notification.getOrderConfirmation().getOrderReference());
        variables.put("totalAmount", notification.getOrderConfirmation().getTotalAMount());
        variables.put("products",notification.getOrderConfirmation().getProducts());
        context.setVariables(variables);
        final String templateName = EmailTemplates.ORDER_CONFIRMATION.getTemplate();
        String htmlTemplate = templateEngine.process(templateName, context);

        mimeHelper.setText(htmlTemplate, true); // ready html string
        mimeHelper.setSubject(EmailTemplates.ORDER_CONFIRMATION.getSubject());
        mimeHelper.setTo(notification.getOrderConfirmation().getCustomer().getEmail());
    }
    public void paymentMimeHelperSet(MimeMessageHelper mimeHelper, Notification notification, Context context) throws MessagingException {
        //template key:value variables
        Map<String, Object> variables= new HashMap<>();
        String fullName= notification.getPaymentConfirmation().getFirstName()+" "+notification.getPaymentConfirmation().getLastName();
        variables.put("customerName", fullName);
        variables.put("amount", notification.getPaymentConfirmation().getAmount());
        variables.put("orderReference",notification.getPaymentConfirmation().getOrderReference());
        context.setVariables(variables);
        final String templateName = EmailTemplates.PAYMENT_CONFIRMATION.getTemplate();
        String htmlTemplate = templateEngine.process(templateName, context);

        mimeHelper.setText(htmlTemplate, true); // ready html string
        mimeHelper.setSubject(EmailTemplates.PAYMENT_CONFIRMATION.getSubject());
        mimeHelper.setTo(notification.getPaymentConfirmation().getEmail());
    }



    @Async
    @Override
    public void sendAsyncEmailIdempotent (Notification notification,  String idempotentKey)  {

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeHelper = new MimeMessageHelper(
                    mimeMessage,MimeMessageHelper.MULTIPART_MODE_RELATED, UTF_8.name());

            mimeHelper.setFrom(sentFrom);
            //html-template context
            Context context = new Context();

            if(notification.getNotificationType().equals(NotificationType.EMAIL_ORDER ))
                orderMimeHelperSet(mimeHelper,notification,context);

            if(notification.getNotificationType().equals(NotificationType.EMAIL_PAYMENT ))
                paymentMimeHelperSet(mimeHelper,notification,context);

            mailSender.send(mimeMessage);

            log.info("Email with idempotency-key:{} sent of type : {}", idempotentKey,notification.getNotificationType());


        } catch (MessagingException e) {
            log.error("Failed to send email with idempotency-key:{} of type {}. with cause: {}",idempotentKey, notification.getNotificationType(),e.getMessage());
        }

    }





}
