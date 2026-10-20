package com.example.tienda.model;

/** Un producto de la tienda con su numero de ventas (la "prioridad" para el Heap). */
public class Producto {

    private final String nombre;
    private int ventas;

    public Producto(String nombre, int ventas) {
        this.nombre = nombre;
        this.ventas = ventas;
    }

    public String getNombre() { return nombre; }

    public int getVentas() { return ventas; }

    public void sumarVentas(int cantidad) {
        this.ventas += cantidad;
    }

    @Override
    public String toString() {
        return nombre + " (" + ventas + " ventas)";
    }
}
