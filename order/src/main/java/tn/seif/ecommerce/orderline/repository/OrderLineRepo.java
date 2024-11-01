package tn.seif.ecommerce.orderline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.seif.ecommerce.orderline.entity.OrderLine;

import java.util.List;

public interface OrderLineRepo extends JpaRepository<OrderLine,Long> {
    List<OrderLine> findByOrderId(Long orderId);
}
