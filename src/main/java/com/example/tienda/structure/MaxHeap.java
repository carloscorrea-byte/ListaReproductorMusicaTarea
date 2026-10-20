package com.example.tienda.structure;

import com.example.tienda.model.Producto;

import java.util.ArrayList;
import java.util.List;

/**
 * Max-Heap (monticulo maximo) implementado sobre un arreglo.
 * La raiz (posicion 0) siempre es el producto con MAS ventas.
 * Para el nodo en la posicion i:  padre = (i-1)/2,  hijo izq = 2i+1,  hijo der = 2i+2.
 * Si dos productos tienen las mismas ventas, va primero el que va antes alfabeticamente.
 */
public class MaxHeap {

    private final List<Producto> datos = new ArrayList<>();

    public int tamanio() { return datos.size(); }

    public boolean estaVacio() { return datos.isEmpty(); }

    /** Mira el maximo sin sacarlo. */
    public Producto verMaximo() {
        return datos.isEmpty() ? null : datos.get(0);
    }

    // ---------- INSERTAR ----------
    public void insertar(Producto producto) {
        datos.add(producto);          // se agrega al final...
        subir(datos.size() - 1);      // ...y sube hasta su lugar
    }

    // ---------- EXTRAER EL MAXIMO ----------
    public Producto extraerMaximo() {
        if (datos.isEmpty()) {
            return null;
        }
        Producto maximo = datos.get(0);
        Producto ultimo = datos.remove(datos.size() - 1);
        if (!datos.isEmpty()) {
            datos.set(0, ultimo);     // el ultimo pasa a la raiz...
            bajar(0);                 // ...y baja hasta su lugar
        }
        return maximo;
    }

    private void subir(int i) {
        while (i > 0) {
            int padre = (i - 1) / 2;
            if (tienePrioridad(datos.get(i), datos.get(padre))) {
                intercambiar(i, padre);
                i = padre;
            } else {
                break;
            }
        }
    }

    private void bajar(int i) {
        int n = datos.size();
        while (true) {
            int izq = 2 * i + 1;
            int der = 2 * i + 2;
            int mayor = i;
            if (izq < n && tienePrioridad(datos.get(izq), datos.get(mayor))) mayor = izq;
            if (der < n && tienePrioridad(datos.get(der), datos.get(mayor))) mayor = der;
            if (mayor == i) {
                break;
            }
            intercambiar(i, mayor);
            i = mayor;
        }
    }

    private boolean tienePrioridad(Producto a, Producto b) {
        if (a.getVentas() != b.getVentas()) {
            return a.getVentas() > b.getVentas();
        }
        return a.getNombre().compareToIgnoreCase(b.getNombre()) < 0;
    }

    private void intercambiar(int i, int j) {
        Producto temp = datos.get(i);
        datos.set(i, datos.get(j));
        datos.set(j, temp);
    }

    /** El arreglo interno, para ver como queda el heap. */
    @Override
    public String toString() {
        List<String> partes = new ArrayList<>();
        for (Producto p : datos) {
            partes.add(p.getNombre() + ":" + p.getVentas());
        }
        return partes.toString();
    }
}
