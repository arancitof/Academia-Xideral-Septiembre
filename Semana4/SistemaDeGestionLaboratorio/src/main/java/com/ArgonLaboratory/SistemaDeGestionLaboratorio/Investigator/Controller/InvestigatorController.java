package com.ArgonLaboratory.SistemaDeGestionLaboratorio.Investigator.Controller;


import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Investigator.dto.InvestigatorRequest;
import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Investigator.dto.InvestigatorResponse;
import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Investigator.model.Investigator;
import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Investigator.service.InvestigatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/investigators")
@RequiredArgsConstructor
@Tag(name = "Investigator", description = "Investigator API")
public class InvestigatorController {

    //Recuerda que el Controller depende del Service
    private final InvestigatorService investigatorService;

    //END Point para crear investigator
    @PostMapping
    @Operation(summary = "Crear Investigador", description = "Crear un nuevo investigador")
    public ResponseEntity<InvestigatorResponse> createInvestigator(
            @Valid @RequestBody InvestigatorRequest investigatorRequest){
        Investigator investigator = new Investigator();
        investigator.setFirstname(investigatorRequest.getFirstName());
        investigator.setLastName(investigatorRequest.getLastName());
        investigator.setEmail(investigatorRequest.getEmail());
        investigator.setLicenseNumber(investigatorRequest.getLicenseNumber());
        investigator.setSpecialization(investigatorRequest.getSpecialization());
        investigator.setPhone(investigatorRequest.getPhone());

        Investigator createdInvestigator = investigatorService.createInvestigator(investigator);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(InvestigatorResponse.fromEntity(createdInvestigator));

    }

    //Para actualizar un investigador
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar Investigador", description = "Actualizar desde uno ya existente")
    public ResponseEntity<InvestigatorResponse> updateInvestigator(
            @PathVariable Long id,
            @Valid @RequestBody InvestigatorRequest investigatorRequest){
        Investigator investigator = new Investigator();
        investigator.setFirstname(investigatorRequest.getFirstName());
        investigator.setLastName(investigatorRequest.getLastName());
        investigator.setEmail(investigatorRequest.getEmail());
        investigator.setLicenseNumber(investigatorRequest.getLicenseNumber());
        investigator.setSpecialization(investigatorRequest.getSpecialization());
        investigator.setPhone(investigatorRequest.getPhone());

        Investigator updatedInvestigator = investigatorService.updateInvestigator(id, investigator);
        return ResponseEntity.ok(InvestigatorResponse.fromEntity(updatedInvestigator));
    }

    //Obtener un Investigador por ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener Investigador por ID", description = "Obtener un investigador por su ID")
    public ResponseEntity<InvestigatorResponse> getInvestigatorById(
            @PathVariable Long id){
        Investigator investigator = investigatorService.getInvestigatorById(id);
        return ResponseEntity.ok(InvestigatorResponse.fromEntity(investigator));
    }

    //Obtener un Investigador por Cedula
    @GetMapping("/license/{licenseNumber}")
    @Operation(summary = "Obtener Investigador por Cedula", description = "Obtener un investigador por su Cedula")
    public ResponseEntity<InvestigatorResponse> getInvestigatorByLicenseNumber(
            @PathVariable String licenseNumber){
        Investigator investigator = investigatorService.getInvestigatorByLicenseNumber(licenseNumber);
        return ResponseEntity.ok(InvestigatorResponse.fromEntity(investigator));
    }

    //Obtener a todos los investigadores
    @GetMapping
    @Operation(summary = "Obtener todos los Investigadores", description = "Obtener a todos los investigadores registrados")
    public ResponseEntity<List<InvestigatorResponse>> getAllInvestigators(
            @RequestParam(required = false) String specialization){
        List<Investigator> investigators = investigatorService.getAllInvestigators();
        List<InvestigatorResponse> response = investigators.stream()
                .map(InvestigatorResponse::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    //Eliminar a un Investigador
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar Investigador")
    public ResponseEntity<Void> deleteInvestigator(
            @PathVariable Long id){
        investigatorService.deleteInvestigator(id);
        return ResponseEntity.noContent().build();
    }



}
