package com.HospitalSanFermin.GestionClinica.appointments;

import com.HospitalSanFermin.GestionClinica.Doctors.Doctor;
import com.HospitalSanFermin.GestionClinica.Patients.Paciente;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "citas")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "medico_id", nullable = false)
    private Doctor doctor;

    @Column(nullable = false)
    private LocalDateTime fechaHora;

    @Column
    private String motivoCita;



    public Cita(Paciente paciente, Doctor doctor, LocalDateTime fechaHora, String motivoCita) {
        this.paciente = paciente;
        this.doctor = doctor;
        this.fechaHora = fechaHora;
        this.motivoCita = motivoCita;


    }





}
