package com.HospitalSanFermin.GestionClinica.Patients;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    List<Paciente> findByCitaAgendada(boolean citaAgendada);
}
