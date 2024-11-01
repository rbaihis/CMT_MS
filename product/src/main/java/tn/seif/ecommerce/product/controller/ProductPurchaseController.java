package tn.seif.ecommerce.product.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.seif.ecommerce.product.dto.ProductPurchaseRequest;
import tn.seif.ecommerce.product.dto.ProductPurchaseResponse;
import tn.seif.ecommerce.product.dto.ProductRequest;
import tn.seif.ecommerce.product.dto.ProductResponse;
import tn.seif.ecommerce.product.service.IProductPurchaseService;
import tn.seif.ecommerce.product.service.IProductService;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProductPurchaseController {

    private final IProductPurchaseService service;

    @PostMapping("/purchase") ResponseEntity<List<ProductPurchaseResponse>> purchaseProducts(
            @RequestBody @Valid List<ProductPurchaseRequest> request
    ){

        return ResponseEntity.ok().body(service.purchaseProducts(request));
    }

    @PutMapping("/cancel-purchase") ResponseEntity<Void> cancelPurchaseProducts(List<ProductPurchaseRequest> request){
        service.cancelPurchaseProducts(request);
        return ResponseEntity.ok().build();
    }

}
