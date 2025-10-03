package com.ArgonLaboratory.SistemaDeGestionLaboratorio.Experiment.service;

import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Experiment.model.Experiment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ExperimentService {

    //Crear y guardar un nuevo experimento
    Experiment createExperiment(Experiment experiment);

    //Buscar experimento por Id
    Optional<Experiment> getExperimentById(Long id);

    //Devolver lista de los experimentos
    List<Experiment> getAllExperiments();

    //Actualizar un experimento existente
    Experiment updateExperiment(Long id, Experiment experiment);

    //Eliminar un experimento por su id
    void deleteExperiment(Long id);

    //---------Búsquedas--------
    //Buscar experimento por nombre
    List<Experiment> findByName(String name);

    //Buscar experimento por folio
    Optional<Experiment> findByFolio(String folio);

    //Buscar experimento por nivel de riesgo
    List<Experiment> findByRisk(Experiment.levelRisk risk);

    //Buscar por cedula profesional del investigador
    List<Experiment> findByInvestigatorLicenseNumber(String licenseNumber);

    //Buscar por fechas de creación
    List<Experiment> findByCreationDate(LocalDate date);

    //Buscar por investigador y estado
    List<Experiment> findByInvestigatorLicenseNumberAndStatus(String licenseNumber, Experiment.ExperimentStatus status);

    boolean existsByFolio(String folio);


}
