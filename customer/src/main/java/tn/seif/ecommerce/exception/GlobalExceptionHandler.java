package tn.seif.ecommerce.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> handle(CustomerNotFoundException exp){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exp.getMsg());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorResponse> handle(MethodArgumentNotValidException exp){

        Map<String,String> errors = new HashMap<>();
        exp.getBindingResult().getAllErrors()
                .forEach( error->{
                    String fieldName= ((FieldError)error).getField();
                    String errorMsg= error.getDefaultMessage();
                    errors.put(fieldName,errorMsg);
                });

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                CustomErrorResponse.builder().errors(errors).build()
        );
    }
}
