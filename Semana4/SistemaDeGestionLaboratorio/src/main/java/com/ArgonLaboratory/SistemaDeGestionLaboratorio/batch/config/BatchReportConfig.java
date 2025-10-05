package com.ArgonLaboratory.SistemaDeGestionLaboratorio.batch.config;

import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Experiment.model.Experiment;
import com.ArgonLaboratory.SistemaDeGestionLaboratorio.batch.listener.JobCompletionNotificationListener;
import com.ArgonLaboratory.SistemaDeGestionLaboratorio.batch.reader.MonthlyExperimentReader;
import com.ArgonLaboratory.SistemaDeGestionLaboratorio.batch.writer.DepartamentalReportWriter;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchReportConfig {

    //Aqui hacemos las inyecciones de dependencias
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EntityManagerFactory entityManagerFactory;
    private final DepartamentalReportWriter writer;
    private final JobCompletionNotificationListener listener;

    public BatchReportConfig(JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            EntityManagerFactory entityManagerFactory,
            DepartamentalReportWriter writer,
            JobCompletionNotificationListener listener) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.entityManagerFactory = entityManagerFactory;
        this.writer = writer;
        this.listener = listener;
    }

    @Bean
    public Job monthlyReportJob(){
    return new JobBuilder("monthlyReportJob", jobRepository)
            //Permite ejecutar el job con nuevos parámetros
            .incrementer(new RunIdIncrementer())
            //Pone al listener para el reporte final
            .listener(listener)
            //define el flujo de ejection con el step
            .flow(reportStep())
            .end()
            .build();
    }

    @Bean
    public Step reportStep(){
        return new StepBuilder("reportStep", jobRepository)
                //Procesa solo 10 datos a la vez
                .<Experiment, Experiment>chunk(10, transactionManager)
                //Usa el lector JPA
                .reader(MonthlyExperimentReader.build(entityManagerFactory))
                .processor(processor())
                .writer(writer)
                .build();
    }

    @Bean
    public ItemProcessor<Experiment, Experiment> processor() {
        // En este caso, no necesitamos transformar el dato.
        // El Reader nos da un Experiment y el Writer espera un Experiment.
        return experiment -> experiment;
    }

}
