package com.ArgonLaboratory.SistemaDeGestionLaboratorio.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.modulith.events.Externalized;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Externalized("sistemagestionlaboratorio.experiment.high-risk::#{#this.folio}")
public class ExperimentHighRiskEvent {

    private Long experimentId;
    private String folio;
    private String experimentName;
    private String riskLevel;
    private String InvestigatorLicenseNumber;

    //La fecha en que fue declaro con nivel de riesgo ALTO (High)
    private LocalDateTime riskAssignedAt;
}
