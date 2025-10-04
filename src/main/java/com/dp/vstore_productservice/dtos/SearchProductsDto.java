package com.dp.vstore_productservice.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter @Setter
public class SearchProductsDto {
    @NotBlank
    private String name;
    private List<String> sortBy;

    public SearchProductsDto() {
        sortBy = List.of("rating,desc");
    }
}
