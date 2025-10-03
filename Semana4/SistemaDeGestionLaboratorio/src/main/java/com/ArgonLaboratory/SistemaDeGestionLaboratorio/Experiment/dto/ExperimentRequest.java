package com.ArgonLaboratory.SistemaDeGestionLaboratorio.Experiment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExperimentRequest {

    @NotBlank(message = "Es requerido el nombre del experimento")
    private String name;

    @NotBlank(message = "El status del experimento es necesario")
    private String status;

    @NotNull(message = "El nivel de riesgo del experimento es necesario")
    private String risk;

    @NotNull(message = "La cedula del investigador es necesaria")
    private String investigatorLicenseNumber;
}
