package tn.seif.ecommerce.order.dto;

import lombok.*;

import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class PurchaseResponse {

    private Long id;
    private String description;
    private String  name;
    private BigDecimal price;
    private Long quantity;
    private BigDecimal totalPrice;
}
