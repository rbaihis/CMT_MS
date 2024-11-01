package tn.seif.ecommerce.category.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import tn.seif.ecommerce.product.entity.Product;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class CategoryRequest {

    private Long id;
    @NotNull(message = "catalog id mandatory")
    private String name;
    private List<Product> products;
}
