package com.Celular.Junit.Camara;

public class CamaraSamsung implements Camara{

    public CamaraSamsung(){
    }

    @Override
    public void tomarFoto(){
        System.out.println("Tomando foto con camara Samsung");
    }


}
