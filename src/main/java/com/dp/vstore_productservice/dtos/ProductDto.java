package com.dp.vstore_productservice.dtos;

import com.dp.vstore_productservice.models.Category;
import com.dp.vstore_productservice.models.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ProductDto {
    private Long productId;
    private String productName;
    private String productDescription;
    private double productPrice;
    private List<String> category;
    private int stock;
    private Double rating = 0.0;

    public static ProductDto from(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setProductId(product.getId());
        productDto.setProductName(product.getProductName());
        productDto.setProductDescription(product.getProductDescription());
        productDto.setProductPrice(product.getProductPrice());

        productDto.setCategory(new ArrayList<>());
        productDto.getCategory().addAll(
                product.getCategory().stream()
                        .map(Category::getCategoryName)
                        .toList()
        );
        productDto.setStock(product.getStock());
        productDto.setRating(product.getAverageRating());

        return productDto;
    }
}
