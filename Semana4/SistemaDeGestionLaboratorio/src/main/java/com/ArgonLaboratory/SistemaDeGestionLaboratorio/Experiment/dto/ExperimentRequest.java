package com.ArgonLaboratory.SistemaDeGestionLaboratorio.Experiment.dto;

import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Experiment.model.Experiment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExperimentRequest {

    @NotBlank(message = "Es requerido el nombre del experimento")
    private String name;

    @NotBlank(message = "La descripción del experimento es necesaria")
    @Size(max = 500, message = "La descripción no puede exceder los 500 caracteres")
    private String description;


    @NotNull(message = "El status del experimento es necesario")
    private Experiment.ExperimentStatus status;


    @NotNull(message = "El nivel de riesgo del experimento es necesario")
    private Experiment.levelRisk risk;

    @NotNull(message = "La cedula del investigador es necesaria")
    private String investigatorLicenseNumber;
}
