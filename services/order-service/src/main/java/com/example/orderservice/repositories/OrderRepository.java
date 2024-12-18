package com.example.orderservice.repositories;

import com.example.orderservice.entitites.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}