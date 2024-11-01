package tn.seif.ecommerce.orderline.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.seif.ecommerce.orderline.dto.OrderLineResponse;
import tn.seif.ecommerce.orderline.service.OrderLineService;

import java.util.List;

@RestController
@RequestMapping("/order-line")
@RequiredArgsConstructor
@CrossOrigin("*")
public class OrderLineController {
    private final OrderLineService service;

    @GetMapping()
    // fixme: replace with Pagination
    public ResponseEntity<List<OrderLineResponse>> getAllResponse(){
        return ResponseEntity.accepted().body(
                service.findAll()
        );
    }

    @GetMapping("/{order_id}")
    public ResponseEntity<List<OrderLineResponse>> getAllResponse(@PathVariable Long order_id){
        return ResponseEntity.accepted().body( service.findOrderIsOrderLines( order_id) );
    }
}
