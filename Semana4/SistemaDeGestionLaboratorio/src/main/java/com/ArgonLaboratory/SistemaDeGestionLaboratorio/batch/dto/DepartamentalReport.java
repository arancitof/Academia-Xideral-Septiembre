package com.ArgonLaboratory.SistemaDeGestionLaboratorio.batch.dto;

import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Investigator.model.Specialization;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.YearMonth;

@Data
@NoArgsConstructor
@Document(collection = "monthly_reports")
public class DepartamentalReport {

    @Id
    private String id;

    //Para saber en que mes es el reporte
    private YearMonth reportPeriod;

    private Specialization specialization;
    private long successfulExperiments;
    private double successScore;

    public DepartamentalReport(Specialization specialization) {
        this.specialization = specialization;
        // ahora see calcula el periodo del reporte (el mes pasado)
        this.reportPeriod = YearMonth.now().minusMonths(1);
        // Se crea un ID único y predecible, ejemplo: "Biochemistry_2023-10"
        // Esto evita duplicados si el job se ejecuta más de una vez.
        this.id = specialization.name() + "_" + this.reportPeriod.toString();
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
