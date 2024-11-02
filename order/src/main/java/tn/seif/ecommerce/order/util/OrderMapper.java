package tn.seif.ecommerce.order.util;

import org.springframework.stereotype.Service;
import tn.seif.ecommerce.order.dto.PurchaseResponse;
import tn.seif.ecommerce.order.entity.Order;
import tn.seif.ecommerce.order.dto.OrderRequest;
import tn.seif.ecommerce.order.dto.OrderResponse;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderMapper {
    public Order toOrder(OrderRequest request , List<PurchaseResponse> productsPurchased) {
        Order order= Order.builder()
                .customerId(request.getCustomerId())
                .reference(request.getReference())
                .paymentMethod(request.getPaymentMethod())
                .totalAmount(new BigDecimal(0))
                .build();

        for (PurchaseResponse purchase : productsPurchased) {
            BigDecimal amount = purchase.getPrice().multiply(new BigDecimal( purchase.getQuantity()));
            order.setTotalAmount(order.getTotalAmount().add(purchase.getTotalPrice()));
        }

        return order;
    }

    public OrderResponse toOrderResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .reference(order.getReference())
                .totalAmount(order.getTotalAmount())
                .paymentMethod(order.getPaymentMethod())
                .createdDate(order.getCreatedDate())
                .customerId(order.getCustomerId())
                .build();
    }
}
