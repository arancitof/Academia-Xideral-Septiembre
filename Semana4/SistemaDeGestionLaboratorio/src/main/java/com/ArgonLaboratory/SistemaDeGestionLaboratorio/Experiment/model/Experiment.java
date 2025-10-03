package com.ArgonLaboratory.SistemaDeGestionLaboratorio.Experiment.model;

import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Investigator.model.Investigator;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "experiments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Experiment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Experiment name is required")
    @Column(nullable = false, length = 255)
    private String name;

    @Column(name ="folio", unique = true, nullable = false, updatable = false, length = 50)
    private String folio;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private ExperimentStatus status;

    @Column(nullable = false ,length = 100)
    private levelRisk risk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "investigator_license_number", referencedColumnName = "licenseNumber")
    private Investigator investigator;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public enum ExperimentStatus {
        PLANNING,
        IN_PROGRESS,
        COMPLETED,
        CANCELLED
    }

    public enum levelRisk{
        LOW,
        MEDIUM,
        HIGH
    }
}
