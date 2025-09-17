package com.Celular.Junit;


import com.Celular.Junit.Camara.Camara;
import com.Celular.Junit.Camara.CamaraSamsung;
import com.Celular.Junit.Camara.CamaraSony;
import com.Celular.Junit.Pantalla.Pantalla;
import com.Celular.Junit.Pantalla.PantallaLg;
import com.Celular.Junit.Pantalla.PantallaSamsung;

public class JunitApplication {

	public static void main(String[] args) {

        System.out.println("Bienvenidos al ensambladror");

        Pantalla pantallaDeSamsung = new PantallaSamsung();
        Pantalla pantallaDeLg = new PantallaLg();
        Camara camaraDeSony = new CamaraSony();
        Camara camaraDeSamsung = new CamaraSamsung();

        Celular SamsungA50 = new Celular(pantallaDeSamsung, camaraDeSamsung);
        Celular OppoReno7 = new Celular(pantallaDeLg , camaraDeSony);

        SamsungA50.encenderCelular();
        System.out.println("Celular SamsungA50 listo para su venta");

        OppoReno7.encenderCelular();
        System.out.println("Celular OppoReno7 listo para su venta");

	}

}
