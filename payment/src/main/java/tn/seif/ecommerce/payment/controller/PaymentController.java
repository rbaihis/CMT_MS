package tn.seif.ecommerce.payment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.seif.ecommerce.payment.service.IPaymentService;
import tn.seif.ecommerce.payment.dto.PaymentRequest;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@CrossOrigin("*")

public class PaymentController {
    private final IPaymentService service;

    @PostMapping
    public ResponseEntity<Long> createPayment(@RequestBody PaymentRequest request){
        return ResponseEntity.ok().body(service.createPayment(request));
    }
}
