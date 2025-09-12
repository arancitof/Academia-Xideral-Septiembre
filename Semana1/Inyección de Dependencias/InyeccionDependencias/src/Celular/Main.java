package Celular;

import Celular.Camara.Camara;
import Celular.Camara.CamaraSamsung;
import Celular.Camara.CamaraSony;
import Celular.Pantalla.Pantalla;
import Celular.Pantalla.PantallaLg;
import Celular.Pantalla.PantallaSamsung;

public class Main {
    public static void main(String[] args) {

        System.out.println("Bienbenidos al Ensamblador de Celulares");


        //Primero traigo los componentes que voy a usar
        Pantalla pantallaDeSamsung = new PantallaSamsung();
        Pantalla pantallaDeLg = new PantallaLg();
        Camara camaradeSony = new CamaraSony();
        Camara camaradeSamsung = new CamaraSamsung();


        //Ahora Fabrico el celular con los componetes inyectados
        Celular SamsungA50 = new Celular(pantallaDeSamsung,camaradeSony);
        Celular OppoReno7 = new Celular(pantallaDeLg,camaradeSamsung);


        //Ahora probamos que los celulares funcionen.
        SamsungA50.encenderCelular();
        System.out.println("Celular SamsungA50 listo para su venta");
        OppoReno7.encenderCelular();
        System.out.println("Celular OppoReno7 listo para su venta");

    }
}
