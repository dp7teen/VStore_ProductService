package com.dp.vstore_productservice.services.productUpdater;

import com.dp.vstore_productservice.dtos.UpdateProductDto;
import com.dp.vstore_productservice.models.Product;
import org.springframework.stereotype.Component;

@Component
public class UpdateProductName implements ProductUpdater {
    public void update(Product product, UpdateProductDto dto) {
        if  (dto.getProductName() != null) {
            product.setProductName(dto.getProductName());
        }
    }
}
