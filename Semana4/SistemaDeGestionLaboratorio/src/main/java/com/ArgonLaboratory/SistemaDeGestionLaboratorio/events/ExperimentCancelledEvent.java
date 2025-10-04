package com.ArgonLaboratory.SistemaDeGestionLaboratorio.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.modulith.events.Externalized;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Externalized("sistemagestionlaboratorio.experiment.cancelled::#{#this.folio}")
public class ExperimentCancelledEvent {

    private Long experimentId;
    private String folio;
    private String experimentName;
    private String finalStatus;
    private String InvestigatorLicenseNumber;
    private LocalDateTime cancelledAt;

}
