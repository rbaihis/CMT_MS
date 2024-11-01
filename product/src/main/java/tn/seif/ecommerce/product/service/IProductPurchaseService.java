package tn.seif.ecommerce.product.service;

import jakarta.transaction.Transactional;
import tn.seif.ecommerce.product.dto.ProductPurchaseRequest;
import tn.seif.ecommerce.product.dto.ProductPurchaseResponse;
import tn.seif.ecommerce.product.dto.ProductRequest;
import tn.seif.ecommerce.product.dto.ProductResponse;

import java.util.List;

public interface IProductPurchaseService {


    @Transactional
    List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request);

    //
    @Transactional
    void cancelPurchaseProducts(List<ProductPurchaseRequest> request);
}
