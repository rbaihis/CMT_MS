package tn.seif.ecommerce.product.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.stereotype.Service;
import tn.seif.ecommerce.context.threadlocal.IdempotencyKeyContextManager;
import tn.seif.ecommerce.exception.*;
import tn.seif.ecommerce.product.entity.Product;
import tn.seif.ecommerce.product.dto.ProductPurchaseRequest;
import tn.seif.ecommerce.product.dto.ProductPurchaseResponse;
import tn.seif.ecommerce.product.dto.ProductRequest;
import tn.seif.ecommerce.product.dto.ProductResponse;
import tn.seif.ecommerce.product.repository.ProductRepository;
import tn.seif.ecommerce.product.util.ProductMapper;
import tn.seif.ecommerce.product.util.ProductPurchaseMapper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final IIdempotencyService idempotencyService;
    private final ProductMapper mapperProduct;
    private final ProductPurchaseMapper mapperPurchaseProduct;
    private final ProductRepository repo;



    @Override
    public Long addProduct(ProductRequest request) {
        return repo.save(mapperProduct.toProduct(request)).getId();
    }

    @Override
    public void updateProduct(ProductRequest request) {
        //---verify using @Version for Conflict because of multithreading
        try {
            Product newProd = mapperProduct.toProduct(request);
            newProd.setVersion( findById(request.getId()).getVersion() );
            repo.save(newProd);
        } catch (OptimisticEntityLockException e) {
            throw new ConflictException("ProductService", "updateProduct", "Conflict on write detected, please retry again failed request", e);
        }
    }

    //-------
    private Product findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new ElementNotFoundException(
                String.format("can not update Customer:: No customer found with id %s ", id)
        ));
    }

    @Override
    public ProductResponse getProduct(Long id) {
        return mapperProduct.toProductResponse(
                findById(id)
        );
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return repo.findAll().stream()
                .map(mapperProduct::toProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProduct(Long id) {
        repo.delete(findById(id));

    }

    @Override
    public List<Product> getProductsOrderAscByIds(List<Long> ids) {
        return repo.findAllByIdInOrderById(ids);
    }

    @Override
    public void saveUpdateAllProducts(List<Product> products) {
        repo.saveAll(products);
    }

    //----------------------------------------------------------------------------------------
    private List<Long> getIdsFromPurchaseRequest(List<ProductPurchaseRequest> request) {
        return request.stream()
                .map(ProductPurchaseRequest::getId)
                .toList();
    }


}
