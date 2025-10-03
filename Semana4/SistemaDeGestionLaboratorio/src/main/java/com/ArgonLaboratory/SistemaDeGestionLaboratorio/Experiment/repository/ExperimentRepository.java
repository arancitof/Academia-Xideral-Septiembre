package com.ArgonLaboratory.SistemaDeGestionLaboratorio.Experiment.repository;

import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Experiment.model.Experiment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ExperimentRepository extends JpaRepository<Experiment, Long> {

    //Encontrar experimento por nombre (Ignorando mayúsculas o minúsculas)
    List<Experiment> findByNameIgnoreCase(String name);

    //Encontrar experimento por Status
    List<Experiment> findByStatus(Experiment.ExperimentStatus status);

    //Buscar experiemnto por su numero de Folio
    Optional<Experiment> findByFolio(String folio);

    //Encontrar experimento por nivel de riesgo
    List<Experiment> findByRisk(Experiment.levelRisk risk);

    //Encontrar experimento por investigador ligado a su cedula profesional
    List<Experiment> findByInvestigatorLicenseNumber(String licenseNumber);

    //Encontrar experimento por investigador y estado
    List<Experiment> findByInvestigatorLicenseNumberAndStatus(String licenseNumber, Experiment.ExperimentStatus status);

    //Encontrar experimento por intervalo de fechas
    List<Experiment> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    boolean existsByFolio(String folio);

}
