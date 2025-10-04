package com.dp.vstore_productservice.strategies.productSearchingStrategy;

import com.dp.vstore_productservice.dtos.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SearchingStrategy {
    Page<ProductDto> search(String name, List<Sort.Order> orders, int page, int size);
}
