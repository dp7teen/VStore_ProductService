package com.dp.vstore_productservice.strategies.productSearchingStrategy;

import com.dp.vstore_productservice.dtos.ProductDto;
import com.dp.vstore_productservice.models.Category;
import com.dp.vstore_productservice.repositories.CategoryRepository;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SearchByCategory implements SearchingStrategy {
    private final CategoryRepository categoryRepository;

    public SearchByCategory(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Page<ProductDto> search(String name, List<Sort.Order> orders, int page, int size) {
        List<Category> categories = categoryRepository.findAllByCategoryNameContainsIgnoreCaseAndDeletedFalse(name);
        if (categories.isEmpty()) {
            return null;
        }
        List<ProductDto> productDtos = new ArrayList<>();
        for (Category category : categories) {
            List<ProductDto> dtos = category.getProducts().stream().map(ProductDto::from).toList();
            productDtos.addAll(dtos);
        }
        return new PageImpl<>(productDtos, PageRequest.of(page, size), productDtos.size());
    }
}
