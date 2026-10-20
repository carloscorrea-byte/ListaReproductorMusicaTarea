package com.example.menu.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Nodo de un arbol N-ARIO: un item del menu.
 * A diferencia del arbol binario, puede tener CUALQUIER cantidad de hijos (submenus).
 */
public class ItemMenu {

    private final String titulo;
    private final String link;
    private final String componente;
    private final List<ItemMenu> hijos = new ArrayList<>();

    public ItemMenu(String titulo, String link, String componente) {
        this.titulo = titulo;
        this.link = link;
        this.componente = componente;
    }

    public String getTitulo() { return titulo; }

    public String getLink() { return link; }

    public String getComponente() { return componente; }

    /** Lista de submenus (solo lectura). Para modificarla se usan agregarHijo / quitarHijo. */
    public List<ItemMenu> getHijos() {
        return Collections.unmodifiableList(hijos);
    }

    public void agregarHijo(ItemMenu hijo) {
        hijos.add(hijo);
    }

    public boolean quitarHijo(ItemMenu hijo) {
        return hijos.remove(hijo);
    }

    public boolean esHoja() {
        return hijos.isEmpty();
    }

    @Override
    public String toString() {
        return titulo + "  [link: " + link + " | componente: " + componente + "]";
    }
}
