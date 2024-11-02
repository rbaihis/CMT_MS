package tn.seif.ecommerce.product.util;

import org.springframework.stereotype.Service;
import tn.seif.ecommerce.category.entity.Category;
import tn.seif.ecommerce.product.entity.Product;
import tn.seif.ecommerce.product.dto.ProductRequest;
import tn.seif.ecommerce.product.dto.ProductResponse;

@Service
public class IProductMapper implements ProductMapper {
    @Override
    public Product toProduct(ProductRequest request) {
        return Product.builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .available_quantity(request.getAvailable_quantity())
                .category(
                        Category.builder().id(request.getCategory()).build()
                )
                .build();
    }

    @Override
    public ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .available_quantity(product.getAvailable_quantity())
                .categoryId(product.getCategory().getId())
                .categoryName(product.getCategory().getName())
                .build();
    }
}
