package tn.seif.ecommerce.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomServerErrorException extends RuntimeException {
    private final String msg ;

}
