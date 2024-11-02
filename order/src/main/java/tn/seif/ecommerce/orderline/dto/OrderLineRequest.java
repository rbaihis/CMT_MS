package tn.seif.ecommerce.orderline.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Builder
@Setter
@Getter
public class OrderLineRequest {
    private Long id;
    private Long orderId;
    private Long productId;
    private Long quantity;
    private BigDecimal unitPrice;

    public OrderLineRequest(Long id, Long orderId, Long productId, Long quantity, BigDecimal unitPrice) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
}
