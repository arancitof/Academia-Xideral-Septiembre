package com.ArgonLaboratory.SistemaDeGestionLaboratorio.Investigator.repository;

import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Investigator.model.Investigator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@EntityScan(basePackageClasses = Investigator.class)
@TestPropertySource(properties = {
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=FALSE",
    "spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl"
})
public class InvestigatorRepositoryTest {

    @Autowired
    private InvestigatorRepository investigatorRepository;

    @BeforeEach
    void setUp() {
        // investigatorRepository.deleteAll(); // No es necesario con create-drop

        Investigator investigator1 = new Investigator();
        investigator1.setFirstname("Christian");
        investigator1.setLastName("Ramirez");
        investigator1.setEmail("chris@xideral.co");
        investigator1.setLicenseNumber("12345678");
        investigator1.setSpecialization("Biologia Molecular");
        investigator1.setPhone("1234567890");

        Investigator investigator2 = new Investigator();
        investigator2.setFirstname("Ana");
        investigator2.setLastName("Gomez");
        investigator2.setEmail("ana@xideral.co");
        investigator2.setLicenseNumber("87654321");
        investigator2.setSpecialization("Biologia Molecular");
        investigator2.setPhone("0987654321");

        Investigator investigator3 = new Investigator();
        investigator3.setFirstname("Pedro");
        investigator3.setLastName("Perez");
        investigator3.setEmail("pedro@xideral.co");
        investigator3.setLicenseNumber("11223344");
        investigator3.setSpecialization("Quimica Organica");
        investigator3.setPhone("1122334455");

        investigatorRepository.saveAll(List.of(investigator1, investigator2, investigator3));
    }

    @DisplayName("Prueba para buscar un investigador por su email")
    @Test
    void findByEmail_Success() {
        // Arrange
        String email = "chris@xideral.co";

        // Act
        Optional<Investigator> foundInvestigator = investigatorRepository.findByEmail(email);

        // Assert
        assertThat(foundInvestigator).isPresent();
        assertThat(foundInvestigator.get().getEmail()).isEqualTo(email);
    }

    @DisplayName("Prueba para verificar que no se encuentra un investigador con un email inexistente")
    @Test
    void findByEmail_NotFound() {
        // Arrange
        String email = "nonexistent@xideral.co";

        // Act
        Optional<Investigator> foundInvestigator = investigatorRepository.findByEmail(email);

        // Assert
        assertThat(foundInvestigator).isNotPresent();
    }

    @DisplayName("Prueba para buscar un investigador por su número de cédula")
    @Test
    void findByLicenseNumber_Success() {
        // Arrange
        String licenseNumber = "12345678";

        // Act
        Optional<Investigator> foundInvestigator = investigatorRepository.findByLicenseNumber(licenseNumber);

        // Assert
        assertThat(foundInvestigator).isPresent();
        assertThat(foundInvestigator.get().getLicenseNumber()).isEqualTo(licenseNumber);
    }

    @DisplayName("Prueba para verificar que no se encuentra un investigador con una cédula inexistente")
    @Test
    void findByLicenseNumber_NotFound() {
        // Arrange
        String licenseNumber = "00000000";

        // Act
        Optional<Investigator> foundInvestigator = investigatorRepository.findByLicenseNumber(licenseNumber);

        // Assert
        assertThat(foundInvestigator).isNotPresent();
    }

    @DisplayName("Prueba para buscar investigadores por especialización")
    @Test
    void findBySpecialization_Success() {
        // Arrange
        String specialization = "Biologia Molecular";

        // Act
        List<Investigator> investigators = investigatorRepository.findBySpecialization(specialization);

        // Assert
        assertThat(investigators).isNotNull();
        assertThat(investigators.size()).isEqualTo(2);
    }

    @DisplayName("Prueba para verificar que se devuelve una lista vacía para una especialización inexistente")
    @Test
    void findBySpecialization_NotFound() {
        // Arrange
        String specialization = "Fisica Cuantica";

        // Act
        List<Investigator> investigators = investigatorRepository.findBySpecialization(specialization);

        // Assert
        assertThat(investigators).isNotNull();
        assertThat(investigators).isEmpty();
    }

    @DisplayName("Prueba para verificar si un email existe")
    @Test
    void existsByEmail_True() {
        // Arrange
        String email = "ana@xideral.co";

        // Act
        boolean exists = investigatorRepository.existsByEmail(email);

        // Assert
        assertThat(exists).isTrue();
    }

    @DisplayName("Prueba para verificar si un email no existe")
    @Test
    void existsByEmail_False() {
        // Arrange
        String email = "nonexistent@xideral.co";

        // Act
        boolean exists = investigatorRepository.existsByEmail(email);

        // Assert
        assertThat(exists).isFalse();
    }

    @DisplayName("Prueba para verificar si una cédula existe")
    @Test
    void existsByLicenseNumber_True() {
        // Arrange
        String licenseNumber = "87654321";

        // Act
        boolean exists = investigatorRepository.existsByLicenseNumber(licenseNumber);

        // Assert
        assertThat(exists).isTrue();
    }

    @DisplayName("Prueba para verificar si una cédula no existe")
    @Test
    void existsByLicenseNumber_False() {
        // Arrange
        String licenseNumber = "00000000";

        // Act
        boolean exists = investigatorRepository.existsByLicenseNumber(licenseNumber);

        // Assert
        assertThat(exists).isFalse();
    }
}
