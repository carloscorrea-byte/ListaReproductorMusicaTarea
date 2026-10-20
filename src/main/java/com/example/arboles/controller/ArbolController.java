package com.example.arboles.controller;

import com.example.arboles.service.ArbolService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/arbol")
public class ArbolController {

    private final ArbolService servicio;

    public ArbolController(ArbolService servicio) {
        this.servicio = servicio;
    }

    @PostMapping("/insertar/{valor}")
    public Map<String, Object> insertar(@PathVariable int valor) {
        boolean ok = servicio.insertar(valor);
        return respuesta(valor, ok, ok ? "Valor insertado" : "El valor ya existe, no se inserta");
    }

    @GetMapping("/buscar/{valor}")
    public Map<String, Object> buscar(@PathVariable int valor) {
        boolean existe = servicio.buscar(valor);
        return respuesta(valor, existe, existe ? "El valor SI esta en el arbol" : "El valor NO esta en el arbol");
    }

    @DeleteMapping("/{valor}")
    public Map<String, Object> eliminar(@PathVariable int valor) {
        boolean ok = servicio.eliminar(valor);
        return respuesta(valor, ok, ok ? "Valor eliminado" : "El valor no existe en el arbol");
    }

    @GetMapping("/recorridos")
    public Map<String, Object> recorridos() {
        return servicio.recorridos();
    }

    @GetMapping("/info")
    public Map<String, Object> info() {
        return servicio.info();
    }

    @GetMapping(value = "/dibujo", produces = MediaType.TEXT_PLAIN_VALUE)
    public String dibujo() {
        return servicio.dibujo();
    }

    @DeleteMapping("/limpiar")
    public Map<String, Object> limpiar() {
        servicio.limpiar();
        Map<String, Object> mapa = new LinkedHashMap<>();
        mapa.put("mensaje", "Arbol vaciado");
        return mapa;
    }

    private Map<String, Object> respuesta(int valor, boolean exito, String mensaje) {
        Map<String, Object> mapa = new LinkedHashMap<>();
        mapa.put("valor", valor);
        mapa.put("exito", exito);
        mapa.put("mensaje", mensaje);
        return mapa;
    }
}
