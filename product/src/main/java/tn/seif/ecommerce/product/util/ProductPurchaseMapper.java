package tn.seif.ecommerce.product.util;

import tn.seif.ecommerce.product.entity.Product;
import tn.seif.ecommerce.product.dto.ProductPurchaseResponse;

public interface ProductPurchaseMapper {
    ProductPurchaseResponse toProductPurchaseResponse(Product product,Long quantity);
}
