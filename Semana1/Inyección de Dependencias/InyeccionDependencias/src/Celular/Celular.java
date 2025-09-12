package Celular;

public class Celular {

    //Variable para guardar el espacio de la pantalla
    private Pantalla miPantalla;

    public Celular(Pantalla pantallaUsada){
        System.out.println("Creando celular");
        this.miPantalla = pantallaUsada;;
    }

    public void encenderCelular(){
        System.out.println("Encendiendo celular");
        this.miPantalla.encenderPantalla();
        System.out.println("Celular listo para usar");
    }
}
