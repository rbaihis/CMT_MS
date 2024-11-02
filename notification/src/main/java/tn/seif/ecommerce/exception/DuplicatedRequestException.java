package tn.seif.ecommerce.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DuplicatedRequestException extends RuntimeException {
    private final String msg ;

}
