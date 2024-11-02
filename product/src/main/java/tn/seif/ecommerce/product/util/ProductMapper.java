package tn.seif.ecommerce.product.util;

import tn.seif.ecommerce.product.entity.Product;
import tn.seif.ecommerce.product.dto.ProductRequest;
import tn.seif.ecommerce.product.dto.ProductResponse;

public interface ProductMapper {
    Product toProduct(ProductRequest request) ;
    ProductResponse toProductResponse(Product product);


}
