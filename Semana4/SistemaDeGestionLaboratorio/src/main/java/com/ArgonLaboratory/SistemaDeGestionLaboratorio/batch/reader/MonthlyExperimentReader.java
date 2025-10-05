package com.ArgonLaboratory.SistemaDeGestionLaboratorio.batch.reader;

import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Experiment.model.Experiment;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.builder.MongoPagingItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Map;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class MonthlyExperimentReader {

    public static ItemReader<Experiment> build(EntityManagerFactory entityManagerFactory){
        YearMonth lastMonth = YearMonth.now().minusMonths(1);
        LocalDate startDate = lastMonth.atDay(1);
        LocalDate endDate = lastMonth.atEndOfMonth();

        String jpqlQuery = "SELECT e FROM Experiment e " +
                "WHERE e.status = :status AND e.createdAt BETWEEN :startDate AND :endDate " +
                "ORDER BY e.id ASC";

        return new JpaPagingItemReaderBuilder<Experiment>()
                .name("monthlyExperimentJpaReader") // Le damos un nombre para JPA
                .entityManagerFactory(entityManagerFactory) // Se le pasa el factory de JPA
                .queryString(jpqlQuery) // Se le pasa la consulta JPQL
                .parameterValues(Map.of( // Se le pasan los parámetros para la consulta
                        "status", Experiment.ExperimentStatus.COMPLETED,
                        "startDate", startDate.atStartOfDay(),
                        "endDate", endDate.atTime(LocalTime.MAX)
                ))
                .pageSize(10) // Le decimos que lea de la BD en páginas de 10
                .build();
    }
}
