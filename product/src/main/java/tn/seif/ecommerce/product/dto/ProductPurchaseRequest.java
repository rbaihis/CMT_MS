package tn.seif.ecommerce.product.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductPurchaseRequest {

    @NotNull(message = "id is mandatory")
    private Long id;
    @NotNull(message = "quantity is mandatory & positive > 0")
    @Positive(message = "quantity is mandatory & positive > 0")
    private Long quantity;

}
