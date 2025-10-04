package com.dp.vstore_productservice.services.productUpdater;

import com.dp.vstore_productservice.dtos.UpdateProductDto;
import com.dp.vstore_productservice.models.Category;
import com.dp.vstore_productservice.models.Product;

import com.dp.vstore_productservice.repositories.CategoryRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UpdateProductCategory implements ProductUpdater {
    private CategoryRepository categoryRepository;

    public UpdateProductCategory () {}

    public UpdateProductCategory(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void update(Product product, UpdateProductDto dto) {
        if (dto.getCategory() != null && !dto.getCategory().isEmpty()) {
            product.getCategory().clear();
            for (String categoryName : dto.getCategory()) {
                Optional<Category>  category = categoryRepository.findByCategoryName(categoryName);
                if (category.isPresent()) {
                    product.getCategory().add(category.get());
                }
                else {
                    Category newCategory = new Category();
                    newCategory.setCategoryName(categoryName);
                    newCategory.setProducts(new ArrayList<>());
                    product.getCategory().add(newCategory);
                }
            }
        }
    }
}
