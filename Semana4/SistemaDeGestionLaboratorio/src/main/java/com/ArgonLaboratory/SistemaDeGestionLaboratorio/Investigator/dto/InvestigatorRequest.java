package com.ArgonLaboratory.SistemaDeGestionLaboratorio.Investigator.dto;


import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Investigator.model.Specialization;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvestigatorRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$",
            message = "El nombre solo puede contener letras")
    private String firstName;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 100, message = "El apellido debe tener entre 2 y 100 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$",
            message = "El apellido solo puede contener letras")
    private String lastName;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email no es válido")
    private String email;

    @NotBlank(message = "La cédula profesional es obligatoria")
    @Pattern(regexp = "^[0-9]{7,8}$",
            message = "La cédula debe contener 7 u 8 dígitos")
    private String licenseNumber;

    @NotNull(message = "La especialización es requerida")
    private Specialization specialization;

    @NotBlank(message = "El numero de teléfono es requerido")
    @Pattern(regexp = "^\\d{10}$", message = "El número de teléfono debe tener 10 dígitos")
    private String phone;
}
