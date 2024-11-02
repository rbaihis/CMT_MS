package tn.seif.ecommerce.product.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.springframework.stereotype.Service;
import tn.seif.ecommerce.exception.ConflictException;
import tn.seif.ecommerce.exception.ProductPurchaseException;
import tn.seif.ecommerce.product.dto.ProductPurchaseRequest;
import tn.seif.ecommerce.product.dto.ProductPurchaseResponse;
import tn.seif.ecommerce.product.entity.IdempotencyPrefixType;
import tn.seif.ecommerce.product.entity.Product;
import tn.seif.ecommerce.product.util.ProductPurchaseMapper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class ProductPurchaseService implements IProductPurchaseService {
    private final IIdempotencyService idempotencyService;
    private final IProductService productService;
    private final ProductPurchaseMapper mapperPurchaseProduct;


    private List<Long> getIdsFromPurchaseRequest(List<ProductPurchaseRequest> request) {
        return request.stream()
                .map(ProductPurchaseRequest::getId)
                .toList();
    }

    @Override
    @Transactional
    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request) {

        idempotencyService.assertIdempotency(180, IdempotencyPrefixType.PURCHASE);

        List<Long> ids = getIdsFromPurchaseRequest(request);
        List<Product> productsFetched = productService.getProductsOrderAscByIds(ids);


        if (productsFetched.size() < request.size()) // validation wrong request (unlikely occurrence)
            throw new ProductPurchaseException(String.format("One or More Product To purchase does Not Exit:: either Product is Deleted or Bug In Code , ids of products are %s", ids.toString()));

        request.sort(Comparator.comparing(ProductPurchaseRequest::getId)); // for correct index looping
        List<ProductPurchaseResponse > response = new ArrayList<>();
        for (int i = 0; i < productsFetched.size(); i++) {
            Product prod = productsFetched.get(i);
            Long quantity = request.get(i).getQuantity();
            if ((prod.getAvailable_quantity() - quantity) < 0)
                throw new ProductPurchaseException(String.format("failed purchase:: quantity of product is %s < your order %s", prod.getAvailable_quantity(), quantity));

            prod.setAvailable_quantity(prod.getAvailable_quantity() - quantity);
            response.add(mapperPurchaseProduct.toProductPurchaseResponse(prod, quantity));
        }

        //---verify using @Version for Conflict because of multithreading
        try {
            log.info("updating product");
            productService.saveUpdateAllProducts(productsFetched);
            return response;
        } catch (OptimisticEntityLockException e) {
            throw new ConflictException("ProductService", "purchaseProduct", "Conflict on write detected, please retry again failed request", e);
        }
    }


    //
    @Override
    @Transactional
    public void cancelPurchaseProducts(List<ProductPurchaseRequest> request) {

        idempotencyService.assertIdempotency(180, IdempotencyPrefixType.PURCHASE_CANCEL);

        request.sort(Comparator.comparing(ProductPurchaseRequest::getId));
        List<Long> ids = request.stream()
                .map(ProductPurchaseRequest::getId)
                .toList();

        List<Product> fetchedProducts = productService.getProductsOrderAscByIds(ids);

        if (fetchedProducts.size() < request.size())
            throw new ProductPurchaseException(String.format("One or More Product does Not Exit(Product may be Deleted) :: RollBack method failed, to update quantities of products : %s ", ids.toString()));


        for (int i = 0; i < fetchedProducts.size(); i++) {
            Product prod = fetchedProducts.get(i);
            Long quantityToReverse = request.get(i).getQuantity();

            prod.setAvailable_quantity(prod.getAvailable_quantity() + quantityToReverse);
        }

        //---verify using @Version for Conflict because of multithreading by other request
        try {
            productService.saveUpdateAllProducts(fetchedProducts);
        } catch (OptimisticEntityLockException e) {
            throw new ConflictException("ProductService", "purchaseProduct", "Conflict on write detected, please retry again failed request", e);
        }
    }


}
