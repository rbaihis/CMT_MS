package tn.seif.ecommerce.product.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class ProductRequest {

    private Long id;
    @NotNull(message = "quantity is required and should be positive")
    private Long available_quantity;
    @NotNull(message = "description is required")
    private String description;
    @NotNull(message = "name is required")
    private String  name;
    @NotNull(message = "price is required and should be positive")
    private BigDecimal price;
    @NotNull(message = "product category is required")
    private Long category;


}
