package tn.seif.ecommerce.payment.util;

import org.springframework.stereotype.Service;
import tn.seif.ecommerce.payment.dto.PaymentConfirmation;
import tn.seif.ecommerce.payment.dto.PaymentRequest;

@Service
public class ConfirmationMapper {

    public PaymentConfirmation payRequestToPaymentConfirmation(PaymentRequest request){
        return PaymentConfirmation.builder()
                .orderReference(request.getOrderReference())
                .amount(request.getTotalAmount().longValue())
                .firstName(request.getCustomer().getFirstName())
                .lastName(request.getCustomer().getLastName())
                .email(request.getCustomer().getEmail())
                .build();
    }
}
