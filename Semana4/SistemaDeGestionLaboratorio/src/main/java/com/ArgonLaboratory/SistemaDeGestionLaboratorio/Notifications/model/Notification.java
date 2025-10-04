package com.ArgonLaboratory.SistemaDeGestionLaboratorio.Notifications.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "notifications")
public class Notification {

    @Id
    private String id;
    private String message;
    private NotificationChannel channel; // Renamed from sender
    private NotificationType type;
    private NotificationStatus status;
    private String relatedEntityType;
    private String relatedEntityId; // Added field
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
