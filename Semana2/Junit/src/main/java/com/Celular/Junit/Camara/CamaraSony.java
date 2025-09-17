package com.Celular.Junit.Camara;

public class CamaraSony implements Camara {

    public CamaraSony(){
        System.out.println("Usando Camara Sony");
    }

    @Override
    public void tomarFoto(){
        System.out.println("Tomando foto con camara Sony");
    }
}
