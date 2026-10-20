package com.example.grafos.model;

/** Nodo que representa una ciudad. Solo tiene nombre. */
public class Ciudad extends Nodo {

    public Ciudad(String nombre) {
        super(idDe(nombre), nombre);
    }

    public static String idDe(String nombre) {
        return "ciudad:" + nombre.trim().toLowerCase();
    }

    @Override
    public String getTipo() {
        return "CIUDAD";
    }
}
