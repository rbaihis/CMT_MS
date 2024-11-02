package tn.seif.ecommerce.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.seif.ecommerce.order.entity.Order;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order,Long> {
    List<Order> findAllByCustomerIdOrderByCreatedDate(String customerId);

    Boolean existsByReference(String reference);
}
