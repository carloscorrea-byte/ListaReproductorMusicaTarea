package com.example.grafos.model;

/** Nodo que representa una persona. Tiene nombre y edad. */
public class Persona extends Nodo {

    private final int edad;

    public Persona(String nombre, int edad) {
        super(idDe(nombre), nombre);
        this.edad = edad;
    }

    public static String idDe(String nombre) {
        return "persona:" + nombre.trim().toLowerCase();
    }

    public int getEdad() { return edad; }

    @Override
    public String getTipo() {
        return "PERSONA";
    }

    @Override
    public String toString() {
        return getNombre() + " (" + edad + ")";
    }
}
