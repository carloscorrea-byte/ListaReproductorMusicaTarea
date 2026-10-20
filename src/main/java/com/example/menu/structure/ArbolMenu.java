package com.example.menu.structure;

import com.example.menu.model.ItemMenu;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Arbol N-ario de items de menu.
 * La raiz es el menu principal; cada item puede tener N submenus.
 * Los titulos no se repiten (se comparan sin importar mayusculas).
 */
public class ArbolMenu {

    private final ItemMenu raiz;

    public ArbolMenu(ItemMenu raiz) {
        this.raiz = raiz;
    }

    public ItemMenu getRaiz() {
        return raiz;
    }

    // ---------- AGREGAR ----------
    /** Agrega "nuevo" como submenu del item cuyo titulo es tituloPadre. */
    public boolean agregar(String tituloPadre, ItemMenu nuevo) {
        ItemMenu padre = buscar(tituloPadre);
        if (padre == null || buscar(nuevo.getTitulo()) != null) {
            return false; // el padre no existe o el titulo ya esta usado
        }
        padre.agregarHijo(nuevo);
        return true;
    }

    // ---------- BUSCAR ----------
    /** Busca un item por titulo recorriendo el arbol en profundidad. */
    public ItemMenu buscar(String titulo) {
        return buscar(raiz, titulo);
    }

    private ItemMenu buscar(ItemMenu actual, String titulo) {
        if (actual.getTitulo().equalsIgnoreCase(titulo)) {
            return actual;
        }
        for (ItemMenu hijo : actual.getHijos()) {
            ItemMenu encontrado = buscar(hijo, titulo);
            if (encontrado != null) {
                return encontrado;
            }
        }
        return null;
    }

    // ---------- ELIMINAR ----------
    /** Elimina un item y TODOS sus submenus. La raiz no se puede eliminar. */
    public boolean eliminar(String titulo) {
        if (raiz.getTitulo().equalsIgnoreCase(titulo)) {
            return false;
        }
        ItemMenu padre = buscarPadre(raiz, titulo);
        if (padre == null) {
            return false;
        }
        return padre.quitarHijo(buscar(titulo));
    }

    private ItemMenu buscarPadre(ItemMenu actual, String titulo) {
        for (ItemMenu hijo : actual.getHijos()) {
            if (hijo.getTitulo().equalsIgnoreCase(titulo)) {
                return actual;
            }
            ItemMenu padre = buscarPadre(hijo, titulo);
            if (padre != null) {
                return padre;
            }
        }
        return null;
    }

    // ---------- IMPRIMIR (recorrido en preorden con sangria) ----------
    public String imprimir() {
        StringBuilder sb = new StringBuilder();
        sb.append(raiz).append("\n");
        List<ItemMenu> hijos = raiz.getHijos();
        for (int i = 0; i < hijos.size(); i++) {
            imprimir(hijos.get(i), "", i == hijos.size() - 1, sb);
        }
        return sb.toString();
    }

    private void imprimir(ItemMenu item, String prefijo, boolean esUltimo, StringBuilder sb) {
        sb.append(prefijo).append(esUltimo ? "`-- " : "|-- ").append(item).append("\n");
        String nuevoPrefijo = prefijo + (esUltimo ? "    " : "|   ");
        List<ItemMenu> hijos = item.getHijos();
        for (int i = 0; i < hijos.size(); i++) {
            imprimir(hijos.get(i), nuevoPrefijo, i == hijos.size() - 1, sb);
        }
    }

    // ---------- RECORRIDO POR NIVELES (usa una COLA) ----------
    public List<String> porNiveles() {
        List<String> resultado = new ArrayList<>();
        Queue<ItemMenu> cola = new LinkedList<>();
        cola.add(raiz);
        while (!cola.isEmpty()) {
            ItemMenu actual = cola.poll();
            resultado.add(actual.getTitulo());
            cola.addAll(actual.getHijos());
        }
        return resultado;
    }

    // ---------- INFORMACION ----------
    public int contar() {
        return contar(raiz);
    }

    private int contar(ItemMenu n) {
        int total = 1;
        for (ItemMenu hijo : n.getHijos()) {
            total += contar(hijo);
        }
        return total;
    }

    /** Altura: cantidad de niveles. Solo la raiz = 1. */
    public int altura() {
        return altura(raiz);
    }

    private int altura(ItemMenu n) {
        int maxHijos = 0;
        for (ItemMenu hijo : n.getHijos()) {
            maxHijos = Math.max(maxHijos, altura(hijo));
        }
        return 1 + maxHijos;
    }

    /** Camino desde la raiz hasta el item, ej: "Menu > Productos > Hogar". Null si no existe. */
    public String ruta(String titulo) {
        List<String> camino = new ArrayList<>();
        if (!ruta(raiz, titulo, camino)) {
            return null;
        }
        return String.join(" > ", camino);
    }

    private boolean ruta(ItemMenu actual, String titulo, List<String> camino) {
        camino.add(actual.getTitulo());
        if (actual.getTitulo().equalsIgnoreCase(titulo)) {
            return true;
        }
        for (ItemMenu hijo : actual.getHijos()) {
            if (ruta(hijo, titulo, camino)) {
                return true;
            }
        }
        camino.remove(camino.size() - 1); // por aqui no era: devolvemos un paso
        return false;
    }
}
