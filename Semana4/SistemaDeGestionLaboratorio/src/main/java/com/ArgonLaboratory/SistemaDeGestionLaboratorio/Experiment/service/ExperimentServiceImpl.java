package com.ArgonLaboratory.SistemaDeGestionLaboratorio.Experiment.service;

import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Experiment.model.Experiment;
import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Experiment.repository.ExperimentRepository;
import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Investigator.model.Investigator;
import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Investigator.repository.InvestigatorRepository;
import com.ArgonLaboratory.SistemaDeGestionLaboratorio.events.ExperimentCreatedEvent;
import com.ArgonLaboratory.SistemaDeGestionLaboratorio.events.ExperimentFinalizedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ExperimentServiceImpl implements ExperimentService{

    //El service depende de los repositorios Experiment y Investigator
    private final ExperimentRepository experimentRepository;
    private final InvestigatorRepository investigatorRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public Experiment createExperiment(Experiment experiment) {
        //Hay que asegurarnos que exista un investigador valido
        if (experiment.getInvestigator() == null || experiment.getInvestigator().getLicenseNumber() == null){
            throw new IllegalArgumentException("La licencia del investigador es obligatoria para crear un experimento.");
        }
            Investigator investigator = investigatorRepository.findByLicenseNumber(experiment.getInvestigator().getLicenseNumber())
                    .orElseThrow(() -> new IllegalArgumentException("Investigador no encontrado con la cedula: " + experiment.getInvestigator().getLicenseNumber()));
            experiment.setInvestigator(investigator);

            //Aqui generamos el folio unico del experimento
        experiment.setFolio(generateUniqueFolio());

        //Guardamos el experimento
        Experiment savedExperiment = experimentRepository.save(experiment);

        //Publicamos el evento de que se ha creado un nuevo experimento
        ExperimentCreatedEvent event = new ExperimentCreatedEvent(
                savedExperiment.getId(),
                savedExperiment.getFolio(),
                savedExperiment.getName(),
                savedExperiment.getRisk().name(),
                savedExperiment.getInvestigator().getLicenseNumber(),
                savedExperiment.getCreatedAt()
        );
        eventPublisher.publishEvent(event);
        log.info("ExperimentCreatedEvent publicado para el experimento con folio: {}", savedExperiment.getFolio());

        return savedExperiment;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Experiment> getExperimentById(Long id) {
        return experimentRepository.findById(id);
    }

    private String generateUniqueFolio() {
        Random random =new Random();
        String folio;
        do{
            int number = 10000 + random.nextInt(90000);
            folio = "EXP-" +number;
        } while (experimentRepository.existsByFolio(folio));
        return folio;
    }


    @Override
    @Transactional(readOnly = true)
    public List<Experiment> getAllExperiments() {
        return experimentRepository.findAll();
    }

    @Override
    public Experiment updateExperiment(Long id, Experiment experimentDetails) {
        //Primero hay que comprobar si existe un experimento con ese id
        Experiment existingExperiment = experimentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Experimento no encontrado con el id: " + id));

        //Guardamos el estado anterior
        Experiment.ExperimentStatus previousStatus = existingExperiment.getStatus();

        //Actualizacion de datos
        existingExperiment.setName(experimentDetails.getName());
        existingExperiment.setDescription(experimentDetails.getDescription());
        existingExperiment.setStatus(experimentDetails.getStatus());
        existingExperiment.setRisk(experimentDetails.getRisk());

        //Tambien hay que actualizar en caso de que el investigador sea nuevo
        if (experimentDetails.getInvestigator() != null && experimentDetails.getInvestigator().getLicenseNumber() != null){
            Investigator investigator = investigatorRepository.findByLicenseNumber(experimentDetails.getInvestigator().getLicenseNumber())
                    .orElseThrow(() -> new IllegalArgumentException("Investigador no encontrado con esa licencia: " + experimentDetails.getInvestigator().getLicenseNumber()));
            existingExperiment.setInvestigator(investigator);
        }else {
            throw new IllegalArgumentException("Investigador no encontrado");
        }

        //Guardamos los datos Acualizados
        Experiment updatedExperiment = experimentRepository.save(existingExperiment);

        //Comprobamos si el experimento ya se marco como finalizado
        if (updatedExperiment.getStatus() == Experiment.ExperimentStatus.COMPLETED && previousStatus != Experiment.ExperimentStatus.COMPLETED){

            //Creamos el evento
            ExperimentFinalizedEvent event = new ExperimentFinalizedEvent(
                    updatedExperiment.getId(),
                    updatedExperiment.getFolio(),
                    updatedExperiment.getName(),
                    updatedExperiment.getStatus().name(),
                    updatedExperiment.getInvestigator().getLicenseNumber(),
                    updatedExperiment.getUpdatedAt()

            );
            //Publicamos el evento
            eventPublisher.publishEvent(event);
            log.info("ExperimentFinalizedEvent ha publicado para el experimento con folio: {}", updatedExperiment.getFolio());

        }
        return updatedExperiment;
    }

    @Override
    public void deleteExperiment(Long id) {
        if (!experimentRepository.existsById(id)){
            throw new RuntimeException("No se puede eliminar un experimento que no existe!");
        }
        experimentRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Experiment> findByName(String name) {
        return experimentRepository.findByNameIgnoreCase(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Experiment> findByFolio(String folio) {
        return experimentRepository.findByFolio(folio);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Experiment> findByRisk(Experiment.levelRisk risk) {
        return experimentRepository.findByRisk(risk);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Experiment> findByInvestigatorLicenseNumber(String licenseNumber) {
        return experimentRepository.findByInvestigatorLicenseNumber(licenseNumber);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Experiment> findByCreationDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        return experimentRepository.findByCreatedAtBetween(startOfDay, endOfDay);
    }

    @Override
    public List<Experiment> findByInvestigatorLicenseNumberAndStatus(String licenseNumber, Experiment.ExperimentStatus status) {
        return experimentRepository.findByInvestigatorLicenseNumberAndStatus(licenseNumber, status);
    }

    @Override
    public boolean existsByFolio(String folio) {
        return experimentRepository.existsByFolio(folio);
    }
}
