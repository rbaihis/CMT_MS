package tn.seif.ecommerce.order.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.seif.ecommerce.order.dto.OrderRequest;
import tn.seif.ecommerce.order.dto.OrderResponse;
import tn.seif.ecommerce.order.service.OrderService;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;

    @PostMapping()
    public ResponseEntity<Long> createOrder(@RequestBody @Valid OrderRequest request){

        return ResponseEntity.accepted().body(service.createOrder(request));
    }

    @GetMapping()
    public ResponseEntity<List<OrderResponse>>findAll(){

        return ResponseEntity.ok().body(
                service.getAllOrder()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse>findById(@PathVariable Long id){

        return ResponseEntity.ok().body(
                service.findById(id)
        );
    }

    @GetMapping("/customer/{id_customer}")
    public ResponseEntity<List<OrderResponse>> findAllCustomerOrders(@PathVariable String id_customer){

        return ResponseEntity.ok().body(
                service.findCustomerOrders(id_customer)
        );
    }
}
