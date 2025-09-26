package com.HospitalSanFermin.GestionClinica.Doctors;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "medicos")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false, unique = true)
    private String rfc;

    @Column(nullable = false, unique = true)
    private String telefono;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String especialidad;

    @Column(nullable = false)
    private boolean disponible;

    public Doctor(String nombre, String apellido, String rfc, String telefono, String email, String especialidad, boolean disponible){
        this.nombre = nombre;
        this.apellido = apellido;
        this.rfc = rfc;
        this.telefono = telefono;
        this.email = email;
        this.especialidad = especialidad;
        this.disponible = disponible;
    }

}
