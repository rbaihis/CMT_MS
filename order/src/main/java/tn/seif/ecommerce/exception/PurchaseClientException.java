package tn.seif.ecommerce.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PurchaseClientException extends  RuntimeException{
    private final String msg;

}
