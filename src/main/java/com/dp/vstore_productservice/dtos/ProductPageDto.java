package com.dp.vstore_productservice.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter @Setter
public class ProductPageDto {
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean lastPage;
    private List<ProductDto> content;


    public static ProductPageDto from(Page<ProductDto> page) {
        ProductPageDto dto = new ProductPageDto();
        dto.content = page.getContent();
        dto.pageNumber = page.getNumber();
        dto.pageSize = page.getSize();
        dto.totalElements = page.getTotalElements();
        dto.totalPages = page.getTotalPages();
        dto.lastPage = page.isLast();
        return dto;
    }
}
