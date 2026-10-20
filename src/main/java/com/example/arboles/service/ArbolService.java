package com.example.arboles.service;

import com.example.arboles.structure.ArbolBinarioBusqueda;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Capa de servicio: guarda UN arbol en memoria mientras la app esta corriendo.
 * (Spring crea una sola instancia de este servicio, por eso el arbol se conserva
 * entre peticiones.)
 */
@Service
public class ArbolService {

    private final ArbolBinarioBusqueda arbol = new ArbolBinarioBusqueda();

    public boolean insertar(int valor) { return arbol.insertar(valor); }

    public boolean buscar(int valor) { return arbol.buscar(valor); }

    public boolean eliminar(int valor) { return arbol.eliminar(valor); }

    public void limpiar() { arbol.limpiar(); }

    public String dibujo() { return arbol.dibujar(); }

    public Map<String, Object> recorridos() {
        Map<String, Object> mapa = new LinkedHashMap<>();
        mapa.put("inorden", arbol.inorden());
        mapa.put("preorden", arbol.preorden());
        mapa.put("postorden", arbol.postorden());
        return mapa;
    }

    public Map<String, Object> info() {
        Map<String, Object> mapa = new LinkedHashMap<>();
        mapa.put("tamanio", arbol.getTamanio());
        mapa.put("altura", arbol.altura());
        mapa.put("minimo", arbol.minimo());
        mapa.put("maximo", arbol.maximo());
        return mapa;
    }
}
