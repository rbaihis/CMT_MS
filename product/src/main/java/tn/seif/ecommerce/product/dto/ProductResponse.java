package tn.seif.ecommerce.product.dto;

import lombok.*;

import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class ProductResponse {

    private Long id;
    private Long available_quantity;
    private String description;
    private String  name;
    private BigDecimal price;

    private Long categoryId;
    private String categoryName;



}
