package tn.seif.ecommerce.orderline.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.seif.ecommerce.orderline.utils.OrderLineMapper;
import tn.seif.ecommerce.orderline.dto.OrderLineRequest;
import tn.seif.ecommerce.orderline.dto.OrderLineResponse;
import tn.seif.ecommerce.orderline.repository.OrderLineRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderLineIMP implements OrderLineService {
    private final OrderLineRepo repo;
    private final OrderLineMapper mapper;


    @Override
    @Transactional
    public void saveOrderLine(OrderLineRequest request) {
        repo.save(mapper.toOrderLine(request));
    }

    @Override
    public List<OrderLineResponse> findAll() {
        return repo.findAll().stream()
                .map(mapper::toOrderLineResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderLineResponse> findOrderIsOrderLines(Long order_id) {
        return repo.findByOrderId(order_id).stream().map(mapper::toOrderLineResponse).collect(Collectors.toList());
    }
}
