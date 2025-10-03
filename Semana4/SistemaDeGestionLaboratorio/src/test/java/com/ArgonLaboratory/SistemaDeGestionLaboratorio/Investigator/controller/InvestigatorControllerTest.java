package com.ArgonLaboratory.SistemaDeGestionLaboratorio.Investigator.controller;


import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Investigator.dto.InvestigatorRequest;
import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Investigator.model.Investigator;
import com.ArgonLaboratory.SistemaDeGestionLaboratorio.Investigator.service.InvestigatorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
public class InvestigatorControllerTest {

    //MockMvc permite simular Peticiones HTTP, Inyectamos esta dependencia
    @Autowired
    private MockMvc mockMvc;

    //Con el ObjectMapper transformamos objetos a JSON, ideal para pruebas unitarias
    //Se utiliza para comparar salidas de controladores REST.
    @Autowired
    private ObjectMapper objectMapper;

    //Nuestro Controlador depende del Service, este será nuestro Mock
    @MockBean
    private InvestigatorService investigatorService;

    //Generamos el espacio del test
    private Investigator investigatorTest;
    private InvestigatorRequest investigatorRequest;

    //Para cada prueba nos interesa iniciar los moks e inyectarlos
    @BeforeEach
    void setUp() {
        investigatorTest = new Investigator();
        investigatorTest.setId(1L);
        investigatorTest.setFirstname("Pepito");
        investigatorTest.setLastName("Fernandez");
        investigatorTest.setEmail("pepito.pruebas@gmail.com");
        investigatorTest.setLicenseNumber("12345678");
        investigatorTest.setSpecialization("BIOCHEMISTRY");
        investigatorTest.setPhone("5512345678");
        investigatorTest.setCreatedAt(LocalDateTime.now());

        investigatorRequest = new InvestigatorRequest();
        investigatorRequest.setFirstName("Pepito");
        investigatorRequest.setLastName("Fernandez");
        investigatorRequest.setEmail("juan.perez@example.com");
        investigatorRequest.setLicenseNumber("12345678");
        investigatorRequest.setSpecialization("BIOCHEMISTRY");
        investigatorRequest.setPhone("5512345678");
    }

    @DisplayName("Metodo Crear Investigador")
    @Test
    void testCreateInvestigator() throws Exception{
        //¡Siempre recuerda el patron AAA(Arrage-Act-Asser)!!!
        //Arrange
        //Le decimos al Mock como actuar
        when(investigatorService.createInvestigator(any(Investigator.class))).thenReturn(investigatorTest);

        //Act
        //Simulamos una petición con mockMVC
        mockMvc.perform(post("/investigators")
                        //Para indicar que el contenido tiene formato JSON
                .contentType(MediaType.APPLICATION_JSON)
                        //Aquí se convierte el objeto banco a un formato JSON
                .content(objectMapper.writeValueAsString(investigatorRequest)))

        //Assert
        //Esperamos un código HTTP 201 (Created)
                .andExpect(status().isCreated())
                .andExpect(jsonPath(".id").value(1))
                .andExpect(jsonPath("$.firstName").value("Pepito"))
                .andExpect(jsonPath("$.lastName").value("Fernandez"))
                .andExpect(jsonPath("$.email").value("pepito.pruebas@gmail.com"))
                .andExpect(jsonPath("$.licenseNumber").value("12345678"))
                .andExpect(jsonPath("$.specialization").value("BIOCHEMISTRY"))
                .andExpect(jsonPath("$.phone").value("5512345678"));
    }

    @DisplayName("Método para actualizar a un investigador")
    @Test
    void testUpdateInvestigator() throws Exception{
        //Arrage
        //Le decimos al mock como actuar
        when(investigatorService.updateInvestigator(eq(1L), any(Investigator.class))).thenReturn(investigatorTest);

        //Act
        //Simulamos la petición con mockMVC
        mockMvc.perform(put("/investigators/1")
                        //Para indicar que el contenido tiene formato JSON
                .contentType(MediaType.APPLICATION_JSON)
                        //Aquí se convierte el objeto banco a un formato JSON
                .content(objectMapper.writeValueAsString(investigatorRequest)))

        //Assert
        //Esperamos un código HTTP 200 (OK)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Pepito"));
    }

    @DisplayName("Método para encontrar un investigador por su ID")
    @Test
    void testGetInvestigatorById() throws Exception{

        //Arrage
        when(investigatorService.getInvestigatorById(1L)).thenReturn(investigatorTest);
        //Act
        //Hacemos la peticion GET con mockMVC
        mockMvc.perform(get("/investigators/1"))

                //Assert
        //Esperamos un código HTTP 200 (OK)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Pepito"));

        verify(investigatorService).getInvestigatorById(1L);
    }

    @DisplayName("Método para encontrar un investigador por su Cedula Profesional")
    @Test
    void testGetInvestigatorByLicenseNumber() throws Exception{

    }

    @DisplayName("Método para encontrar todos los Investigadores")
    @Test
    void testGetAllInvestigators() throws Exception{
        //Arrange
        //Necesitamos crear un nuevo investigador
        Investigator investigator2 = new Investigator();
        investigator2.setId(2L);
        investigator2.setFirstname("Juanito");
        investigator2.setLastName("Perez");
        investigator2.setEmail("juanito.pruebas@gmail.com");
        investigator2.setLicenseNumber("876648331");
        investigator2.setSpecialization("ORGANIC_CHEMISTRY");
        investigator2.setPhone("987364319");
        investigator2.setCreatedAt(LocalDateTime.now());

        List<Investigator> investigators = Arrays.asList(investigatorTest, investigator2);
        when(investigatorService.getAllInvestigators()).thenReturn(investigators);

        //Act
        //Simulamos la petición GET con mockMVC
        mockMvc.perform(get("/investigators"))
                //Assert
                //Esperamos un código HTTP 200 (OK)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName").value("Pepito"))
                .andExpect(jsonPath("$[0].lastName").value("Fernandez"))
                .andExpect(jsonPath("$[1].firstName").value("Juanito"))
                .andExpect(jsonPath("$[1].lastName").value("Perez"));



    }

    @DisplayName("Método para dar de bajo a un investigador")
    @Test
    void testDeleteInvestigator() throws Exception{
        //Arrange
        when(investigatorService.getInvestigatorById(1L)).thenReturn(investigatorTest);
        doNothing().when(investigatorService).deleteInvestigator(1L);

        //Act
        //Simulamos la petición DELETE con mockMVC
        mockMvc.perform(delete("/investigators/1"))
                //Assert
                .andExpect(status().isNoContent());

        verify(investigatorService).deleteInvestigator(1L);
    }
}
