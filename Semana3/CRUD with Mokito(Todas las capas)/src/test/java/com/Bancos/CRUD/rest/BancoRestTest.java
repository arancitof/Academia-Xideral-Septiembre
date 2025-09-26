package com.Bancos.CRUD.rest;


import com.Bancos.CRUD.model.Banco;
import com.Bancos.CRUD.service.BancoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



//Esta anotación se usa para pruebas unitarias de la capa web
//Como tal no levanta el servidor completo
@WebMvcTest(BancoRest.class)
class BancoRestTest {

    //Banco Rest depende de Banco Service, será nuestro Mock
    @MockBean
    private BancoService bancoService;


    //MockMvc permite simular Peticiones HTTP, Inyectamos esta dependencia
    @Autowired
    private MockMvc mockMvc;

    //Generamos el espacio para el banco de prueba
    private Banco banco;

    //Con el ObjectMapper transformamos objetos a JSON, ideal para pruebas unitarias
    //Se utiliza para comparar salidas de controladores REST.
    @Autowired
    private ObjectMapper objectMapper;

    //Para cada prueba nos interesa Inciar los moks e inyectarlos
    @BeforeEach
    void setUp() {
        //Ahora, creamos un banco de prueba
        banco = new Banco("Banco Prueba", "banco@prueba.com", "123456789", "Atlantis");
        banco.setId("1");
    }

    @DisplayName("Metodo Create banco")
    @Test
    void testCreateBanco() throws Exception {
        //Recuerda cumplir el patron AAA (Arrange-Act-Assert)

        //-----Arrange-Preparación-----
        //Le decimos al Mock como actuar
        when(bancoService.save(any(Banco.class))).thenReturn(banco);

        //----Act----
        //hacemos una peticion post con mockMvc
        mockMvc.perform(post("/api/bancos")
                //Para indicar que el contenido tiene formato JSON
                .contentType(MediaType.APPLICATION_JSON)
                //Aqui se convierte el objeto banco a un formato JSON
                .content(objectMapper.writeValueAsString(banco)))
        //-------------- Assert-----------
                //Esperamos un código HTTP 201 (Created)
                .andExpect(status().isCreated())
                //Verificamos que el JSON tenga los datos correctos (ID)
                .andExpect(jsonPath(".id").value("1"))
                //Verificamos que el JSON tenga los datos correctos (Nombre)
                .andExpect(jsonPath("$.nombre").value("Banco Prueba"));

    }

    @DisplayName("Método para encontrar todos los bancos")
    @Test
    void testGetAllBancos() throws Exception {

        //--------Arrange
        //Para mayor visualización generamos otro banco de prueba
        Banco banco2 = new Banco("Banco Prueba2", "otro@banco.com","987654321", "Mesopotamia");
        banco2.setId("2");
        //Guardamos los datos en una Array
        List<Banco> bancos = Arrays.asList(banco,banco2);

        //Ahora le decimos al Mock Como actuar
        when(bancoService.findall()).thenReturn(bancos);

        //--------Act
        //hacemos una petición GET con mockMvc
        mockMvc.perform(get("/api/bancos"))
                //Esperamos un codigo HTTP 200 (OK)
                .andExpect(status().isOk())

        //--------------Assert----------------
                //El JSON debe tener los 2 bancos con sus valores respectivos
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nombre").value("Banco Prueba"))
                .andExpect(jsonPath("$[1].nombre").value("Banco Prueba2"));

    }

    @DisplayName("Método para encontrar un banco por su ID")
    @Test
    void testGetBancoById() throws Exception {
        //------Arrange----------
        //Le decimos al Mock como actuar
        when(bancoService.findById("1")).thenReturn(Optional.of(banco));

        //------Act--------------
        //hacemos una petición GET con mockMvc
        mockMvc.perform(get("/api/bancos/{id}", "1"))

        //------------Assert--------------
                //Esperamos un código HTTP 200 (OK)
                .andExpect(status().isOk())
                //El JSON debe tener los datos correctos
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.nombre").value("Banco Prueba"));
    }

    @DisplayName("Metodos para actualizar el banco")
    @Test
    void testUpdateBanco() throws Exception {
        //------Arrange---------
        //Le decimos al Mock como actuar

        Banco bancoActualizado = new Banco("Banco Actualizado", "update@banco.com", "0102030405","Marte");
        when(bancoService.update(anyString(), any(Banco.class))).thenReturn(bancoActualizado);

        //------Act--------------
        //hacemos una petición PUT con mockMvc
        mockMvc.perform(put("/api/bancos/{id}", "1")
                //Para indicar que el contenido tiene formato JSON
                .contentType(MediaType.APPLICATION_JSON)
                //Aquí se convierte el objeto bancoActualizado a un formato JSON
                .content(objectMapper.writeValueAsString(bancoActualizado)))

        //------------Assert--------------
                //Esperamos un código HTTP 200 (OK)
                .andExpect(status().isOk())
                //El JSON debe tener los datos correctos
                .andExpect(jsonPath("$.nombre").value("Banco Actualizado"))
                .andExpect(jsonPath("$.email").value("update@banco.com"));

    }

    @DisplayName("Metodo para eliminar un banco por su ID")
    @Test
    void testDeleteBanco() throws Exception {
        //------Arrange---------
        //Le decimos al Mock como actuar

        //Primero debes comprobar que el banco exista
        when(bancoService.findById("1")).thenReturn(Optional.of(banco));
        //Ahora como deleteById da void usamos doNothing
        doNothing().when(bancoService).deleteById("1");

        //------Act--------------
        //hacemos una petición DELETE con mockMvc
        mockMvc.perform(delete("/api/bancos/{id}", "1"))

        //------------Assert--------------
                //Esperamos un código HTTP 204 (No Content)
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteBanco_NotFound() throws Exception {
        // --- Given ---
        // Simulamos que el banco que se intenta borrar no existe.
        when(bancoService.findById("99")).thenReturn(Optional.empty());

        // --- When & Then ---
        mockMvc.perform(delete("/api/bancos/{id}", "99"))
                .andExpect(status().isNotFound()); // Esperamos un estado 404 (Not Found)
    }














}
