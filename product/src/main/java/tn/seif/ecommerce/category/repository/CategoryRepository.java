package tn.seif.ecommerce.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.seif.ecommerce.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
