package com.dp.vstore_productservice.services.productUpdater;

import com.dp.vstore_productservice.dtos.UpdateProductDto;
import com.dp.vstore_productservice.models.Product;
import org.springframework.stereotype.Component;

@Component
public interface ProductUpdater {
    void update(Product product, UpdateProductDto dto);
}
