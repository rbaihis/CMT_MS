package tn.seif.ecommerce.category.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.seif.ecommerce.category.dto.CategoryRequest;
import tn.seif.ecommerce.category.dto.CategoryResponse;
import tn.seif.ecommerce.category.entity.Category;
import tn.seif.ecommerce.product.util.ProductMapper;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryMapper {

    private final ProductMapper prodMapper;
    public Category toCategory(CategoryRequest request) {
        return  Category.builder()
                .id(request.getId())
                .name(request.getName())
                .build();
    }
    public CategoryResponse toCategoryResponse(Category category) {
         CategoryResponse response =CategoryResponse.builder()
                 .id(category.getId())
                .name(category.getName())
                .build();
         if(response.getProducts()==null || response.getProducts().isEmpty())
             return response;

         response.setProducts(category.getProducts().stream().map(prodMapper::toProductResponse).collect(Collectors.toList()));
         return response;
    }


}
