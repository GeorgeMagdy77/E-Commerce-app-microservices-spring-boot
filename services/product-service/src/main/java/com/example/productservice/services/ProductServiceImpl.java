package com.example.productservice.services;

import com.example.productservice.Mapper.ProductMapper;
import com.example.productservice.dto.ProductPurchaseRequest;
import com.example.productservice.dto.ProductPurchaseResponse;
import com.example.productservice.dto.ProductRequest;
import com.example.productservice.dto.ProductResponse;
import com.example.productservice.entities.Product;
import com.example.productservice.exception.DuplicateResourceException;
import com.example.productservice.exception.ProductPurchaseException;
import com.example.productservice.exception.RequestValidationException;
import com.example.productservice.exception.ResourceNotFoundException;
import com.example.productservice.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final ProductMapper mapper;

    @Override
    public List<ProductResponse> getAll() {
        List<Product> products = productRepository.findAll();
        List<ProductResponse> list = products.stream().map(product -> mapper.toProductResponse(product))
                .collect(Collectors.toList());

        return list;
    }

    @Override
    public ProductResponse getById(Integer Id) {
        Product product = productRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException(
                "product with id [%s] not found".formatted(Id)
        ));

        ProductResponse productResponse = mapper.toProductResponse(product);
        return productResponse;
    }

    @Override
    public ProductResponse save(ProductRequest productRequest) {

        /// check if product ID exists ---> throw an exception
        Integer id = productRequest.id();
        if(productRepository.existsProductById(id)){
            throw new DuplicateResourceException("Id already taken");
        }

        Product product = mapper.toProduct(productRequest);
        product = productRepository.save(product);
        ProductResponse productResponse = mapper.toProductResponse(product);
        return productResponse;

    }


    @Override
    public void delete(Integer Id) {
        if (!productRepository.existsProductById(Id)) {
            throw new ResourceNotFoundException(
                    "customer with id [%s] not found".formatted(Id)
            );
        }
        productRepository.deleteById(Id);
    }

    @Override
    public ProductResponse update(Integer productId , ProductRequest updateRequest){
        ProductResponse productResponse = getById(productId);
        Product product = mapper.toProduct(productResponse);


        boolean isChanged = false;

        if (updateRequest.name() != null && !updateRequest.name().equals(product.getName())) {

            product.setName(updateRequest.name());
            isChanged = true;
        }


        if (updateRequest.availableQuantity() != 0 && updateRequest.availableQuantity()!=product.getAvailableQuantity()) {

            product.setAvailableQuantity(updateRequest.availableQuantity());
            isChanged = true;
        }

        if(updateRequest.description() != null && !updateRequest.description().equals(product.getDescription())){
            product.setDescription(updateRequest.description());
            isChanged = true;
        }

        if(updateRequest.price() != null && !updateRequest.price().equals(product.getPrice())){
            product.setPrice(updateRequest.price());
        }

        /// if data is the same as it is requested to be updated
        if (!isChanged) {
            throw new RequestValidationException("no data changes , as it remains the same");
        }

        product = productRepository.save(product);
        ProductResponse updatedProductResponse = mapper.toProductResponse(product);
        return updatedProductResponse;

    }


    @Transactional(rollbackFor = ProductPurchaseException.class)
    public List<ProductPurchaseResponse> purchaseProducts(
            List<ProductPurchaseRequest> request
    )
    {
        var productIds = request  // user wants to buy the products 1,2,3
                .stream()
                .map(ProductPurchaseRequest::productId)
                .toList();

        // return the products that is stored in the database
        var storedProducts = productRepository.findAllByIdInOrderById(productIds); // in the database we only have products with IDs 1,2

        // We need to check that all the products we want to purchase they already exists in the database
        if (productIds.size() != storedProducts.size()) {
            throw new ProductPurchaseException("One or more products does not exist");
        }

        // We extract the IDs of the stored products
        var sortedRequest = request
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();
        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();
        for (int i = 0; i < storedProducts.size(); i++) {
            var product = storedProducts.get(i);
            var productRequest = sortedRequest.get(i);
            if (product.getAvailableQuantity() < productRequest.quantity()) {
                throw new ProductPurchaseException("Insufficient stock quantity for product with ID:: " + productRequest.productId());
            }
            var newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            productRepository.save(product);
            purchasedProducts.add(mapper.toproductPurchaseResponse(product, productRequest.quantity()));
        }
        return purchasedProducts;
    }
}