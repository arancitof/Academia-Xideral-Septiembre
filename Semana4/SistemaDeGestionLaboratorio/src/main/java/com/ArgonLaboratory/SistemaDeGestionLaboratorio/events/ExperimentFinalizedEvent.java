package com.ArgonLaboratory.SistemaDeGestionLaboratorio.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.modulith.events.Externalized;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Externalized("experiment-finalized")
public class ExperimentFinalizedEvent {
}
