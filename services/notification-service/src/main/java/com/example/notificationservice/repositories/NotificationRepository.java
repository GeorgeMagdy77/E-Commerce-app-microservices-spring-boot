package com.example.notificationservice.repositories;

import com.example.notificationservice.entities.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification, String> {
}
