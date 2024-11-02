package tn.seif.ecommerce.category.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.seif.ecommerce.category.dto.CategoryRequest;
import tn.seif.ecommerce.category.dto.CategoryResponse;
import tn.seif.ecommerce.category.service.ICategoryService;

import java.util.List;

@RestController
@RequestMapping("/product/category")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CategoryController {

    private final ICategoryService service;

    @PostMapping
    public ResponseEntity<Long> createCategory(@RequestBody @Valid CategoryRequest request) {

        return ResponseEntity.ok().body(service.addCategory(request));
    }

    //-----
    @PutMapping
    public ResponseEntity<Void> updateCategory(@RequestBody @Valid CategoryRequest request) {
        service.updateCategory(request);
        return ResponseEntity.ok().build();
    }

    //----
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.getCategory(id));
    }

    //-----
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategorys() {
        return ResponseEntity.ok().body(service.getAllCategories());
    }

    //----
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        service.deleteCategory(id);
        return ResponseEntity.ok().build();
    }

}