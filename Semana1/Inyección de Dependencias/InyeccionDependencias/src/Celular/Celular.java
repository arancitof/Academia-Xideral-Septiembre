package Celular;

import Celular.Pantalla.Pantalla;

public class Celular {

    // Aquí se hace la Inyección de dependencias
    private Pantalla miPantalla;

    public Celular(Pantalla pantallaUsada){
        System.out.println("Creando celular");
        this.miPantalla = pantallaUsada;
    }


    /* Aquí se hace el polimorfismo, la variable de referencia miPantalla puede apuntar
    * a cualquier objeto que implemente la interfaz Pantalla.
    * Sin importar si es de Samsung o de LG
    */
    public void encenderCelular(){
        System.out.println("Encendiendo celular");
        this.miPantalla.encenderPantalla();
        System.out.println("Celular listo para usar");
    }
}
