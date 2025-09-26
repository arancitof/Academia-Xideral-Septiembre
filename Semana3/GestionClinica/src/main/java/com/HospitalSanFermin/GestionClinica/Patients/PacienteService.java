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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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

    //Metodo para registrar nuevo paciente
    @Transactional
    public Paciente registrarPaciente(String nombre, String apellido, String sexo, String curp, String telefono, String email, String direccion, LocalDate fechaNacimiento) {
        // Verificar si ya existe un paciente con el mismo CURP o email
        if (pacienteRepository.findByCurp(curp).isPresent()) {
            throw new IllegalStateException("Ya existe un paciente con el CURP proporcionado.");
        }
        if (pacienteRepository.findByEmail(email).isPresent()) {
            throw new IllegalStateException("Ya existe un paciente con el email proporcionado.");
        }

        //Lógica para generar el número de pacientes
        long totalpacientesRegistrados = pacienteRepository.count();
        String numeroPaciente = String.format("SF-%05d", totalpacientesRegistrados + 1);

        Paciente nuevoPaciente = new Paciente(numeroPaciente, nombre, apellido, sexo, curp, telefono, email, direccion, fechaNacimiento, false);
        return pacienteRepository.save(nuevoPaciente);
    }



    //Metodo para agendar una nueva cita
    @Transactional
    public Cita agendarCita(String numeroPaciente, String nombreDoctor, String especialidad, LocalDateTime fechaHora, String motivoCita) {
        //Verificamos si el paciente ya tiene unc cita agendada
        Paciente paciente = pacienteRepository.findByNumeroPaciente(numeroPaciente)
                .orElseThrow(()-> new IllegalStateException("El paciente con el ID: " + numeroPaciente + " no existe"));

        Optional<Cita> citaExistente = citaRepository.findByPacienteAndFechaHora(paciente, fechaHora);
        if(citaExistente.isPresent()){
            throw new IllegalStateException("El paciente ya tiene una cita agendada para esta fecha y hora: " + citaExistente.get().getFechaHora());
        }

        //Verificamos si el doctor existe
        Doctor doctor = doctorRepository.findFirstByNombreAndEspecialidadAndDisponible(nombreDoctor, especialidad, true)
                .orElseThrow(()-> new IllegalStateException("El doctor con el nombre: " + nombreDoctor + " y especialidad: " + especialidad + " no existe"));

        //Creamos y agendamos la cita
        Cita nuevaCita = new Cita(paciente, doctor, fechaHora, motivoCita);
        Cita citaGuardada = citaRepository.save(nuevaCita);

        //Publicamos el evento de que el paciente agendo una cita
        eventPublisher.publishEvent(new CitaAgendadaEvent(this,citaGuardada));

        return citaGuardada;
    }

    //metodo para que el paciente consulte sus citas
    public List<Cita> pacienteMisCitas(String numeroPaciente){
        // Buscamos al paciente por su número para obtener su ID interno.
        Paciente paciente = pacienteRepository.findByNumeroPaciente(numeroPaciente)
                .orElseThrow(() -> new IllegalStateException("No se encontró un paciente con el número: " + numeroPaciente));


        //Usamos su id interno para buscar sus citas.
        return citaRepository.findByPacienteId(paciente.getId());
    }
}
