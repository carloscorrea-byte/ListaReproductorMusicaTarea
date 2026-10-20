package com.example.grafos.structure;

import com.example.grafos.model.Nodo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Grafo NO dirigido implementado con LISTA DE ADYACENCIA.
 * Como indica la clase, tiene 2 atributos:
 *   1) la lista de nodos
 *   2) un objeto de adyacencia: para cada nodo, la lista de nodos con los que esta conectado
 */
public class Grafo {

    private final List<Nodo> nodos = new ArrayList<>();
    private final Map<String, List<Nodo>> adyacencia = new LinkedHashMap<>();

    // ---------- AGREGAR ----------
    /** Agrega un nodo. Devuelve false si ya existe uno con ese id. */
    public boolean agregarNodo(Nodo nodo) {
        if (buscarNodo(nodo.getId()) != null) {
            return false;
        }
        nodos.add(nodo);
        adyacencia.put(nodo.getId(), new ArrayList<>());
        return true;
    }

    /** Agrega una arista entre dos nodos (en ambos sentidos). */
    public boolean agregarArista(Nodo a, Nodo b) {
        if (a == null || b == null || a.getId().equals(b.getId())) {
            return false;
        }
        List<Nodo> vecinosA = adyacencia.get(a.getId());
        List<Nodo> vecinosB = adyacencia.get(b.getId());
        if (vecinosA == null || vecinosB == null || estaConectado(a, b)) {
            return false;
        }
        vecinosA.add(b);
        vecinosB.add(a);
        return true;
    }

    // ---------- BUSCAR ----------
    /** Busca un nodo en la lista de nodos por su id. Devuelve null si no existe. */
    public Nodo buscarNodo(String id) {
        for (Nodo n : nodos) {
            if (n.getId().equals(id)) {
                return n;
            }
        }
        return null;
    }

    /** Busca en la lista de adyacencia: los nodos conectados a uno dado. */
    public List<Nodo> adyacentesDe(String id) {
        List<Nodo> vecinos = adyacencia.get(id);
        return vecinos == null ? Collections.emptyList() : Collections.unmodifiableList(vecinos);
    }

    public boolean estaConectado(Nodo a, Nodo b) {
        for (Nodo vecino : adyacentesDe(a.getId())) {
            if (vecino.getId().equals(b.getId())) {
                return true;
            }
        }
        return false;
    }

    public List<Nodo> getNodos() {
        return Collections.unmodifiableList(nodos);
    }

    // ---------- IMPRIMIR ----------
    /** Una linea por nodo: Nodo -> [vecinos]. */
    public String imprimir() {
        StringBuilder sb = new StringBuilder();
        for (Nodo n : nodos) {
            sb.append(n).append(" -> ").append(adyacentesDe(n.getId())).append("\n");
        }
        return sb.toString();
    }
}
