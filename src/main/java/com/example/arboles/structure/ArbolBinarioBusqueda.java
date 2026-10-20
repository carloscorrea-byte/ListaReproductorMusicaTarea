package com.example.arboles.structure;

import com.example.arboles.model.Nodo;

import java.util.ArrayList;
import java.util.List;

/**
 * Arbol Binario de Busqueda (ABB).
 * Regla: para cada nodo, todo lo que esta a su izquierda es MENOR
 * y todo lo que esta a su derecha es MAYOR.
 * No permite valores repetidos.
 */
public class ArbolBinarioBusqueda {

    private Nodo raiz;
    private int tamanio;

    // ---------- INSERTAR ----------
    public boolean insertar(int valor) {
        if (buscar(valor)) {
            return false; // ya existe, no se repite
        }
        raiz = insertar(raiz, valor);
        tamanio++;
        return true;
    }

    private Nodo insertar(Nodo actual, int valor) {
        if (actual == null) {
            return new Nodo(valor); // encontramos el lugar vacio
        }
        if (valor < actual.getValor()) {
            actual.setIzquierdo(insertar(actual.getIzquierdo(), valor));
        } else {
            actual.setDerecho(insertar(actual.getDerecho(), valor));
        }
        return actual;
    }

    // ---------- BUSCAR ----------
    public boolean buscar(int valor) {
        Nodo actual = raiz;
        while (actual != null) {
            if (valor == actual.getValor()) {
                return true;
            }
            actual = (valor < actual.getValor()) ? actual.getIzquierdo() : actual.getDerecho();
        }
        return false;
    }

    // ---------- ELIMINAR ----------
    public boolean eliminar(int valor) {
        if (!buscar(valor)) {
            return false;
        }
        raiz = eliminar(raiz, valor);
        tamanio--;
        return true;
    }

    private Nodo eliminar(Nodo actual, int valor) {
        if (actual == null) {
            return null;
        }
        if (valor < actual.getValor()) {
            actual.setIzquierdo(eliminar(actual.getIzquierdo(), valor));
        } else if (valor > actual.getValor()) {
            actual.setDerecho(eliminar(actual.getDerecho(), valor));
        } else {
            // Caso 1 y 2: sin hijos o con un solo hijo
            if (actual.getIzquierdo() == null) return actual.getDerecho();
            if (actual.getDerecho() == null) return actual.getIzquierdo();

            // Caso 3: dos hijos -> lo reemplazamos por el menor del subarbol derecho
            Nodo sucesor = nodoMinimo(actual.getDerecho());
            actual.setValor(sucesor.getValor());
            actual.setDerecho(eliminar(actual.getDerecho(), sucesor.getValor()));
        }
        return actual;
    }

    // ---------- RECORRIDOS ----------
    /** Izquierda -> Raiz -> Derecha (en un ABB sale ordenado de menor a mayor). */
    public List<Integer> inorden() {
        List<Integer> resultado = new ArrayList<>();
        inorden(raiz, resultado);
        return resultado;
    }

    private void inorden(Nodo n, List<Integer> r) {
        if (n == null) return;
        inorden(n.getIzquierdo(), r);
        r.add(n.getValor());
        inorden(n.getDerecho(), r);
    }

    /** Raiz -> Izquierda -> Derecha. */
    public List<Integer> preorden() {
        List<Integer> resultado = new ArrayList<>();
        preorden(raiz, resultado);
        return resultado;
    }

    private void preorden(Nodo n, List<Integer> r) {
        if (n == null) return;
        r.add(n.getValor());
        preorden(n.getIzquierdo(), r);
        preorden(n.getDerecho(), r);
    }

    /** Izquierda -> Derecha -> Raiz. */
    public List<Integer> postorden() {
        List<Integer> resultado = new ArrayList<>();
        postorden(raiz, resultado);
        return resultado;
    }

    private void postorden(Nodo n, List<Integer> r) {
        if (n == null) return;
        postorden(n.getIzquierdo(), r);
        postorden(n.getDerecho(), r);
        r.add(n.getValor());
    }

    // ---------- INFORMACION ----------
    /** Altura: cantidad de niveles. Arbol vacio = 0, solo la raiz = 1. */
    public int altura() {
        return altura(raiz);
    }

    private int altura(Nodo n) {
        if (n == null) return 0;
        return 1 + Math.max(altura(n.getIzquierdo()), altura(n.getDerecho()));
    }

    public Integer minimo() {
        return raiz == null ? null : nodoMinimo(raiz).getValor();
    }

    public Integer maximo() {
        if (raiz == null) return null;
        Nodo actual = raiz;
        while (actual.getDerecho() != null) {
            actual = actual.getDerecho();
        }
        return actual.getValor();
    }

    private Nodo nodoMinimo(Nodo n) {
        while (n.getIzquierdo() != null) {
            n = n.getIzquierdo();
        }
        return n;
    }

    public int getTamanio() {
        return tamanio;
    }

    public boolean estaVacio() {
        return raiz == null;
    }

    public void limpiar() {
        raiz = null;
        tamanio = 0;
    }

    // ---------- DIBUJO EN TEXTO ----------
    /**
     * Dibuja el arbol "acostado": la raiz queda a la izquierda,
     * los hijos derechos arriba y los hijos izquierdos abajo.
     */
    public String dibujar() {
        if (raiz == null) return "(arbol vacio)";
        StringBuilder sb = new StringBuilder();
        dibujar(raiz, 0, sb);
        return sb.toString();
    }

    private void dibujar(Nodo n, int nivel, StringBuilder sb) {
        if (n == null) return;
        dibujar(n.getDerecho(), nivel + 1, sb);
        sb.append("    ".repeat(nivel)).append(n.getValor()).append("\n");
        dibujar(n.getIzquierdo(), nivel + 1, sb);
    }
}
