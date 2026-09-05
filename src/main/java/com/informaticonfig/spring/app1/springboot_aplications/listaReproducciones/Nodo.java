package com.informaticonfig.spring.app1.springboot_aplications.listaReproducciones;

public class Nodo {

    String titulo;      // Título de la canción
    String artista;      // Nombre del artista
    int duracionSegundos; // Duración de la canción en segundos
    Nodo siguiente; // Si este nodo es el ÚLTIMO de la lista, "siguiente" será null.


    public Nodo(String titulo, String artista, int duracionSegundos) {
        this.titulo = titulo;
        this.artista = artista;
        this.duracionSegundos = duracionSegundos;
        this.siguiente = null; 
    }
}
