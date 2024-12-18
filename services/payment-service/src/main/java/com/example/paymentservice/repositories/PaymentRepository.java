package com.example.paymentservice.repositories;

import com.example.paymentservice.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}