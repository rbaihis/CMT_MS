package tn.seif.ecommerce.product.service;

import jakarta.transaction.Transactional;
import tn.seif.ecommerce.product.dto.ProductPurchaseRequest;
import tn.seif.ecommerce.product.dto.ProductPurchaseResponse;
import tn.seif.ecommerce.product.dto.ProductRequest;
import tn.seif.ecommerce.product.dto.ProductResponse;
import tn.seif.ecommerce.product.entity.Product;

import java.util.List;

public interface IProductService {

    Long addProduct(ProductRequest request);

    void updateProduct(ProductRequest request);

    ProductResponse getProduct(Long id);

    List<ProductResponse> getAllProducts();

    void deleteProduct(Long id);

    List<Product> getProductsOrderAscByIds( List<Long> ids);

    void saveUpdateAllProducts(List<Product> products);


}
