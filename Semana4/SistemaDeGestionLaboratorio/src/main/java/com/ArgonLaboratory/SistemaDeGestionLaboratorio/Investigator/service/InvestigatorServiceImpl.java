package com.ArgonLaboratory.SistemaDeGestionLaboratorio.Investigator.service;


import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Investigator.model.Investigator;
import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Investigator.repository.InvestigatorRepository;
import com.ArgonLaboratory.SistemaDeGestionLaboratorio.events.InvestigatorCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class InvestigatorServiceImpl implements InvestigatorService{

    //Lo que necesito para implementar el servicio
    private final InvestigatorRepository investigatorRepository;
    private final ApplicationEventPublisher eventPublisher;


    //Crear un Investigador
    @Override
    public Investigator createInvestigator(Investigator investigator) {
        //Dos Investigadores no pueden tener dos CEDULAS!
        if(investigatorRepository.existsByLicenseNumber(investigator.getLicenseNumber())){
            throw new IllegalStateException("Ya existe un investigador con esa cedula profesional: " +investigator.getLicenseNumber());
        }
        //Guardamos el investigador
        Investigator savedInvestigator = investigatorRepository.save(investigator);

        //Publicamos el evento de que se ha creado un nuevo investigador
        InvestigatorCreatedEvent event = new InvestigatorCreatedEvent(
                savedInvestigator.getId(),
                savedInvestigator.getFullName(),
                savedInvestigator.getEmail(),
                savedInvestigator.getCreatedAt()
        );
        eventPublisher.publishEvent(event);
        log.info("InvestigatorCreatedEvent publicado para el investigador con Cedula: {}" , savedInvestigator.getLicenseNumber());
        return savedInvestigator;

    }

    //Actualizar un Investigador
    @Override
    public Investigator updateInvestigator(Long id, Investigator investigator) {

        Investigator existingInvestigator = getInvestigatorById(id);
        if (!existingInvestigator.getEmail().equals(investigator.getEmail()) &&
            investigatorRepository.existsByEmail(investigator.getEmail())) {
                throw new IllegalArgumentException("Este Email ya existe: " + investigator.getEmail());
        }
        existingInvestigator.setEmail(investigator.getEmail());
        existingInvestigator.setFirstname(investigator.getFirstname());
        existingInvestigator.setLastName(investigator.getLastName());
        existingInvestigator.setSpecialization(investigator.getSpecialization());
        existingInvestigator.setPhone(investigator.getPhone());

        return investigatorRepository.save(existingInvestigator);
    }

    //Obtener un Investigador por su ID
    @Override
    @Transactional(readOnly = true)
    public Investigator getInvestigatorById(Long id) {
        return investigatorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Investigador no encontrado con el id: " + id));
    }

    //Buscar por Cedula
    @Override
    @Transactional(readOnly = true)
    public Investigator getInvestigatorByLicenseNumber(String licenseNumber) {
        return investigatorRepository.findByLicenseNumber(licenseNumber)
                .orElseThrow(() -> new IllegalArgumentException("Investigador no encontrado con cédula: " + licenseNumber));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Investigator> getInvestigatorByEmail(String email) {
        return investigatorRepository.findByEmail(email);
    }


    @Override
    @Transactional(readOnly = true)
    public List<Investigator> getAllInvestigators() {
        return investigatorRepository.findAll();
    }

    @Override
    public void deleteInvestigator(Long id) {
        //Comprobamos si hay un Investigador con ese ID
        if(!investigatorRepository.existsById(id)){
            throw new IllegalArgumentException("No se puede eliminar un Investigador que no existe!");
    }
        investigatorRepository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return investigatorRepository.existsByEmail(email);
    }
}
