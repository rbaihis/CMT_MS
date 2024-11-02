package tn.seif.ecommerce.exception;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tn.seif.ecommerce.context.threadlocal.IdempotencyKeyContextManager;
import tn.seif.ecommerce.exception.dto.ProductErrorResponse;
import tn.seif.ecommerce.product.service.IIdempotencyService;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    private final IIdempotencyService idempotencyService;

    @ExceptionHandler(ElementNotFoundException.class)
    public ResponseEntity<String> handle(ElementNotFoundException exp){
        log.warn(exp.getMsg());
        idempotencyService.deleteThisIdempotencyForAllPrefix(IdempotencyKeyContextManager.getIdempotencyKey() );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exp.getMsg());
    }

    @ExceptionHandler(ProductPurchaseException.class)
    public ResponseEntity<String> handle(ProductPurchaseException exp){
        log.warn(exp.getMsg());
        idempotencyService.deleteThisIdempotencyForAllPrefix(IdempotencyKeyContextManager.getIdempotencyKey() );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exp.getMsg());
    }

    @ExceptionHandler(CategoryCascadeException.class)
    public ResponseEntity<String> handle(CategoryCascadeException exp){
        log.warn(exp.getMsg());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exp.getMsg());
    }

    @ExceptionHandler(CustomServerErrorException.class)
    public ResponseEntity<String> handle(CustomServerErrorException exp){
        log.error(exp.getMsg());

        try { // used to avoid recursive call if reason is connection to where idempotency key stored
            idempotencyService.deleteThisIdempotencyForAllPrefix(IdempotencyKeyContextManager.getIdempotencyKey() );
        }catch (RedisSystemException ex){
            log.error("Redis error persist with cause :{}" , ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exp.getMessage());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ProductErrorResponse> handle(MethodArgumentNotValidException exp){
    public ResponseEntity<String> handle(MethodArgumentNotValidException exp){
        log.warn(exp.getAllErrors().toString());
        // allow retry since rollback occurred(actually this failed validation so no idemKey where added)
        idempotencyService.deleteThisIdempotencyForAllPrefix(IdempotencyKeyContextManager.getIdempotencyKey() );

        Map<String,String> errors = new HashMap<>();
        exp.getBindingResult().getAllErrors()
                .forEach( error->{
                    String fieldName= ((FieldError)error).getField();
                    String errorMsg= error.getDefaultMessage();
                    errors.put(fieldName,errorMsg);
                });

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ProductErrorResponse.builder().errors(errors).build().getErrors().toString()
        );
    }


    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<String> handle(ConflictException exp){
        log.warn(exp.getMessage());
        // allow retry since rollback occurred
        idempotencyService.deleteThisIdempotencyForAllPrefix(IdempotencyKeyContextManager.getIdempotencyKey() );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                "please retry Error occured during operation :: contact customer service if this persistant with keyworld 'ConflictException' " );
    }


    @ExceptionHandler(DuplicatedRequestException.class)
    public ResponseEntity<String> handle(DuplicatedRequestException exp){
        log.warn(exp.getMsg());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exp.getMsg());
    }
}
