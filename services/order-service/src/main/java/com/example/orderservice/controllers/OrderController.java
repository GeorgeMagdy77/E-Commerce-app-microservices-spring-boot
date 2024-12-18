package com.example.orderservice.controllers;


import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.services.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService OrderService;

    @PostMapping("/placeOrder")
    public ResponseEntity<Integer> placeOrder(@RequestBody @Valid OrderRequest orderRequest) throws JsonProcessingException {
        return new ResponseEntity<>(OrderService.save(orderRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {

        return new ResponseEntity<List<OrderResponse>>(OrderService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(OrderService.getById(id));
    }
}
