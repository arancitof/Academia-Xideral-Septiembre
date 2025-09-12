package Celular;

public class Main {
    public static void main(String[] args) {

        /*
        * ============Bienvenidos a la planta de esamblaje de telefonos ==========
        *
        */

        //Primero traigo los componentes que voy a usar
        Pantalla pantallaDeSamsung = new PantallaSamsung();

        //Ahora Fabrico el celular con los componetes inyectados
        Celular SamsungA50 = new Celular(pantallaDeSamsung);

        //Ahora probamos que el celular funcione.

        SamsungA50.encenderCelular();
        System.out.println("Celular SamsungA50 listo para su venta");

    }
}
