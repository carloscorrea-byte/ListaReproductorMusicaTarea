package com.example.grafos.service;

import com.example.grafos.model.Ciudad;
import com.example.grafos.model.Nodo;
import com.example.grafos.model.Persona;
import com.example.grafos.structure.Grafo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Reglas del reto sobre el grafo: personas, ciudades y amistades. */
@Service
public class GrafoService {

    private final Grafo grafo = new Grafo();

    // ---------- CIUDADES ----------
    public Ciudad crearCiudad(String nombre) {
        validarTexto(nombre, "El nombre de la ciudad es obligatorio");
        if (buscarCiudad(nombre) != null) {
            throw new IllegalArgumentException("La ciudad ya existe: " + nombre);
        }
        Ciudad ciudad = new Ciudad(nombre.trim());
        grafo.agregarNodo(ciudad);
        return ciudad;
    }

    public Ciudad buscarCiudad(String nombre) {
        if (nombre == null || nombre.isBlank()) return null;
        Nodo n = grafo.buscarNodo(Ciudad.idDe(nombre));
        return (n instanceof Ciudad c) ? c : null;
    }

    public List<Ciudad> ciudades() {
        List<Ciudad> lista = new ArrayList<>();
        for (Nodo n : grafo.getNodos()) {
            if (n instanceof Ciudad c) lista.add(c);
        }
        return lista;
    }

    // ---------- PERSONAS ----------
    /** Cada persona queda conectada (referenciada) a UNA ciudad. */
    public Persona crearPersona(String nombre, int edad, String nombreCiudad) {
        validarTexto(nombre, "El nombre de la persona es obligatorio");
        if (edad <= 0) {
            throw new IllegalArgumentException("La edad debe ser mayor que 0");
        }
        if (buscarPersona(nombre) != null) {
            throw new IllegalArgumentException("La persona ya existe: " + nombre);
        }
        Ciudad ciudad = buscarCiudad(nombreCiudad);
        if (ciudad == null) {
            throw new IllegalArgumentException("La ciudad no existe: " + nombreCiudad);
        }
        Persona persona = new Persona(nombre.trim(), edad);
        grafo.agregarNodo(persona);
        grafo.agregarArista(persona, ciudad);
        return persona;
    }

    public Persona buscarPersona(String nombre) {
        if (nombre == null || nombre.isBlank()) return null;
        Nodo n = grafo.buscarNodo(Persona.idDe(nombre));
        return (n instanceof Persona p) ? p : null;
    }

    /** Conecta a dos personas como amigas. */
    public void crearAmistad(String nombre1, String nombre2) {
        Persona a = buscarPersona(nombre1);
        Persona b = buscarPersona(nombre2);
        if (a == null || b == null) {
            throw new IllegalArgumentException("Las dos personas deben existir");
        }
        if (!grafo.agregarArista(a, b)) {
            throw new IllegalArgumentException("No se pudo crear la amistad (ya existe o es la misma persona)");
        }
    }

    // ---------- CONSULTAS ----------
    /** Lista de personas que viven en una ciudad = vecinos de la ciudad que son Persona. */
    public List<Persona> personasQueViven(String nombreCiudad) {
        Ciudad ciudad = buscarCiudad(nombreCiudad);
        if (ciudad == null) {
            throw new IllegalArgumentException("La ciudad no existe: " + nombreCiudad);
        }
        List<Persona> resultado = new ArrayList<>();
        for (Nodo vecino : grafo.adyacentesDe(ciudad.getId())) {
            if (vecino instanceof Persona p) resultado.add(p);
        }
        return resultado;
    }

    /** Todos los nodos conectados a una persona (su ciudad y sus amigos). */
    public List<Nodo> conexionesDe(String nombrePersona) {
        Persona persona = buscarPersona(nombrePersona);
        if (persona == null) {
            throw new IllegalArgumentException("La persona no existe: " + nombrePersona);
        }
        return grafo.adyacentesDe(persona.getId());
    }

    public String imprimir() {
        return grafo.imprimir();
    }

    /** Formato que necesita react-d3-graph: { nodes: [...], links: [{source, target}] }. */
    public Map<String, Object> paraVisualizar() {
        List<Map<String, String>> links = new ArrayList<>();
        for (Nodo a : grafo.getNodos()) {
            for (Nodo b : grafo.adyacentesDe(a.getId())) {
                // cada arista se agrega una sola vez
                if (a.getId().compareTo(b.getId()) < 0) {
                    links.add(Map.of("source", a.getId(), "target", b.getId()));
                }
            }
        }
        Map<String, Object> datos = new LinkedHashMap<>();
        datos.put("nodes", grafo.getNodos());
        datos.put("links", links);
        return datos;
    }

    private void validarTexto(String texto, String mensaje) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException(mensaje);
        }
    }
}
