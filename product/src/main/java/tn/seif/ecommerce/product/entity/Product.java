package tn.seif.ecommerce.product.entity;

import jakarta.persistence.*;
import lombok.*;
import tn.seif.ecommerce.category.entity.Category;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class Product {
    @Id
    @GeneratedValue
    private Long id;
    private Long available_quantity;
    private String description;
    private String  name;
    private BigDecimal price;

    @ManyToOne(optional = false) // Ensures that category is not null
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Category category;

    @Version
    private Long version;


}
