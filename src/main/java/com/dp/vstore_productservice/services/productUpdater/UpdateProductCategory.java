package com.dp.vstore_productservice.services.productUpdater;

import com.dp.vstore_productservice.dtos.UpdateProductDto;
import com.dp.vstore_productservice.models.Category;
import com.dp.vstore_productservice.models.Product;

import com.dp.vstore_productservice.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UpdateProductCategory implements ProductUpdater {
    private final CategoryRepository categoryRepository;

    public UpdateProductCategory(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void update(Product product, UpdateProductDto dto) {
        if (dto.getCategory() != null && !dto.getCategory().isEmpty()) {
            product.getCategory().clear();
            for (String categoryName : dto.getCategory()) {
                Optional<Category> category = categoryRepository.findByCategoryNameAndDeletedFalse(categoryName);
                if (category.isPresent()) {
                    product.getCategory().add(category.get());
                }
                else {
                    Category newCategory = new Category();
                    newCategory.setCategoryName(categoryName);
                    newCategory.setProducts(new ArrayList<>());
                    categoryRepository.save(newCategory);
                    product.getCategory().add(newCategory);
                }
            }
        }
    }
}
