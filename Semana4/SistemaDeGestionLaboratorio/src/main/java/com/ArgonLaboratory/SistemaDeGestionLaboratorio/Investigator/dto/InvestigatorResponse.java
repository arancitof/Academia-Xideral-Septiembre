package com.ArgonLaboratory.SistemaDeGestionLaboratorio.Investigator.dto;

import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Investigator.model.Investigator;
import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Investigator.model.Specialization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvestigatorResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String licenseNumber;
    private Specialization specialization;
    private String phone;
    private String createdAt;

    public static InvestigatorResponse fromEntity(Investigator investigator) {
        return new InvestigatorResponse(
                investigator.getId(),
                investigator.getFirstname(),
                investigator.getLastName(),
                investigator.getEmail(),
                investigator.getLicenseNumber(),
                investigator.getSpecialization(),
                investigator.getPhone(),
                investigator.getCreatedAt().toString()
                );
    }
}
