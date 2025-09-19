package com.Bancos.CRUD;


import com.Bancos.CRUD.model.Banco;
import com.Bancos.CRUD.repository.BancoRepository;
import com.Bancos.CRUD.service.BancoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class CrudApplicationTests {
    /*Las pruebas unitarias con mockito son una forma de realizar pruebas unitarias
    * donde simulas mediante un "muck" alguna característica antes de usar la real
    * Como ser un chef que quiere hacer una nueva receta y necesita ingredientes
    * o muy caros o muy difíciles sin la certeza de si esa receta funcionara...
    * entonces, mejor simular ese ingrediente, probar si combina en la receta y posteriormente conseguir
    * el ingrediente real
    * En ese sentido tendremos
    * ----Almacén donde se guardan los ingredientes ==> Repository----
    * ----Menu de los platillos ==> model
    * ----El chef encargado de la prueba, el que sabe toda la lógica ==> Service
    * */

    /*Antes de iniciar las pruebas unitarias hay que preparar los ingredientes*/

    //Es nuestro almacén, un ingrediente simulado, necesito un almacén simulado
    @Mock
    private BancoRepository bancoRepository;

    /*Aquí tenemos al chef, el que se encarga de darle vida a la receta (BancoServiceImpl)
    * y tenemos la descripción detallada de la receta (banco Service)*/
    /*Con @InyectMocks decimos que nuestro chef depende de nuestro almacén simulado para
    * preparar su nueva receta*/
    @InjectMocks
    private BancoServiceImpl bancoService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    //Armamos nuestra cocina y los ingredientes necesarios
    @DisplayName("Probando metodo de guardado")
    @Test
    void testSaveBanco(){
        //Creamos el nuevo ingrediente a probar
        Banco bancoDePrueba = new Banco();
        bancoDePrueba.setNombre("Banco Prueba");
        bancoDePrueba.setEmail("banco@prueba.com");
        bancoDePrueba.setTelefono("123456789");
        bancoDePrueba.setOrigen("Atlantis");

        //Definimos el resultado esperado
        Banco bancoGuardado = new Banco();
        bancoGuardado.setId("1");
        bancoGuardado.setNombre("Banco Prueba");
        bancoGuardado.setEmail("banco@prueba.com");
        bancoGuardado.setTelefono("123456789");
        bancoGuardado.setOrigen("Atlantis");
        bancoGuardado.setFechaCreacion(LocalDateTime.now());

        /*Le decimos al almacén cuando actuar
        * "Cuando alguien llame al metodo save, con cualquier objeto tipo banco, entonces
        * devuelve el banco guardado"*/
        when(bancoRepository.save(any(Banco.class))).thenReturn(bancoGuardado);

        /*Llamamos al chef en prueba*/
        Banco bancoResultante = bancoService.save(bancoDePrueba);

        /*Finalmete comprobamos*/
        //Que no sea null
        assertNotNull(bancoResultante);
        //Que se haya guardado con el id esperado
        assertEquals("1", bancoResultante.getId());
        //Verificamos que si tenga una fecha de creacion
        assertNotNull(bancoResultante.getFechaCreacion());
        //Verificamos que sea el nombre correcto
        assertEquals("Banco Prueba", bancoResultante.getNombre());
        //Verificamos que sea el email correcto
        assertEquals("banco@prueba.com", bancoResultante.getEmail());
        //Verificamos que sea el telefono correcto
        assertEquals("123456789", bancoResultante.getTelefono());
        //Verificamos que sea el origen correcto
        assertEquals("Atlantis", bancoResultante.getOrigen());
    }


    @DisplayName("Probando metodo para encontrar todos los bancos")
    @Test
    void testFindAllBancos() {
        // Creamos una lista de bancos que nuestro "almacén de imitación" devolverá.
        Banco banco1 = new Banco();
        banco1.setId("1");
        banco1.setNombre("Banco Uno");
        Banco banco2 = new Banco();
        banco2.setId("2");
        banco2.setNombre("Banco Dos");
        List<Banco> listaDeBancos = Arrays.asList(banco1, banco2);

        // Le decimos al almacen simulado como actuar
        // "Cuando te pidan encontrar todos los bancos, devuelve la lista que preparamos".
        when(bancoRepository.findAll()).thenReturn(listaDeBancos);


        // Llamamos al chef
        List<Banco> resultado = bancoService.findall();

        // Comprobamos que la lista no sea nula y que tenga el tamaño esperado.
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
    }

    @DisplayName("Probando metodo para encontrar un banco por su ID")
    @Test
    void testFindById() {
        Banco bancoEncontrado = new Banco();
        bancoEncontrado.setId("1");
        bancoEncontrado.setNombre("Banco Encontrado");


        // "Cuando te pidan buscar el banco con ID '1', devuelve este banco".
        // Usamos Optional.of() porque el método del repositorio devuelve un Optional.
        when(bancoRepository.findById("1")).thenReturn(Optional.of(bancoEncontrado));


        Optional<Banco> resultado = bancoService.findById("1");


        // Comprobamos que el resultado esté presente y que contenga el banco correcto.
        assertTrue(resultado.isPresent());
        assertEquals("Banco Encontrado", resultado.get().getNombre());
    }

    @DisplayName("Probando metodo para eliminar un banco por su ID")
    @Test
    void testDeleteById() {

        doNothing().when(bancoRepository).deleteById("1");


        bancoService.deleteById("1");


        verify(bancoRepository, times(1)).deleteById("1");
    }

    @DisplayName("Probando metodo para actualizar un banco")
    @Test
    void testUpdateBanco() {

        // 1. El banco que ya existe en la "base de datos".
        Banco bancoExistente = new Banco();
        bancoExistente.setId("1");
        bancoExistente.setNombre("Nombre Viejo");

        // 2. Los nuevos datos que queremos guardar.
        Banco datosNuevos = new Banco();
        datosNuevos.setNombre("Nombre Actualizado");
        datosNuevos.setEmail("email@nuevo.com");

        // 3. El resultado final que esperamos.
        Banco bancoActualizado = new Banco();
        bancoActualizado.setId("1");
        bancoActualizado.setNombre("Nombre Actualizado");
        bancoActualizado.setEmail("email@nuevo.com");

        // Instrucciones para el muck
        // - Cuando busquen el banco con ID "1", devuélvelo.
        when(bancoRepository.findById("1")).thenReturn(Optional.of(bancoExistente));
        // - Cuando guarden los cambios, devuelve el banco ya actualizado.
        when(bancoRepository.save(any(Banco.class))).thenReturn(bancoActualizado);

        // Actuar
        Banco resultado = bancoService.update("1", datosNuevos);

        // Verificar
        assertNotNull(resultado);
        assertEquals("1", resultado.getId());
        assertEquals("Nombre Actualizado", resultado.getNombre());
        assertEquals("email@nuevo.com", resultado.getEmail());
    }

    @DisplayName("Probando actualizar un banco que no existe")
    @Test
    void testUpdateBanco_NotFound() {

        // Le decimos al doble de acción que cuando busque el banco con ID "_no_existe_",
        // no encontrará nada (devuelve un Optional vacío).
        when(bancoRepository.findById("_no_existe_")).thenReturn(Optional.empty());


        // Aquí, esperamos que la acción de actualizar lance una excepción.
        // `assertThrows` es una forma elegante de decir: "Ejecuta este código, y espero que falle con este error".
        assertThrows(RuntimeException.class, () -> {
            bancoService.update("_no_existe_", new Banco());
        });
    }
}
