package com.ArgonLaboratory.SistemaDeGestionLaboratorio.Notifications.service;

import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Notifications.model.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationService {

    Notification createNotification(Notification notification);

    Optional<Notification> findNotificationById(String id);

    List<Notification> findAllNotifications();

    void deleteNotificationById(String id);

    List<Notification> findNotificationsByStatus(String status);

    List<Notification> findNotificationsByType(String type);

    List<Notification> findNotificationsByChannel(String channel);

    List<Notification> findNotificationsByRelatedEntityId(String relatedEntityId);



}
