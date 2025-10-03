package com.ArgonLaboratory.SistemaDeGestionLaboratorio.Investigator.repository;

import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Investigator.model.Investigator;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InvestigatorRepository extends JpaRepository<Investigator, Long> {

    List<Investigator> findBySpecialization(String specialization);

    Optional<Investigator> findByLicenseNumber(String licenseNumber);

    Optional<Investigator> findByEmail(String email);

    boolean existsByLicenseNumber(String licenseNumber);

    boolean existsByEmail(String email);


}
