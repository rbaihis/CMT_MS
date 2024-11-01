package tn.seif.ecommerce.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.seif.ecommerce.category.dto.CategoryRequest;
import tn.seif.ecommerce.category.dto.CategoryResponse;
import tn.seif.ecommerce.category.entity.Category;
import tn.seif.ecommerce.category.repository.CatalogRepo;
import tn.seif.ecommerce.category.util.CategoryMapper;
import tn.seif.ecommerce.exception.CategoryCascadeException;
import tn.seif.ecommerce.exception.ElementNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CatalogRepo repo;
    private final CategoryMapper mapper;
    @Override
    public Long addCategory(CategoryRequest request) {
        return repo.save(mapper.toCategory(request)).getId();
    }

    @Override
    public void updateCategory(CategoryRequest request) {
        if(request.getId()!=null && !repo.existsById(request.getId()))
            throw new ElementNotFoundException("category to update does not exist..");
        repo.save(mapper.toCategory( request));
    }

    @Override
    public CategoryResponse getCategory(Long id) {
        return repo.findById(id).map(mapper::toCategoryResponse).orElseThrow(
                ()-> new ElementNotFoundException("category find by id not found ....")
        );
    }

    @Override
    public CategoryResponse getEagerCategory(Long id) {
        Category category = repo.findById(id).orElseThrow(
                ()-> new ElementNotFoundException("category find by id not found ....")
        );
        //trigger eager fetch
        category.getProducts().size();

        return mapper.toCategoryResponse(category);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {

        return repo.findAll().stream().map(mapper::toCategoryResponse).collect(Collectors.toList());
    }

    @Override
    public void deleteCategory(Long id) {
         Category category = repo.findById(id).orElseThrow(
                 ()-> new ElementNotFoundException("category find by id not found ....")
         );

        if( category.getProducts().isEmpty()  )
            throw new CategoryCascadeException("category can not be deleted , products are attached to it ...");

        repo.delete(category);
    }
}
