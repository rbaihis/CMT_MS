package tn.seif.ecommerce.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class ConflictException extends RuntimeException {

    public ConflictException(String serviceName, String methodName, String message, Throwable cause) {
        super(String.format("Service: %s, Method: %s, Error: %s", serviceName, methodName, message), cause);
    }


}
