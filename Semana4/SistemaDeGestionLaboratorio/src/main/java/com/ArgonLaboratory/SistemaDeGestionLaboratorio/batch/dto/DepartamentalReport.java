package com.ArgonLaboratory.SistemaDeGestionLaboratorio.batch.dto;

import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Investigator.model.Specialization;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepartamentalReport {

    private Specialization specialization;
    private long successfulExperiments;
    private double successScore;

    public DepartamentalReport(Specialization specialization) {
        this.specialization = specialization;
        this.successfulExperiments = 0;
        this.successScore = 0.0;
    }
    public void incrementScore(String riskLevel) {
        this.successfulExperiments++;
        switch (riskLevel.toUpperCase()) {
            case "HIGH":
                this.successScore += 3.0;
                break;
            case "MEDIUM":
                this.successScore += 2.0;
                break;
            case "LOW":
                this.successScore += 1.0;
                break;
            default:
                this.successScore += 0.5;
                break;
        }
    }


}
