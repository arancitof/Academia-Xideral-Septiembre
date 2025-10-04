package com.ArgonLaboratory.SistemaDeGestionLaboratorio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {
        "com.ArgonLaboratory.SistemaDeGestionLaboratorio.Investigator.repository",
        "com.ArgonLaboratory.SistemaDeGestionLaboratorio.Experiment.repository"
})
@EnableMongoRepositories(basePackages = "com.ArgonLaboratory.SistemaDeGestionLaboratorio.Notifications.repository")
public class SistemaDeGestionLaboratorioApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaDeGestionLaboratorioApplication.class, args);
	}

}
