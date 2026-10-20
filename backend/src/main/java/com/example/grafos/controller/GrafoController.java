package com.example.grafos.controller;

import com.example.grafos.model.Ciudad;
import com.example.grafos.model.Nodo;
import com.example.grafos.model.Persona;
import com.example.grafos.service.GrafoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class GrafoController {

    // Cuerpos de las peticiones POST
    public record CiudadRequest(String nombre) {}
    public record PersonaRequest(String nombre, int edad, String ciudad) {}
    public record AmistadRequest(String persona1, String persona2) {}

    private final GrafoService servicio;

    public GrafoController(GrafoService servicio) {
        this.servicio = servicio;
    }

    /** Datos para dibujar el grafo con react-d3-graph. */
    @GetMapping("/grafo")
    public Map<String, Object> grafo() {
        return servicio.paraVisualizar();
    }

    @GetMapping("/ciudades")
    public List<Ciudad> ciudades() {
        return servicio.ciudades();
    }

    @PostMapping("/ciudades")
    @ResponseStatus(HttpStatus.CREATED)
    public Ciudad crearCiudad(@RequestBody CiudadRequest body) {
        return servicio.crearCiudad(body.nombre());
    }

    /** Personas que viven en una ciudad. */
    @GetMapping("/ciudades/{nombre}/personas")
    public List<Persona> personasDeCiudad(@PathVariable String nombre) {
        return servicio.personasQueViven(nombre);
    }

    @PostMapping("/personas")
    @ResponseStatus(HttpStatus.CREATED)
    public Persona crearPersona(@RequestBody PersonaRequest body) {
        return servicio.crearPersona(body.nombre(), body.edad(), body.ciudad());
    }

    /** Nodos conectados a una persona (su ciudad y sus amigos). */
    @GetMapping("/personas/{nombre}/conexiones")
    public List<Nodo> conexiones(@PathVariable String nombre) {
        return servicio.conexionesDe(nombre);
    }

    @PostMapping("/amistades")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> crearAmistad(@RequestBody AmistadRequest body) {
        servicio.crearAmistad(body.persona1(), body.persona2());
        return Map.of("mensaje", body.persona1() + " y " + body.persona2() + " ahora son amigos");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> manejarError(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }
}
