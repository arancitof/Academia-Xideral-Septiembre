package com.ArgonLaboratory.SistemaDeGestionLaboratorio.Investigator.service;

import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Investigator.model.Investigator;
import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Investigator.repository.InvestigatorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InvestigatorServiceTest {


    @Mock
    private InvestigatorRepository investigatorRepository;

    @InjectMocks
    private InvestigatorServiceImpl investigatorService;

    //Genereamos el espacio del test
    private Investigator investigator;

    //Para cada prueba nos interesa iniciar los moks e inyectarlos
    @BeforeEach
    void setUp() {
        investigator = new Investigator();
        investigator.setId(1L);
        investigator.setFirstname("Pancho");
        investigator.setLastName("Villa");
        investigator.setEmail("pancho.villa@example.com");
        investigator.setLicenseNumber("123456789");
        investigator.setSpecialization("ANALYTICAL_CHEMISTRY");
        investigator.setPhone("1234567890");
    }

    @DisplayName("Metodo Crear Investigador")
    @Test
    void createInvestigatorSuccess() {
        // Arrange
        when(investigatorRepository.existsByLicenseNumber(investigator.getLicenseNumber())).thenReturn(false);
        when(investigatorRepository.save(any(Investigator.class))).thenReturn(investigator);

        // Act
        Investigator savedInvestigator = investigatorService.createInvestigator(investigator);

        // Assert
        assertNotNull(savedInvestigator);
        assertEquals("123456789", savedInvestigator.getLicenseNumber());
    }

    @DisplayName("Metodo Crear Investigador cuando ya Existe uno")
    @Test
    void createInvestigatorLicenseNumberAlreadyExists() {
        // Arrange
        when(investigatorRepository.existsByLicenseNumber(investigator.getLicenseNumber())).thenReturn(true);

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            investigatorService.createInvestigator(investigator);
        });

        assertEquals("Ya existe un investigador con esa cedula profesional: " + investigator.getLicenseNumber(), exception.getMessage());
    }

    @DisplayName("Metodo Actualizar Investigador")
    @Test
    void updateInvestigatorSuccess() {
        // Arrange
        Investigator updatedInfo = new Investigator();
        updatedInfo.setFirstname("Emiliano");
        updatedInfo.setLastName("Zapata");
        updatedInfo.setEmail("emiliano.zapata@example.com");
        updatedInfo.setSpecialization("PHYSICAL_CHEMISTRY");
        updatedInfo.setPhone("9876543210");

        when(investigatorRepository.findById(1L)).thenReturn(Optional.of(investigator));
        when(investigatorRepository.existsByEmail(updatedInfo.getEmail())).thenReturn(false);
        when(investigatorRepository.save(any(Investigator.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Investigator updatedInvestigator = investigatorService.updateInvestigator(1L, updatedInfo);

        // Assert
        assertNotNull(updatedInvestigator);
        assertEquals("emiliano.zapata@example.com", updatedInvestigator.getEmail());
        assertEquals("PHYSICAL_CHEMISTRY", updatedInvestigator.getSpecialization());
    }

    @DisplayName("Actualizar Investigador cuando ya existe el email")
    @Test
    void updateInvestigatorEmailAlreadyExists() {
        // Arrange
        Investigator updatedInfo = new Investigator();
        updatedInfo.setEmail("emiliano.zapata@example.com");

        when(investigatorRepository.findById(1L)).thenReturn(Optional.of(investigator));
        when(investigatorRepository.existsByEmail(updatedInfo.getEmail())).thenReturn(true);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            investigatorService.updateInvestigator(1L, updatedInfo);
        });

        assertEquals("Este Email ya existe: " + updatedInfo.getEmail(), exception.getMessage());
    }

    @DisplayName("Metodo Obtener Investigador por ID")
    @Test
    void getInvestigatorByIdSuccess() {
        // Arrange
        when(investigatorRepository.findById(1L)).thenReturn(Optional.of(investigator));

        // Act
        Investigator foundInvestigator = investigatorService.getInvestigatorById(1L);

        // Assert
        assertNotNull(foundInvestigator);
        assertEquals(1L, foundInvestigator.getId());
    }

    @DisplayName("Método Obtener Investigador por ID, cuando no existe")
    @Test
    void getInvestigatorByIdNotFound() {
        // Arrange
        when(investigatorRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            investigatorService.getInvestigatorById(1L);
        });

        assertEquals("Investigador no encontrado con el id: 1", exception.getMessage());
    }

    @DisplayName("Método Obtener Investigador por Cedula")
    @Test
    void getInvestigatorByLicenseNumberSuccess() {
        // Arrange
        String licenseNumber = "123456789";
        when(investigatorRepository.findByLicenseNumber(licenseNumber)).thenReturn(Optional.of(investigator));

        // Act
        Investigator foundInvestigator = investigatorService.getInvestigatorByLicenseNumber(licenseNumber);

        // Assert
        assertNotNull(foundInvestigator);
        assertEquals(licenseNumber, foundInvestigator.getLicenseNumber());
    }

    @DisplayName("Método Obtener Investigador por Cedula, cuando no existe")
    @Test
    void getInvestigatorByLicenseNumberNotFound() {
        // Arrange
        String licenseNumber = "nonexistent";
        when(investigatorRepository.findByLicenseNumber(licenseNumber)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            investigatorService.getInvestigatorByLicenseNumber(licenseNumber);
        });

        assertEquals("Investigador no encontrado con cédula: " + licenseNumber, exception.getMessage());
    }
}
