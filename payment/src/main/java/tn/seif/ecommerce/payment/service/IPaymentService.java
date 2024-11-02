package tn.seif.ecommerce.payment.service;

import tn.seif.ecommerce.payment.dto.PaymentRequest;

public interface IPaymentService {
    Long createPayment(PaymentRequest request);
}
