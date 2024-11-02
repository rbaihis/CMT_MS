package tn.seif.ecommerce.external.api.payment;


import tn.seif.ecommerce.order.dto.PaymentRequest;

public interface PaymentClientService {
    Long processPayment(PaymentRequest requestBody);
}
