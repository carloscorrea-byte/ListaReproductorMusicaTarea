package com.example.tienda.service;

import com.example.tienda.model.Producto;
import com.example.tienda.structure.MaxHeap;
import com.example.tienda.structure.Trie;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Combina las dos estructuras:
 *   - el TRIE encuentra todos los productos que empiezan con lo que escribio el cliente
 *   - el HEAP ordena esos resultados y entrega los K mas vendidos
 */
@Service
public class BuscadorProductos {

    private final Trie trie = new Trie();

    public boolean registrar(String nombre, int ventas) {
        return trie.insertar(new Producto(nombre, ventas));
    }

    public Producto buscar(String nombre) {
        return trie.buscar(nombre);
    }

    public boolean eliminar(String nombre) {
        return trie.eliminar(nombre);
    }

    /** Suma ventas a un producto existente. */
    public boolean registrarVenta(String nombre, int cantidad) {
        Producto p = trie.buscar(nombre);
        if (p == null) {
            return false;
        }
        p.sumarVentas(cantidad);
        return true;
    }

    /** Las K sugerencias mas vendidas para un prefijo. */
    public List<Producto> sugerencias(String prefijo, int k) {
        List<Producto> coincidencias = trie.buscarPorPrefijo(prefijo);   // 1) Trie

        MaxHeap heap = new MaxHeap();                                    // 2) Heap
        for (Producto p : coincidencias) {
            heap.insertar(p);
        }

        List<Producto> top = new ArrayList<>();
        while (top.size() < k && !heap.estaVacio()) {
            top.add(heap.extraerMaximo());
        }
        return top;
    }

    public int total() {
        return trie.getTamanio();
    }

    public String verArbolDesde(String prefijo) {
        return trie.imprimirDesde(prefijo);
    }
}
