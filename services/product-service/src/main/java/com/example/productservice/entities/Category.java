package com.example.productservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String description;

    // "category" should be the exactly same name attribute in the Product class
    // REMOVE means when I remove a category , I want to remove all the products that are related to this category
    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
    private List<Product> products;
}