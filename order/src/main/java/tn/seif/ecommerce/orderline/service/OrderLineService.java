package tn.seif.ecommerce.orderline.service;

import tn.seif.ecommerce.orderline.dto.OrderLineRequest;
import tn.seif.ecommerce.orderline.dto.OrderLineResponse;

import java.util.List;

public interface OrderLineService {
    void saveOrderLine(OrderLineRequest orderLineRequest);

    List<OrderLineResponse> findAll();

    List<OrderLineResponse> findOrderIsOrderLines(Long order_id);
}
