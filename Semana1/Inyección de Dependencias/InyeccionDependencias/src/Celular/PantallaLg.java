package Celular;

public class PantallaLg implements Pantalla{

    //Constructor
    public PantallaLg(){
        System.out.println("Usando pantalla LG");
    }

    //Cumpla con la interface

    @Override
    public void encenderPantalla(){
        System.out.println("Encendiendo pantalla LG");
    }
}
