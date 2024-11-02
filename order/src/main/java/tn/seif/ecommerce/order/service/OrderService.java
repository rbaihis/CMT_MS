package tn.seif.ecommerce.order.service;

import org.springframework.stereotype.Service;
import tn.seif.ecommerce.order.dto.OrderRequest;
import tn.seif.ecommerce.order.dto.OrderResponse;

import java.util.List;


public interface OrderService {
    Long createOrder(OrderRequest request);

    List<OrderResponse> getAllOrder();

    OrderResponse findById(Long id);

    List<OrderResponse>findCustomerOrders(String idCustomer);
}
