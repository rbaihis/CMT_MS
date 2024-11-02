package tn.seif.ecommerce.notification.dto;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Product {
    private Long id;
    private String name;
    private Long quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;

}
