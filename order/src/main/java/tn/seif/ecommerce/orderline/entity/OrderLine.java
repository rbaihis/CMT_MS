package tn.seif.ecommerce.orderline.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.seif.ecommerce.order.entity.Order;

import java.math.BigDecimal;

@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
public class OrderLine {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    private Long productId;
    private Long quantity;
    private BigDecimal unitPrice;

}