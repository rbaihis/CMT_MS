package tn.seif.ecommerce.order.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class PurchaseRequest {
    @NotNull(message = "Product is Mandatory")
    private Long id;
    @NotNull(message = "quantity is required ")
    private Long quantity;
    @NotNull(message = "price is required ")
    private BigDecimal price;
}
