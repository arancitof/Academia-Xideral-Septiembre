package com.HospitalSanFermin.GestionClinica.Patients;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "pacientes")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = false)
    private String numeroPaciente;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false)
    private String sexo;

    @Column(nullable = false, unique = true)
    private String curp;

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false, unique = true)
    private LocalDate fechaNacimiento;

    @Column
    private boolean citaAgendada;

    public Paciente(String numeroPaciente ,String nombre, String apellido, String sexo, String curp, String telefono, String email, String direccion, LocalDate fechaNacimiento, boolean citaAgendada){
        this.numeroPaciente = numeroPaciente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.sexo = sexo;
        this.curp = curp;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
        this.citaAgendada = false;
    }


    //Verificar si el paciente ya tiene una cita
    public boolean tieneCita(){
        return this.citaAgendada;
    }

    //Para agendar nueva cita
    public void agendarCita(){
        if(this.citaAgendada){
            throw new IllegalStateException("El paciente ya tiene una cita agendada");
        }
        this.citaAgendada = true;
    }

    //Para cancelar cita
    public void cancelarCita(){
        if(this.citaAgendada){
            this.citaAgendada = false;
        }else{
            throw new IllegalStateException("El paciente no tiene una cita agendada para cancelar");
        }
    }






}
