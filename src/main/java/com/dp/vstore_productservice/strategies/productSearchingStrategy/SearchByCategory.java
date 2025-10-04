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

import java.util.List;
import java.util.Optional;

@Component @NoArgsConstructor
public class SearchByCategory implements SearchingStrategy {
    private CategoryRepository categoryRepository;

    public SearchByCategory(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Page<ProductDto> search(String name, List<Sort.Order> orders, int page, int size) {
        Optional<Category> optionalCategory = categoryRepository.findByCategoryNameAndDeletedFalse(name);
        if (optionalCategory.isEmpty()) {
            return null;
        }
        Category category = optionalCategory.get();
        List<ProductDto> productDtos = category.getProducts().stream().map(ProductDto::from).toList();

        return new PageImpl<>(productDtos, PageRequest.of(page, size), productDtos.size());
    }
}
