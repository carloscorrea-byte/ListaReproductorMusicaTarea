package com.example.arboles.model;

/**
 * Un nodo del arbol. Guarda un valor y las referencias
 * a su hijo izquierdo y a su hijo derecho.
 */
public class Nodo {

    private int valor;
    private Nodo izquierdo;
    private Nodo derecho;

    public Nodo(int valor) {
        this.valor = valor;
    }

    public int getValor() { return valor; }
    public void setValor(int valor) { this.valor = valor; }

    public Nodo getIzquierdo() { return izquierdo; }
    public void setIzquierdo(Nodo izquierdo) { this.izquierdo = izquierdo; }

    public Nodo getDerecho() { return derecho; }
    public void setDerecho(Nodo derecho) { this.derecho = derecho; }
}
