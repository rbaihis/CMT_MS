package tn.seif.ecommerce.category.entity;

import jakarta.persistence.*;
import lombok.*;
import tn.seif.ecommerce.product.entity.Product;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class Category {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)

    private String name;
    @OneToMany(mappedBy = "category")
    @EqualsAndHashCode.Exclude
    private List<Product> products;
}
