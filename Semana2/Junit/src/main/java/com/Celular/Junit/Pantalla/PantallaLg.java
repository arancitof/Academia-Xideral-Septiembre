package com.Celular.Junit.Pantalla;

public class PantallaLg implements Pantalla {
    public PantallaLg(){
        System.out.println("Usando Pantalla LG");
    }
    @Override
    public void encenderPantalla() {
        System.out.println("Encendiendo pantalla LG");
    }

}
