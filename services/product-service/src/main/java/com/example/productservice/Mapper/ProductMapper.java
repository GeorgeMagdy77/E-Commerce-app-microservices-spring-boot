package com.example.productservice.Mapper;

import com.example.productservice.dto.ProductPurchaseResponse;
import com.example.productservice.dto.ProductRequest;
import com.example.productservice.dto.ProductResponse;
import com.example.productservice.entities.Category;
import com.example.productservice.entities.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {

    public Product toProduct(ProductRequest request) {
        return Product.builder()
                .id(request.id())
                .name(request.name())
                .description(request.description())
                .availableQuantity(request.availableQuantity())
                .price(request.price())
                .category(
                        Category.builder()
                                .id(request.categoryId())
                                .build()
                )
                .build();
    }


    public Product toProduct(ProductResponse response) {
        return Product.builder()
                .id(response.id())
                .name(response.name())
                .description(response.description())
                .availableQuantity(response.availableQuantity())
                .price(response.price())
                .category(
                        Category.builder()
                                .id(response.categoryId())
                                .build()
                )
                .build();
    }


    public ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getAvailableQuantity(),
                product.getPrice(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getCategory().getDescription()
        );
    }


    public ProductPurchaseResponse toproductPurchaseResponse(Product product, double quantity) {
        return new ProductPurchaseResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                quantity
        );
    }
}