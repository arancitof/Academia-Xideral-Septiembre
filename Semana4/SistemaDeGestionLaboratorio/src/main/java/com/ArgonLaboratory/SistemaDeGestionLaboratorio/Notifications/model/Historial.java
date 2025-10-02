package com.ArgonLaboratory.SistemaDeGestionLaboratorio.Notifications.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDateTime;

@Document(collection = "experiment_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Historial {


    @Id
    private String id;

    private Long experimentId;

    private String investigatorLicenseNumber;

    private String action;

    private String details;
    // 4. @CreatedDate en lugar de @CreationTimestamp
    //    Esta es la anotación de Spring Data para que MongoDB
    //    ponga la fecha de creación automáticamente.
    @CreatedDate
    private LocalDateTime eventTimestamp;
}
