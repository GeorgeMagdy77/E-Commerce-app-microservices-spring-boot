package com.example.orderservice.services;

import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.dto.OrderResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public interface OrderService {

    public List<OrderResponse> getAll();

    public OrderResponse getById(Integer Id);

    public Integer save(OrderRequest orderRequest) throws JsonProcessingException;


}
