package com.ArgonLaboratory.SistemaDeGestionLaboratorio.Investigator.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "investigator")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Investigator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es requerido")
    @Column(name = "firstname", nullable = false, length = 100)
    private String firstname;

    @NotBlank(message = "El apellido es requerido")
    @Column(nullable = false, length = 100)
    private String lastName;

    @NotBlank(message = "Un email es requerido")
    @Email(message = "Email no valido")
    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @NotBlank(message = "La cedula es obligatoria")
    @Column(nullable = false, length = 100, unique = true)
    private String licenseNumber; //Cedula profesional

    @NotBlank(message = "La especialización es requerida")
    @Column(name = "specialization", nullable = false)
    private String specialization;

    @NotBlank(message = "El numero de teléfono es requerido ")
    @Pattern(regexp = "^\\d{10}$", message = "Numero no valido")
    @Column(nullable = false, length = 20)
    private String phone;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public String getFullName(){
        return firstname + " " + lastName;
    }

    public enum Specialization{
        ORGANIC_CHEMISTRY,
        INORGANIC_CHEMISTRY,
        ANALYTICAL_CHEMISTRY,
        PHYSICAL_CHEMISTRY,
        BIOCHEMISTRY,
        ENVIRONMENTAL_CHEMISTRY,
        PHARMACEUTICAL_CHEMISTRY;
    }

}
