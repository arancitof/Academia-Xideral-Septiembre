package com.ArgonLaboratory.SistemaDeGestionLaboratorio.batch.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Este controller tiene la finalidad de lanzar el job sin necesidad de esperar a fin de mes
//Es solomante para fines de probar el proceso
@RestController
@RequestMapping("/jobs")
public class JobLauncherController {

    private static final Logger log = LoggerFactory.getLogger(JobLauncherController.class);

    //Este controller depende de varios factores
    //Srping Bathc
    @Autowired
    private JobLauncher jobLauncher;

    //Nuestro job que genera el reporte
    @Autowired
    private Job monthlyReportJob;

    //Solo necesitamos el lanzador, es decir una peticion Post
    @RequestMapping("/launch")
    public ResponseEntity<String> launchMonthlyReportJob(){
        log.info("Se ha recibido la solicitud de generar el reporte mensual");
        try {
            //Le damos un conjunto de parametros para que el job se unico
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();

            //Aqui se ejecuta el job
            jobLauncher.run(monthlyReportJob, jobParameters);

            return ResponseEntity.ok("Reporte mensual generado con excito");
        } catch (Exception e){
            log.error("Error al generar el reporte mensual", e);
            return ResponseEntity.status(500).body("Error al generar el reporte mensual");
        }

    }

}
