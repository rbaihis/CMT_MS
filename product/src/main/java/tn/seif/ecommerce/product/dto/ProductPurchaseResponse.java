package tn.seif.ecommerce.product.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class ProductPurchaseResponse {
    private Long id;
    private String name;
    private Long quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;

}
