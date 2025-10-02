package com.ArgonLaboratory.SistemaDeGestionLaboratorio.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(
        basePackages = "com.ArgonLaboratory.SistemaDeGestionLaboratorio.Notifications.repository"
)

@EnableMongoAuditing
public class MongoConfig {
}
