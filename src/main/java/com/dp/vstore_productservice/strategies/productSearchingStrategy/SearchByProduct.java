package com.dp.vstore_productservice.strategies.productSearchingStrategy;

import com.dp.vstore_productservice.dtos.ProductDto;
import com.dp.vstore_productservice.models.Product;
import com.dp.vstore_productservice.repositories.ProductRepository;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component @NoArgsConstructor
public class SearchByProduct implements SearchingStrategy {
    private ProductRepository productRepository;

    public SearchByProduct(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<ProductDto> search(String name, List<Sort.Order> orders, int page, int size) {
        Optional<Product> optionalProduct = productRepository.findByProductName(name);
        if (optionalProduct.isEmpty()) {
            return null;
        }
        List<Product> products = productRepository.findAllByProductNameContainingIgnoreCaseAndDeletedFalse(
                name,
                PageRequest.of(page, size).withSort(Sort.by(orders)));
        List<ProductDto> productDtos = products.stream().map(ProductDto::from).toList();

        return new PageImpl<>(productDtos, PageRequest.of(page, size), products.size());
    }
}
