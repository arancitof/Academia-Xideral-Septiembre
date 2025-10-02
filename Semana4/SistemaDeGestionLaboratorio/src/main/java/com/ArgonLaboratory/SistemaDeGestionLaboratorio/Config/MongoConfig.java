package com.ArgonLaboratory.SistemaDeGestionLaboratorio.Config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ConditionalOnBean(MongoTemplate.class)
@EnableMongoRepositories(
        basePackages = {"com.ArgonLaboratory.SistemaDeGestionLaboratorio.Notifications.repository",
                "com.ArgonLaboratory.SistemaDeGestionLaboratorio.Experiment.repository"
        })

public class MongoConfig {
}
