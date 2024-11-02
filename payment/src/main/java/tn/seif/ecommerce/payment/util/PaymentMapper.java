package tn.seif.ecommerce.payment.util;

import org.springframework.stereotype.Service;
import tn.seif.ecommerce.payment.dto.PaymentRequest;
import tn.seif.ecommerce.payment.entity.Payment;

@Service
public class PaymentMapper {
    public Payment toPayment(PaymentRequest request) {
        return Payment.builder()
                .id(null)
                .orderId(request.getId())
                .paymentMethod(request.getPaymentMethod())
                .totalAmount(request.getTotalAmount())
                .build();
    }
}
