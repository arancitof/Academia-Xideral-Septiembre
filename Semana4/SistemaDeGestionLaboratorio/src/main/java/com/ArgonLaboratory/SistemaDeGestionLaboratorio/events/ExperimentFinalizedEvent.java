package com.ArgonLaboratory.SistemaDeGestionLaboratorio.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.modulith.events.Externalized;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Externalized("sistemagestionlaboratorio.experiment.finalized::#{#this.folio}")
public class ExperimentFinalizedEvent {

    private Long experimentId;
    private String folio;
    private String experimentName;
    private String finalStatus;
    private String InvestigatorLicenseNumber;
    private LocalDateTime finalizedAt;
}
