package com.ArgonLaboratory.SistemaDeGestionLaboratorio.Notifications.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.security.PublicKey;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "notifications")
public class Notification {

    @Id
    private String id;
    private String message;
    private String sender;
    private String type; // e.g., "Experiment Update", "New Investigator"
    private String status;
    private String relatedEntityType; // e.g., "Experiment", "Investigator"
    private LocalDateTime createdAt;

    public enum sender{
        email,
        phone
    }
    public enum type {
        EXPERIMENT_UPDATE,
        NEW_INVESTIGATOR,
        NEW_EXPERIMENT
    }

    public enum relatedEntityType {
        EXPERIMENT,
        INVESTIGATOR
    }

    public enum status {
        PENDING,
        Progress,
        FAILED,
        Canceled
    }

    public Notification(String message, String sender, String type, String relatedEntityType, String status){
        this.message = message;
        this.sender = sender;
        this.type = type;
        this.relatedEntityType = relatedEntityType;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }


}
