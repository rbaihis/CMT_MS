package tn.seif.ecommerce.category.service;

import tn.seif.ecommerce.category.dto.CategoryRequest;
import tn.seif.ecommerce.category.dto.CategoryResponse;

import java.util.List;

public interface ICategoryService {

    Long addCategory(CategoryRequest request);

    void updateCategory(CategoryRequest request);

    CategoryResponse getCategory(Long id);

    CategoryResponse getEagerCategory(Long id);

    List<CategoryResponse> getAllCategories();

    void deleteCategory(Long id);
}
