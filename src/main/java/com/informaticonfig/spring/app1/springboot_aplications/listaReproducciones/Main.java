
package com.informaticonfig.spring.app1.springboot_aplications.listaReproducciones;

public class Main {
    public static void main(String[] args) {

        // 1) Crear la lista de reproducción (arranca vacía)
        ListaReproduccion miLista = new ListaReproduccion();

        // 2) datos simulados
        miLista.agregarCancion("Bohemian Rhapsody", "Queen", 355);
        miLista.agregarCancion("Blinding Lights", "The Weeknd", 200);
        miLista.agregarCancion("Ojos Así", "Shakira", 259);
        miLista.agregarCancion("Uptown Funk", "Bruno Mars", 270);
        miLista.agregarCancion("La Bicicleta", "Carlos Vives, Shakira", 231);

        // 3) Mostramos la lista completa recorriendo los nodos
        miLista.mostrarListaDeReproduccion();

        // 4) Iniciar el reproductor interactivo (comandos n / a / p / s)
        System.out.println();
        miLista.reproducirInteractivo();
    }
}
