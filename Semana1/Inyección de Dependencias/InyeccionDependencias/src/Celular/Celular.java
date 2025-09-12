package Celular;

import Celular.Camara.Camara;
import Celular.Pantalla.Pantalla;

public class Celular {

    // Aquí se hace la Inyección de dependencias
    private Pantalla miPantalla;
    private Camara miCamara;

    public Celular(Pantalla pantallaUsada, Camara camaraUsada){
        System.out.println("Celular Ensamblado");
        this.miPantalla = pantallaUsada;
        this.miCamara = camaraUsada;
    }


    /* Aquí se hace el polimorfismo, la variable de referencia miPantalla puede apuntar
    * a cualquier objeto que implemente la interfaz Pantalla.
    * Sin importar si es de Samsung o de LG
    */
    public void encenderCelular(){
        System.out.println("Encendiendo celular...");
        this.miPantalla.encenderPantalla();
        System.out.println("Pantalla encendida");
        System.out.println("Probando Camara...");
        this.miCamara.tomarFoto();
        System.out.println("Foto tomada!");
    }
}
