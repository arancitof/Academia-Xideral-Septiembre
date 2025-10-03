package com.ArgonLaboratory.SistemaDeGestionLaboratorio.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.modulith.events.Externalized;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Externalized("sistemagestionlaboratorio.investigator.created::#{#this.investigatorId}")
public class InvestigatorCreatedEvent {

    private Long investigatorId;
    private String fullName;
    private String email;
    private LocalDateTime createdAt;
}
