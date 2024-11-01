package tn.seif.ecommerce.category.dto;

import lombok.*;
import tn.seif.ecommerce.product.dto.ProductResponse;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class CategoryResponse {

    private Long id;
    private String name;
    private List<ProductResponse> products;
}
