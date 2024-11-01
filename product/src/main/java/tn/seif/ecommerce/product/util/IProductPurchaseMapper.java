package tn.seif.ecommerce.product.util;

import org.springframework.stereotype.Service;
import tn.seif.ecommerce.product.entity.Product;
import tn.seif.ecommerce.product.dto.ProductPurchaseResponse;

import java.math.BigDecimal;

@Service
public class IProductPurchaseMapper implements ProductPurchaseMapper {

    @Override
    public ProductPurchaseResponse toProductPurchaseResponse(Product product, Long quantity){

        BigDecimal totalPrice = new BigDecimal(quantity).multiply(product.getPrice());
        return ProductPurchaseResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .quantity(quantity)
                .price(product.getPrice())
                .totalPrice(totalPrice)
                .build();
    }


//    @Override
//    public ProductPurchaseResponse toProduct(ProductPurchaseRequest request){
//
//    }
}
