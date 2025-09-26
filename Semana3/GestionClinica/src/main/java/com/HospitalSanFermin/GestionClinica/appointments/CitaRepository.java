package com.HospitalSanFermin.GestionClinica.appointments;

import com.HospitalSanFermin.GestionClinica.Patients.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    Optional<Cita> findByPacienteAndFechaHora(Paciente paciente, LocalDateTime fechaHora);

    //Para Consultar las citas de un paciente
    List<Cita>findByPacienteId(Long pacienteId);
}
