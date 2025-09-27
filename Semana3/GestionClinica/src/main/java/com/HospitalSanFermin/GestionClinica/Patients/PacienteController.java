package com.HospitalSanFermin.GestionClinica.Patients;

import com.HospitalSanFermin.GestionClinica.Doctors.Doctor;
import com.HospitalSanFermin.GestionClinica.Doctors.DoctorService;
import com.HospitalSanFermin.GestionClinica.appointments.Cita;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;
    //Aqui se comunica con el modulo de doctores
    private final DoctorService doctorService;

    public PacienteController(PacienteService pacienteService , DoctorService doctorService) {
        this.pacienteService = pacienteService;
        this.doctorService = doctorService;
    }

    @PostMapping
    public ResponseEntity<Paciente> registrarPaciente(
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String sexo,
            @RequestParam String curp,
            @RequestParam String telefono,
            @RequestParam String email,
            @RequestParam String direccion,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaNacimiento){
        Paciente nuevoPaciente = pacienteService.registrarPaciente(nombre, apellido, sexo, curp, telefono, email, direccion, fechaNacimiento);
        return ResponseEntity.ok(nuevoPaciente);
    }

    @PostMapping("/citas")
            public ResponseEntity<Cita> agendarCita(
                    @RequestParam String numeroPaciente,
                    @RequestParam String nombreDoctor,
                    @RequestParam String especialidad,
                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaHora,
                    @RequestParam String motivoCita){
        Cita nuevaCita = pacienteService.agendarCita(numeroPaciente, nombreDoctor, especialidad, fechaHora, motivoCita);
        return ResponseEntity.ok(nuevaCita);
    }

    @GetMapping("/doctores-disponibles")
    public ResponseEntity<List<Doctor>> obtenerDoctoresDisponibles() {
        List<Doctor> doctoresDisponibles = doctorService.obtenerDoctoresDisponibles();
        return ResponseEntity.ok(doctoresDisponibles);
    }

    @GetMapping("/{numeroPaciente}/historial-citas")
    public ResponseEntity<List<Cita>> pacienteMisCitas(@PathVariable String numeroPaciente){
        List<Cita> citas = pacienteService.pacienteMisCitas(numeroPaciente);
        return ResponseEntity.ok(citas);
    }

    @DeleteMapping("/pacientes/{numeroPaciente}/citas/{citaId}")
    public ResponseEntity<Void> eliminarCita(@PathVariable String numeroPaciente, @PathVariable Long citaId){
        pacienteService.eliminarCita(numeroPaciente, citaId);
        return ResponseEntity.noContent().build();
    }
}
