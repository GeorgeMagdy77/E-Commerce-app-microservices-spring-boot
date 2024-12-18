package com.example.notificationservice.entities;

import com.example.notificationservice.kafka.order.OrderConfirmation;
import com.example.notificationservice.kafka.payment.PaymentConfirmation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(collection = "notifications")
public class Notification {

    @Id
    private String id;
    private NotificationType type;
    private LocalDateTime notificationDate;  // to keep trace of the notification data
    private OrderConfirmation orderConfirmation;
    private PaymentConfirmation paymentConfirmation;
}