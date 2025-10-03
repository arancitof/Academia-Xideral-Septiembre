package com.ArgonLaboratory.SistemaDeGestionLaboratorio.Experiment.controller;

import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Experiment.dto.ExperimentRequest;
import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Experiment.dto.ExperimentResponse;
import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Experiment.model.Experiment;
import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Experiment.service.ExperimentService;
import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Investigator.model.Investigator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/experiments")
@RequiredArgsConstructor
@Tag(name = "Experimentos", description = "Experimentos API")
public class ExperimentController {

    //Recuerda que el Controller depende del Service, hay que inyectarlo
    private final ExperimentService experimentService;

    //Peticion para crear nuevo experimento
    @PostMapping
    @Operation(summary = "Crear experimento", description = "Crear un nuevo experimento")
    public ResponseEntity<ExperimentResponse> createExperiment(
            @Valid @RequestBody ExperimentRequest request) {
        Experiment newExperiment = new Experiment();
        newExperiment.setName(request.getName());
        newExperiment.setStatus(Experiment.ExperimentStatus.valueOf(request.getStatus()));
        newExperiment.setRisk(Experiment.levelRisk.valueOf(request.getRisk()));

        //// Creamos un objeto Investigator temporal solo con la licencia para pasarlo al servicio
        Investigator investigator = new Investigator();
        investigator.setLicenseNumber(request.getInvestigatorLicenseNumber());
        newExperiment.setInvestigator(investigator);

        Experiment createdExperiment = experimentService.createExperiment(newExperiment);
        return ResponseEntity.ok(ExperimentResponse.fromEntity(createdExperiment));
    }

    //Peticion para obtner un experimento por su ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener experimento por ID", description = "Obtener un experimento por su ID")
    public ResponseEntity<ExperimentResponse> getExperimentById(
            @PathVariable Long id) {
        return experimentService.getExperimentById(id)
                .map(experiment -> ResponseEntity.ok(ExperimentResponse.fromEntity(experiment))) // Si se encuentra, mapea a DTO y devuelve 200 OK
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/folio/{folio}")
    @Operation(summary = "Obtener experimento por su folio", description = "Obtener un experimento por su folio")
    public ResponseEntity<ExperimentResponse> getExperimentByFolio(
            @PathVariable String folio) {
        return experimentService.findByFolio(folio)
                .map(experiment -> ResponseEntity.ok(ExperimentResponse.fromEntity(experiment)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Obtener todos los experimentos", description = "Obtener todos los experimentos registrados")
    public ResponseEntity<List<ExperimentResponse>> getAllExperiments() {
        List<ExperimentResponse> experiments = experimentService.getAllExperiments()
                .stream()
                .map(ExperimentResponse::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(experiments);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar experimento", description = "Actualizar un experimento existente")
    public ResponseEntity<ExperimentResponse> updateExperiment(
            @PathVariable Long id,
            @Valid @RequestBody ExperimentRequest request) {
        Experiment experimentUpdated = new Experiment();
        experimentUpdated.setName(request.getName());
        experimentUpdated.setStatus(Experiment.ExperimentStatus.valueOf(request.getStatus()));
        experimentUpdated.setRisk(Experiment.levelRisk.valueOf(request.getRisk()));

        Investigator investigator = new Investigator();
        investigator.setLicenseNumber(request.getInvestigatorLicenseNumber());
        experimentUpdated.setInvestigator(investigator);

        Experiment updatedExperiment = experimentService.updateExperiment(id, experimentUpdated);
        return ResponseEntity.ok(ExperimentResponse.fromEntity(updatedExperiment));
    }

    @DeleteMapping("/{id")
    @Operation(summary = "Eliminar experimento")
    public ResponseEntity<Void> deleteExperiment(
            @PathVariable Long id) {
        experimentService.deleteExperiment(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/search/by-risk")
    @Operation(summary = "Búsqueda de experimento por nivel de Riesgo")
    public ResponseEntity<List<ExperimentResponse>> findByRisk(
            @RequestParam Experiment.levelRisk risk) {
        List<ExperimentResponse> responseDTOs = experimentService.findByRisk(risk)
                .stream()
                .map(ExperimentResponse::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }


    @GetMapping("/search/by-investigator")
    @Operation(summary = "Búsqueda de experimentos por cedula del investigador")
    public ResponseEntity<List<ExperimentResponse>> findByInvestigator(@RequestParam String licenseNumber) {
        List<ExperimentResponse> responseDTOs = experimentService.findByInvestigatorLicenseNumber(licenseNumber)
                .stream()
                .map(ExperimentResponse::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/search/by-date")
    @Operation(summary = "Búsqueda de experimentos por fecha de creación")
    public ResponseEntity<List<ExperimentResponse>> findByCreationDate(@RequestParam LocalDate date) {
        List<ExperimentResponse> responseDTOs = experimentService.findByCreationDate(date).stream()
                .map(ExperimentResponse::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }
    @GetMapping("/search/by-investigator-and-status")
    @Operation(summary = "Búsqueda de experimentos por investigador y estado")
    public ResponseEntity<List<ExperimentResponse>> findByInvestigatorAndStatus(
            @RequestParam String licenseNumber,
            @RequestParam Experiment.ExperimentStatus status) {

        List<ExperimentResponse> responseDTOs = experimentService.findByInvestigatorLicenseNumberAndStatus(licenseNumber, status)
                .stream()
                .map(ExperimentResponse::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/exists/folio/{folio}")
    public ResponseEntity<Void> checkFolioExists(@PathVariable String folio) {
        return experimentService.findByFolio(folio)
                .map(experiment -> ResponseEntity.ok().<Void>build()) // Si existe, devuelve 200 OK
                .orElse(ResponseEntity.notFound().build());          // Si no existe, devuelve 404 Not Found
    }
}
