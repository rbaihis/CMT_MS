package tn.seif.ecommerce.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.seif.ecommerce.product.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findAllByIdInOrderById(List<Long> ids);
}
