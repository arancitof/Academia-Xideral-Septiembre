package com.Celular.Junit;

import com.Celular.Junit.Camara.Camara;
import com.Celular.Junit.Pantalla.Pantalla;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = JunitApplication.class)
public class JunitApplicationTests {

	/*Las pruebas unitarias son la forma de asegurarme que mis clases y métodos funcionan adecuadamente
	* En este caso si ensamblo celulares, debo asegurarme que funcionan o recibiré malos comentarios
	* y por ende no venderé lo establecido para eso...
	* */

    /*Primero destinamos un área de trabajo para mis pruebas*/
    /*Realmente no necesito saber si usa una pantalla en específico, sino que sea cual sea la pantalla
    * esta pueda encender
    * */
    class pantallaPrueba implements Pantalla{
        boolean encendida = false;

        @Override
        public void encenderPantalla() {
            this.encendida =true;
        }
    }
    /*Realmente no necesito saber si usa una camara en específico, sino que sea cual sea la camara
    * esta pueda tomar fotos
    * */

    class camaraPrueba implements Camara{
        boolean fotoTomada = false;

        @Override
        public void tomarFoto() {
            this.fotoTomada = true;
        }
    }

    /*Ya que tenemos lista nuestra área de trabajo ahora sí, analizamos que queremos probar*/
    @DisplayName("Prueba de encendido")
    @Test
    void testEncenderCelular(){
        /*Ahora tomamos nuestras herramientas del área de trabajo e instancia mos lo que vamos a ocupar*/
        pantallaPrueba miPantallaPrueba = new pantallaPrueba();
        camaraPrueba miCamaraPrueba =  new camaraPrueba();
        /*Aquí construimos nuestro celular pero con nuestro componente de prueba */
        Celular miCelular = new Celular(miPantallaPrueba, miCamaraPrueba);

        //a hora que queremos probar que el metodo funcione

        miCelular.encenderCelular();


        /*
        * Aquí finalmente verificamos lo que esperamos que suceda
        * */

        assertTrue(miPantallaPrueba.encendida, "La pantalla debería encender");
        assertTrue(miCamaraPrueba.fotoTomada,"La camara debería tomar una foto");



    }



}
