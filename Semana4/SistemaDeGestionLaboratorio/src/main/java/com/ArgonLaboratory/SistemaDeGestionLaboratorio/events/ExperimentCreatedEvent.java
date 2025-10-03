package com.ArgonLaboratory.SistemaDeGestionLaboratorio.events;

import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Experiment.model.Experiment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationEvent;
import org.springframework.modulith.events.Externalized;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Externalized("sistemagestionlaboratorio.experiment.created::#{#this.folio}")
public class ExperimentCreatedEvent {

    private Long experimentId;
    private String folio;
    private String experimentName;
    private String riskLevel;
    private String InvestigatorLicenseNumber;
    private LocalDateTime createdAt;



}
