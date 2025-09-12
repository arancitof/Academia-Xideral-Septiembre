package Celular.Pantalla;

public class PantallaSamsung implements Pantalla {

    //Constructor de la pantalla
    public PantallaSamsung(){
        System.out.println("Usando pantalla Samsung");
    }

    //La pantalla debe implementar lo de la interfaz

    @Override
    public void encenderPantalla() {
        System.out.println("Encendiendo pantalla Samsung");
    }

}
