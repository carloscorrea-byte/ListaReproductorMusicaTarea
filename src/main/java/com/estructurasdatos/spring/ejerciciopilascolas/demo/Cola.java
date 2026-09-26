package com.estructurasdatos.spring.ejerciciopilascolas.demo;

import java.util.ArrayList;
import java.util.List;

public class Cola {

    private List<String> elementos = new ArrayList<>();

    public void encolar(String elemento) {
        elementos.add(elemento);
    }

    public String desencolar() {

        if (elementos.isEmpty()) {
            throw new RuntimeException("La cola está vacía");
        }

        return elementos.remove(0);
    }

    public String frente() {

        if (elementos.isEmpty()) {
            throw new RuntimeException("La cola está vacía");
        }

        return elementos.get(0);
    }

    public void mostrar() {
        System.out.println(elementos);
    }
}
