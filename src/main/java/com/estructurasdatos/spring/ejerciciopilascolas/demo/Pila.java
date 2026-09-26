package com.estructurasdatos.spring.ejerciciopilascolas.demo;

import java.util.ArrayList;
import java.util.List;

public class Pila {

    private List<Integer> elementos = new ArrayList<>();

    public void apilar(int elemento) {
        elementos.add(elemento);
    }

    public int desapilar() {

        if (elementos.isEmpty()) {
            throw new RuntimeException("La pila está vacía");
        }

        return elementos.remove(elementos.size() - 1);
    }

    public int cima() {

        if (elementos.isEmpty()) {
            throw new RuntimeException("La pila está vacía");
        }

        return elementos.get(elementos.size() - 1);
    }

    public void mostrar() {
        System.out.println(elementos);
    }
}
