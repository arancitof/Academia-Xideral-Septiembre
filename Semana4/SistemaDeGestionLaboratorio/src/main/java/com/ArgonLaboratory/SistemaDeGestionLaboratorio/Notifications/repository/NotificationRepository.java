package com.ArgonLaboratory.SistemaDeGestionLaboratorio.Notifications.repository;

import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Notifications.model.Notification;
import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Notifications.model.NotificationStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotificationRepository extends MongoRepository<Notification, String> {

    List<Notification> findById (Long id);

    List<Notification> findByStatus (NotificationStatus status);
}
