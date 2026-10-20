package com.example.tienda.structure;

import com.example.tienda.model.Producto;

import java.util.ArrayList;
import java.util.List;

/**
 * Trie (arbol de prefijos). Cada nivel del arbol es una letra del nombre.
 * Todos los productos que empiezan igual comparten el mismo camino inicial,
 * por eso buscar por prefijo es rapido: se baja por el prefijo y se recogen
 * los productos que cuelgan de ahi.
 * Las claves se normalizan a minusculas (no distingue mayusculas).
 */
public class Trie {

    private final NodoTrie raiz = new NodoTrie();
    private int tamanio;

    private String normalizar(String texto) {
        return texto.trim().toLowerCase();
    }

    // ---------- INSERTAR ----------
    /** Inserta un producto. Devuelve false si ya existe uno con ese nombre. */
    public boolean insertar(Producto producto) {
        String clave = normalizar(producto.getNombre());
        NodoTrie actual = raiz;
        for (char c : clave.toCharArray()) {
            actual = actual.hijos.computeIfAbsent(c, k -> new NodoTrie());
        }
        if (actual.esFin) {
            return false;
        }
        actual.esFin = true;
        actual.producto = producto;
        tamanio++;
        return true;
    }

    // ---------- BUSCAR (nombre exacto) ----------
    public Producto buscar(String nombre) {
        NodoTrie nodo = nodoDe(normalizar(nombre));
        return (nodo != null && nodo.esFin) ? nodo.producto : null;
    }

    // ---------- BUSCAR POR PREFIJO ----------
    /** Todos los productos cuyo nombre empieza por el prefijo. */
    public List<Producto> buscarPorPrefijo(String prefijo) {
        List<Producto> resultado = new ArrayList<>();
        NodoTrie nodo = nodoDe(normalizar(prefijo));
        if (nodo != null) {
            recolectar(nodo, resultado);
        }
        return resultado;
    }

    private void recolectar(NodoTrie nodo, List<Producto> resultado) {
        if (nodo.esFin) {
            resultado.add(nodo.producto);
        }
        for (NodoTrie hijo : nodo.hijos.values()) {
            recolectar(hijo, resultado);
        }
    }

    /** Baja por el arbol siguiendo las letras de la clave. Null si el camino no existe. */
    private NodoTrie nodoDe(String clave) {
        NodoTrie actual = raiz;
        for (char c : clave.toCharArray()) {
            actual = actual.hijos.get(c);
            if (actual == null) {
                return null;
            }
        }
        return actual;
    }

    // ---------- ELIMINAR ----------
    public boolean eliminar(String nombre) {
        String clave = normalizar(nombre);
        NodoTrie nodo = nodoDe(clave);
        if (nodo == null || !nodo.esFin) {
            return false;
        }
        eliminar(raiz, clave, 0);
        tamanio--;
        return true;
    }

    /** Devuelve true si este nodo quedo inutil (sin hijos y sin producto) y se puede borrar. */
    private boolean eliminar(NodoTrie actual, String clave, int i) {
        if (i == clave.length()) {
            actual.esFin = false;
            actual.producto = null;
            return actual.hijos.isEmpty();
        }
        char c = clave.charAt(i);
        NodoTrie hijo = actual.hijos.get(c);
        if (eliminar(hijo, clave, i + 1)) {
            actual.hijos.remove(c); // poda: quita ramas que ya no sirven
        }
        return !actual.esFin && actual.hijos.isEmpty();
    }

    public int getTamanio() {
        return tamanio;
    }

    // ---------- VISUALIZACION ----------
    /**
     * Dibuja el subarbol que cuelga de un prefijo. Para que sea legible, los tramos
     * sin ramificacion se muestran juntos (el Trie real guarda UNA letra por nodo).
     */
    public String imprimirDesde(String prefijo) {
        String clave = normalizar(prefijo);
        NodoTrie nodo = nodoDe(clave);
        if (nodo == null) {
            return "(no hay productos con el prefijo '" + prefijo + "')\n";
        }
        StringBuilder sb = new StringBuilder();
        imprimir(nodo, clave, "", true, true, sb);
        return sb.toString();
    }

    private void imprimir(NodoTrie nodo, String etiqueta, String prefijo,
                          boolean esUltimo, boolean esRaiz, StringBuilder sb) {
        // juntar el tramo mientras no haya ramificacion ni producto
        while (nodo.hijos.size() == 1 && !nodo.esFin) {
            var unico = nodo.hijos.entrySet().iterator().next();
            etiqueta += unico.getKey();
            nodo = unico.getValue();
        }
        if (esRaiz) {
            sb.append(etiqueta);
        } else {
            sb.append(prefijo).append(esUltimo ? "`-- " : "|-- ").append(etiqueta);
        }
        if (nodo.esFin) {
            sb.append("  -> ").append(nodo.producto);
        }
        sb.append("\n");

        String nuevoPrefijo = esRaiz ? "" : prefijo + (esUltimo ? "    " : "|   ");
        int i = 0;
        for (var entrada : nodo.hijos.entrySet()) {
            boolean ultimo = (++i == nodo.hijos.size());
            imprimir(entrada.getValue(), String.valueOf(entrada.getKey()), nuevoPrefijo, ultimo, false, sb);
        }
    }
}
