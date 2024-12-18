package com.example.productservice.repositories;

import com.example.productservice.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    boolean existsProductById(Integer id);
    List<Product> findAllByIdInOrderById(List<Integer> ids);
}
