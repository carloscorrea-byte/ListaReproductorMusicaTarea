package com.example.grafos.model;

/**
 * Un vertice (nodo) del grafo. Puede ser una Persona o una Ciudad.
 * El id es unico en todo el grafo (ej: "persona:ana" o "ciudad:cali").
 */
public abstract class Nodo {

    private final String id;
    private final String nombre;

    protected Nodo(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() { return id; }

    public String getNombre() { return nombre; }

    /** "PERSONA" o "CIUDAD". */
    public abstract String getTipo();

    @Override
    public String toString() {
        return nombre;
    }
}
