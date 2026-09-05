package com.informaticonfig.spring.app1.springboot_aplications.listaReproducciones;
import java.util.Scanner;

public class ListaReproduccion {

    private Nodo cabeza;

    private int totalCanciones;

    public ListaReproduccion() {
        this.cabeza = null;
        this.totalCanciones = 0;
    }

    public void agregarCancion(String titulo, String artista, int duracionSegundos) {
        // 1) Crear el nodo nuevo con los datos de la canción
        Nodo nodoNuevo = new Nodo(titulo, artista, duracionSegundos);

        if (cabeza == null) {
            cabeza = nodoNuevo;
        } else {
  
            Nodo actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente; // avanzamos al siguiente nodo
            }
            actual.siguiente = nodoNuevo; // enganchamos el nuevo nodo al final
        }

        totalCanciones++;
    }


    public void mostrarListaDeReproduccion() {
        System.out.println("===== LISTA DE REPRODUCCIÓN =====");

        if (cabeza == null) {
            System.out.println("(La lista está vacía, no hay canciones)");
            return;
        }

        Nodo actual = cabeza; 
        int posicion = 1;

        // Recorremos hasta que "actual" sea null (fin de la lista)
        while (actual != null) {
            int minutos = actual.duracionSegundos / 60;
            int segundos = actual.duracionSegundos % 60;

            System.out.printf(
                "%d. %s - %s (%d:%02d)%n",
                posicion, actual.titulo, actual.artista, minutos, segundos
            );

            actual = actual.siguiente; // avanzamos al siguiente nodo
            posicion++;
        }

        System.out.println("==================================");
        System.out.println("Total de canciones: " + totalCanciones);
    }

    /**
     * Reproductor interactivo por consola.
     * Comandos:
     *   n -> siguiente canción
     *   a -> anterior (retroceder)
     *   p -> play / pausa (alterna el estado)
     *   s -> salir del reproductor
     */
    public void reproducirInteractivo() {
        if (cabeza == null) {
            System.out.println("(La lista está vacía, no hay nada que reproducir)");
            return;
        }

        Scanner scanner = new Scanner(System.in);

       
        Nodo actual = cabeza;
        int posicion = 1; 

        // Estado del reproductor: true = sonando, false = en pausa
        boolean reproduciendo = true;

        imprimirEstado(actual, posicion, reproduciendo);

        while (true) {
            System.out.println("\nComandos: [n] siguiente | [a] anterior | [p] play/pausa | [s] salir");
            System.out.print("> ");
            String comando = scanner.nextLine().trim().toLowerCase();

            switch (comando) {
                case "n":
      
                    if (actual.siguiente != null) {
                        actual = actual.siguiente;
                        posicion++;
                        reproduciendo = true; 
                    } else {
                        System.out.println("⚠ Ya estás en la última canción de la lista.");
                    }
                    imprimirEstado(actual, posicion, reproduciendo);
                    break;

                case "a":

                    if (posicion > 1) {
                        posicion--;
                        actual = obtenerNodoEnPosicion(posicion);
                        reproduciendo = true;
                    } else {
                        System.out.println("⚠ Ya estás en la primera canción de la lista.");
                    }
                    imprimirEstado(actual, posicion, reproduciendo);
                    break;

                case "p":
                
                    reproduciendo = !reproduciendo;
                    imprimirEstado(actual, posicion, reproduciendo);
                    break;

                case "s":
                    System.out.println("Saliendo del reproductor. ¡Hasta luego!");
                    return;

                default:
                    System.out.println("Comando no reconocido. Usa n, a, p o s.");
            }
        }
    }


    private Nodo obtenerNodoEnPosicion(int posicion) {
        Nodo actual = cabeza;
        int contador = 1;
        while (contador < posicion) {
            actual = actual.siguiente;
            contador++;
        }
        return actual;
    }

    private void imprimirEstado(Nodo nodo, int posicion, boolean reproduciendo) {
        String estado = reproduciendo ? "▶ REPRODUCIENDO" : "⏸ PAUSADO";
        System.out.println(estado + ": (" + posicion + ") " + nodo.titulo + " - " + nodo.artista);
    }
}
