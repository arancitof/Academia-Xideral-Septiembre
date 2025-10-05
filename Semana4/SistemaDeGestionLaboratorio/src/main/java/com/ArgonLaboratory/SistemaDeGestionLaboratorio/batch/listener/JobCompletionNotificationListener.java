package com.ArgonLaboratory.SistemaDeGestionLaboratorio.batch.listener;

import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Investigator.model.Specialization;
import com.ArgonLaboratory.SistemaDeGestionLaboratorio.batch.dto.DepartamentalReport;
import com.ArgonLaboratory.SistemaDeGestionLaboratorio.batch.writer.DepartamentalReportWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Map;

@Component
public class JobCompletionNotificationListener implements JobExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    // Inyectamos el writer para acceder a los resultados
    private final DepartamentalReportWriter writer;

    public JobCompletionNotificationListener(DepartamentalReportWriter writer) {
        this.writer = writer;
    }

    //Este verifica el área de trabajo, se asegura que el writer este vacío y listo para su ejecución
    @Override
    public void beforeJob(JobExecution jobExecution) {
        writer.clearSummary();
        log.info("Aquí se inicia el Reporte mensual!!! 📄");
    }


    //Este es el encargado de recoger el trabajo hecho
    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("Aquí finaliza el Reporte mensual!!! 📄✅");

            //Obtenemos lo que el Writer ha hecho
            Map<Specialization, DepartamentalReport> summary = writer.getSummary();

            if (summary.isEmpty()) {
                log.info("UPS, No hay experimentos completados con éxito en el ultimo mes");
                return;
            }

            //Aquí viene lo bueno, la lógica para obtener que Departamento (Specialization)
            // tuvo mayor puntaje de éxito durante el mes
            DepartamentalReport topSpecialization = summary.values().stream()
                    .max(Comparator.comparingDouble(DepartamentalReport::getSuccessScore))
                    .orElse(null);

            //Finalmente Publicamos los Resultados por Consola
            log.info("================================================================");
            log.info("      REPORTE MENSUAL DE ESPECIALIZACIONES EXITOSAS");
            log.info("================================================================");
            summary.forEach((spec, report) -> {
                log.info(String.format("Especialización: %-20s | Experimentos Exitosos: %-5d | Puntuación: %.2f",
                        report.getSpecialization().name(),
                        report.getSuccessfulExperiments(),
                        report.getSuccessScore()));
            });
            log.info("----------------------------------------------------------------");
            if (topSpecialization != null) {
                log.info(String.format("ESPECIALIZACIÓN DEL MES: %s (Puntuación: %.2f)",
                        topSpecialization.getSpecialization().name(),
                        topSpecialization.getSuccessScore()));
            }
            log.info("================================================================");
        }
    }
}

