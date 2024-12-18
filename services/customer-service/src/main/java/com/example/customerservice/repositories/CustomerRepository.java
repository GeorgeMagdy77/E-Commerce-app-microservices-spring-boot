package com.example.customerservice.repositories;

import com.example.customerservice.entities.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CustomerRepository extends MongoRepository<Customer,String> {
    boolean existsCustomerByEmail(String email);
    boolean existsCustomerById(String id);
    Optional<Customer> findCustomerByEmail(String email);
}
