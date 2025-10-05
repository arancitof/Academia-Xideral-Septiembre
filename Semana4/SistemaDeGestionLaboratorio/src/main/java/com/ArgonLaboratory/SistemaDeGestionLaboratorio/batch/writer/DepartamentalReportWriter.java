package com.ArgonLaboratory.SistemaDeGestionLaboratorio.batch.writer;


import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Experiment.model.Experiment;
import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Investigator.model.Investigator;
import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Investigator.model.Specialization;
import com.ArgonLaboratory.SistemaDeGestionLaboratorio.batch.dto.DepartamentalReport;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.ArgonLaboratory.SistemaDeGestionLaboratorio.Experiment.model.Experiment.ExperimentStatus.COMPLETED;

//Par ser inyectado despues
@Component
public class DepartamentalReportWriter implements ItemWriter<Experiment> {

    //Para guardar los resultados
    private final Map<Specialization, DepartamentalReport> summary = new ConcurrentHashMap<>();

    @Override
    public void write(Chunk<? extends Experiment> chunk) throws Exception{
        for (Experiment experiment : chunk){
            if (experiment.getStatus() == Experiment.ExperimentStatus.COMPLETED){

                Investigator investigator = experiment.getInvestigator();
                        //Para no considerar experimentos completados sin investigador
                if( investigator == null || investigator.getSpecialization() == null){
                    continue;

                }

                Specialization specialization = investigator.getSpecialization();
                String riskLevel = experiment.getRisk().name();

                // Lógica clave: agregar al mapa de resumen.
                summary.computeIfAbsent(specialization, DepartamentalReport::new)
                        .incrementScore(riskLevel);
            }
        }
    }

    public Map<Specialization, DepartamentalReport> getSummary(){
        return summary;
    }

    public void clearSummary(){
        summary.clear();
    }

}
