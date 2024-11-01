package tn.seif.ecommerce.product.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.seif.ecommerce.product.dto.ProductPurchaseRequest;
import tn.seif.ecommerce.product.dto.ProductPurchaseResponse;
import tn.seif.ecommerce.product.dto.ProductRequest;
import tn.seif.ecommerce.product.dto.ProductResponse;
import tn.seif.ecommerce.product.service.IProductService;


import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/product")
public class ProductController {

    private final IProductService service;

    @PostMapping
    public ResponseEntity<Long> createProduct(@RequestBody @Valid ProductRequest request) {

        return ResponseEntity.ok().body(service.addProduct(request));
    }

    //-----
    @PutMapping
    public ResponseEntity<Void> updateProduct(@RequestBody @Valid ProductRequest request) {
        service.updateProduct(request);
        return ResponseEntity.ok().build();
    }

    //----
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.getProduct(id));
    }

    //-----
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok().body(service.getAllProducts());
    }

    //----
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {

        service.deleteProduct(id);
        return ResponseEntity.ok().build();
    }


}
