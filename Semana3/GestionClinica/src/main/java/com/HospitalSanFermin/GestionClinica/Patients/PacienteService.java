package com.HospitalSanFermin.GestionClinica.Patients;

import com.HospitalSanFermin.GestionClinica.Doctors.Doctor;
import com.HospitalSanFermin.GestionClinica.Doctors.DoctorRepository;
import com.HospitalSanFermin.GestionClinica.SharedNotifications.CitaAgendadaEvent;
import com.HospitalSanFermin.GestionClinica.appointments.Cita;
import com.HospitalSanFermin.GestionClinica.appointments.CitaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final DoctorRepository doctorRepository;
    private final CitaRepository citaRepository;
    private final ApplicationEventPublisher eventPublisher;


    @Autowired
    public PacienteService(PacienteRepository pacienteRepository,
                           DoctorRepository doctorRepository,
                           CitaRepository citaRepository,
                           ApplicationEventPublisher eventPublisher) {
        this.pacienteRepository = pacienteRepository;
        this.doctorRepository = doctorRepository;
        this.citaRepository = citaRepository;
        this.eventPublisher = eventPublisher;

    }



    @Transactional
    public Cita agendarCita(Long pacienteId, Long doctorId, LocalDateTime fechaHora, String motivoCita) {
        //Verificamos si el paciente ya tiene unc cita agendada
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(()-> new IllegalStateException("El paciente con el ID: " + pacienteId + " no existe"));

        Optional<Cita> citaExistente = citaRepository.findByPacienteCitaFechaHora(paciente, LocalDateTime.now());
        if(citaExistente.isPresent()){
            throw new IllegalStateException("El paciente ya tiene una cita agendada para esta fecha y hora: " + citaExistente.get().getFechaHora());
        }

        //Verificamos si el doctor existe
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(()-> new IllegalStateException("El doctor con el ID: " + doctorId + " no existe"));

        //Creamos y agendamos la cita
        Cita nuevaCita = new Cita(paciente, doctor, fechaHora, motivoCita);
        Cita citaGuardada = citaRepository.save(nuevaCita);

        //Publicamos el evento de que el paciente agendo una cita
        eventPublisher.publishEvent(new CitaAgendadaEvent(this,citaGuardada));

        return citaGuardada;



    }



}
