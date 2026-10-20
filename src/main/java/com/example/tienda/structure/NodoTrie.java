package com.example.tienda.structure;

import com.example.tienda.model.Producto;

import java.util.Map;
import java.util.TreeMap;

/**
 * Nodo del Trie: representa UNA letra del camino.
 * Los hijos se guardan en un TreeMap para recorrerlos en orden alfabetico.
 */
class NodoTrie {

    final Map<Character, NodoTrie> hijos = new TreeMap<>();
    boolean esFin;          // true si el camino hasta aqui forma un producto completo
    Producto producto;      // el producto que termina en este nodo (si esFin)
}
