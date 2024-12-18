package com.example.productservice.services;

import com.example.productservice.dto.ProductPurchaseRequest;
import com.example.productservice.dto.ProductPurchaseResponse;
import com.example.productservice.dto.ProductRequest;
import com.example.productservice.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    public List<ProductResponse> getAll();

    public ProductResponse getById(Integer Id);

    public ProductResponse save(ProductRequest productRequest);

    public void delete(Integer Id);

    public ProductResponse update(Integer productId , ProductRequest productRequest);

    public List<ProductPurchaseResponse> purchaseProducts(
            List<ProductPurchaseRequest> request
    );

}
