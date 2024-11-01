package tn.seif.ecommerce.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.seif.ecommerce.external.producer.notification.NotificationPublisherService;
import tn.seif.ecommerce.payment.dto.PaymentRequest;
import tn.seif.ecommerce.payment.entity.Payment;
import tn.seif.ecommerce.payment.repository.PaymentRepo;
import tn.seif.ecommerce.payment.util.ConfirmationMapper;
import tn.seif.ecommerce.payment.util.PaymentMapper;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService implements IPaymentService {
    private final PaymentMapper mapper;
    private final ConfirmationMapper confirmationMapper;
    private final PaymentRepo repo;
    //messageBroker
    private final NotificationPublisherService notificationPublisher;

    @Override
    public Long createPayment(PaymentRequest request) {

        log.info("processing payment .........");
        Payment payment = repo.save(mapper.toPayment(request));

        log.info("processing notification .........");
        notificationPublisher.sendPaymentNotification(
            confirmationMapper.payRequestToPaymentConfirmation(request)
        );


        return payment.getId();
    }




}
