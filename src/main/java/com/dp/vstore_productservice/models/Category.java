package com.dp.vstore_productservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity(name = "categories")
@Getter @Setter
public class Category extends BaseModel {
    @Column(nullable = false, unique = true)
    private String categoryName;
    @ManyToMany(mappedBy = "category")
    private List<Product> products;
}
