package com.example.orderservice.kafka;

import com.example.orderservice.dto.PurchaseResponse;
import com.example.orderservice.entitites.PaymentMethod;
import com.example.orderservice.external.response.CustomerResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation (
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products // list of the products that customer has purchased

) {
}
