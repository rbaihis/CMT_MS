package tn.seif.ecommerce.exception;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
public class ElementNotFoundException extends RuntimeException {

    private final String msg;
}
