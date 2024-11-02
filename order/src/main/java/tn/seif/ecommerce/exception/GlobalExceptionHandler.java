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

    @ExceptionHandler(IdempotencyException.class)
    public ResponseEntity<String> handle(IdempotencyException exp){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exp.getMsg());
    }


    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handle(BusinessException exp){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exp.getMsg());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProductErrorResponse> handle(MethodArgumentNotValidException exp){

        Map<String,String> errors = new HashMap<>();
        exp.getBindingResult().getAllErrors()
                .forEach( error->{
                    String fieldName= ((FieldError)error).getField();
                    String errorMsg= error.getDefaultMessage();
                    errors.put(fieldName,errorMsg);
                });

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ProductErrorResponse.builder().errors(errors).build()
        );
    }


}
