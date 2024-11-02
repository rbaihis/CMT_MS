package tn.seif.ecommerce.email;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
public enum EmailTemplates {
    ORDER_CONFIRMATION("order-confirmation.html","Order successfully processed"),
    PAYMENT_CONFIRMATION("payment-confirmation.html","Payment successfully processed")
    ;

    private final String template;
    private final String subject;

    EmailTemplates(String template, String subject){
        this.template= template;
        this.subject=subject;
    }
}
