package com.ArgonLaboratory.SistemaDeGestionLaboratorio.Investigator.service;

import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Investigator.model.Investigator;
import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Investigator.model.Specialization;

import java.util.List;
import java.util.Optional;

public interface InvestigatorService {

    //Para crear y guardar un nuevo investigador
    Investigator createInvestigator(Investigator investigator);

    //Para actualizar un investigador
    Investigator updateInvestigator(Long id, Investigator investigator);

    //Para obtener un Investigador por su ID
    Investigator getInvestigatorById(Long id);

    Investigator getInvestigatorByLicenseNumber(String licenseNumber);

    //Para obtener un Investigador por su Email
    Optional<Investigator> getInvestigatorByEmail(String email);

    //Para obtener todos los Investigadores
    List<Investigator> getAllInvestigators(Specialization specialization);

    //Para Borrar un investigador
    void deleteInvestigator(Long id);

    //Para Cambiar Email

    boolean existsByEmail(String email);



}
