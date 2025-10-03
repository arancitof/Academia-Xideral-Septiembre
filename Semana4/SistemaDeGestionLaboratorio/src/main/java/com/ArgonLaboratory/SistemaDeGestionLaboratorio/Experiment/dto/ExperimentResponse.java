package com.ArgonLaboratory.SistemaDeGestionLaboratorio.Experiment.dto;

import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Experiment.model.Experiment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExperimentResponse {
    private Long id;
    private String name;
    private String folio;
    private String description;
    private Experiment.ExperimentStatus status;
    private Experiment.levelRisk risk;
    private String investigatorLicenseNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ExperimentResponse fromEntity(Experiment experiment) {
        return new ExperimentResponse(
                experiment.getId(),
                experiment.getName(),
                experiment.getFolio(),
                experiment.getDescription(),
                experiment.getStatus(),
                experiment.getRisk(),
                experiment.getInvestigator().getLicenseNumber(),
                experiment.getCreatedAt(),
                experiment.getUpdatedAt()
        );
    }

}
