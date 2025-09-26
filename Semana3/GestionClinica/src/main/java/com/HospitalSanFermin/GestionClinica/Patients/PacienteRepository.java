package com.HospitalSanFermin.GestionClinica.Patients;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    //Esto nos ayudará para verificar si un paciente ya existe
    //Para en caso no estar en la base de datos, crear uno nuevo
    Optional<Paciente> findByCurp(String curp);
    Optional<Paciente> findByEmail(String email);
}
