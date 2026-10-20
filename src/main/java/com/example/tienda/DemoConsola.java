package com.example.tienda;

import com.example.tienda.model.Producto;
import com.example.tienda.service.BuscadorProductos;
import com.example.tienda.structure.MaxHeap;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/** Se ejecuta al arrancar y muestra el Trie y el Heap funcionando en la consola. */
@Component
public class DemoConsola implements CommandLineRunner {

    private final BuscadorProductos buscador;

    public DemoConsola(BuscadorProductos buscador) {
        this.buscador = buscador;
    }

    @Override
    public void run(String... args) {
        // ---------- Cargar el catalogo ----------
        Object[][] catalogo = {
            {"Camiseta basica", 120}, {"Camisa formal", 95}, {"Camara web", 60},
            {"Casaca de cuero", 40}, {"Cargador USB", 150}, {"Celular Samsung", 210},
            {"Celular iPhone", 180}, {"Cinturon de cuero", 35}, {"Computador portatil", 75},
            {"Mouse inalambrico", 130}, {"Monitor 24 pulgadas", 85}, {"Mochila escolar", 110},
            {"Teclado mecanico", 90}, {"Audifonos bluetooth", 160}
        };
        for (Object[] fila : catalogo) {
            buscador.registrar((String) fila[0], (Integer) fila[1]);
        }

        System.out.println("\n===== TIENDA: TRIE + HEAP =====");
        System.out.println("Productos cargados: " + buscador.total());

        // ---------- TRIE: como queda el arbol ----------
        System.out.println("\n--- TRIE: subarbol del prefijo 'c' (tramos sin ramas se muestran juntos) ---");
        System.out.print(buscador.verArbolDesde("c"));

        // ---------- TRIE: busquedas ----------
        System.out.println("\n--- TRIE: busqueda exacta ---");
        System.out.println("Buscar 'Mouse inalambrico': " + buscador.buscar("Mouse inalambrico"));
        System.out.println("Buscar 'Mouse':             " + buscador.buscar("Mouse") + "   (es solo un prefijo, no un producto)");

        // ---------- TRIE + HEAP: autocompletado ----------
        System.out.println("\n--- AUTOCOMPLETADO: top 3 mas vendidos por prefijo ---");
        for (String prefijo : new String[]{"ca", "cel", "mo", "zzz"}) {
            mostrarSugerencias(prefijo, 3);
        }

        // ---------- Las ventas cambian y el ranking tambien ----------
        System.out.println("\n--- Se venden 150 unidades mas de 'Camisa formal' ---");
        buscador.registrarVenta("Camisa formal", 150);
        mostrarSugerencias("ca", 3);

        // ---------- Eliminar ----------
        System.out.println("\n--- Eliminar 'Celular Samsung' ---");
        System.out.println("Eliminado: " + buscador.eliminar("Celular Samsung"));
        System.out.println("Eliminar de nuevo: " + buscador.eliminar("Celular Samsung"));
        mostrarSugerencias("cel", 3);

        // ---------- HEAP solo: como funciona por dentro ----------
        System.out.println("\n--- HEAP: insertar y extraer (se muestra el arreglo interno) ---");
        MaxHeap heap = new MaxHeap();
        String[] nombres = {"A", "B", "C", "D", "E"};
        int[] ventas = {20, 50, 10, 70, 40};
        for (int i = 0; i < nombres.length; i++) {
            heap.insertar(new Producto(nombres[i], ventas[i]));
            System.out.println("Insertar " + nombres[i] + ":" + ventas[i] + "  ->  " + heap);
        }
        System.out.println("Maximo actual: " + heap.verMaximo());
        while (!heap.estaVacio()) {
            Producto sale = heap.extraerMaximo();
            System.out.println("Extraer maximo: " + sale + "  ->  " + heap);
        }
        System.out.println("===============================\n");
    }

    private void mostrarSugerencias(String prefijo, int k) {
        List<Producto> top = buscador.sugerencias(prefijo, k);
        System.out.println("Prefijo \"" + prefijo + "\":");
        if (top.isEmpty()) {
            System.out.println("   (sin resultados)");
            return;
        }
        for (int i = 0; i < top.size(); i++) {
            System.out.println("   " + (i + 1) + ". " + top.get(i));
        }
    }
}
