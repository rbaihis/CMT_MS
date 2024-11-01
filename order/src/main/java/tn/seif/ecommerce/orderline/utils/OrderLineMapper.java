package tn.seif.ecommerce.orderline.utils;

import org.springframework.stereotype.Service;
import tn.seif.ecommerce.order.entity.Order;
import tn.seif.ecommerce.orderline.entity.OrderLine;
import tn.seif.ecommerce.orderline.dto.OrderLineRequest;
import tn.seif.ecommerce.orderline.dto.OrderLineResponse;

import java.math.BigDecimal;

@Service
public class OrderLineMapper {
    public OrderLine toOrderLine(OrderLineRequest request) {
        return OrderLine.builder()
                .id(request.getId())
                .order(Order.builder().id(request.getOrderId()).build())
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .unitPrice( request.getUnitPrice() )
                .build();
    }

    public OrderLineResponse toOrderLineResponse(OrderLine orderLine) {
        return OrderLineResponse.builder()
                .productId(orderLine.getProductId())
                .quantity(orderLine.getQuantity())
                .unitPrice(orderLine.getUnitPrice())
                .totalPrice(orderLine.getUnitPrice().multiply(new BigDecimal(orderLine.getQuantity())))
                .build();
    }
}
