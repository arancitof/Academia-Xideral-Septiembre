package com.Celular.Junit.Pantalla;

public class PantallaSamsung implements Pantalla{
    public PantallaSamsung(){
        System.out.println("Usando Pantalla Samsung");
    }
    @Override
    public void encenderPantalla() {
        System.out.println("Encendiendo pantalla Samsung");
    }

}
