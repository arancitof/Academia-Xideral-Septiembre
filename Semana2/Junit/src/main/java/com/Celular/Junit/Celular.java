package com.Celular.Junit;

import com.Celular.Junit.Camara.Camara;
import com.Celular.Junit.Pantalla.Pantalla;

public class Celular {

    private Pantalla miPantalla;
    private Camara miCamara;

    public Celular(Pantalla pantallaUsada, Camara camaraUsada){
        System.out.println("Creando Celular");
        this.miPantalla = pantallaUsada;
        this.miCamara = camaraUsada;
    }

    public void encenderCelular(){
        System.out.println("Encendiendo Celular");
        this.miPantalla.encenderPantalla();
        this.miCamara.tomarFoto();
        System.out.println("Celular Encendido");
    }
}
