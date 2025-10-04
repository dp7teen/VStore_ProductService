package com.dp.vstore_productservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity (name = "products")
@Getter @Setter
public class Product extends BaseModel {
    private String productName;
    private String productDescription;
    private Double productPrice;
    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Category> category;
    private Integer stock;
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<Rating> ratings;
    private Double averageRating;
}
