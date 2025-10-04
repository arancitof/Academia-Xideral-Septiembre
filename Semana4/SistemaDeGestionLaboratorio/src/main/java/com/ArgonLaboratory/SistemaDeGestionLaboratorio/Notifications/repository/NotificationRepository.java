package com.ArgonLaboratory.SistemaDeGestionLaboratorio.Notifications.repository;

import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Notifications.model.Notification;
import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Notifications.model.NotificationStatus;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("!test")
public interface NotificationRepository extends MongoRepository<Notification, String> {

    List<Notification> findById (Long id);

    List<Notification> findByStatus (NotificationStatus status);

    List<Notification> findByType (String type);

    List<Notification> findByChannel (String channel);

    List<Notification> findByRelatedEntityId (String relatedEntityId);

}
