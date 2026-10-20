package com.example.grafos;

import com.example.grafos.service.GrafoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/** Al arrancar: carga datos de ejemplo y los imprime en la terminal. */
@Component
public class DatosIniciales implements CommandLineRunner {

    private final GrafoService servicio;

    public DatosIniciales(GrafoService servicio) {
        this.servicio = servicio;
    }

    @Override
    public void run(String... args) {
        servicio.crearCiudad("Cali");
        servicio.crearCiudad("Bogota");
        servicio.crearCiudad("Medellin");

        servicio.crearPersona("Ana", 21, "Cali");
        servicio.crearPersona("Luis", 25, "Cali");
        servicio.crearPersona("Juan", 26, "Cali");
        servicio.crearPersona("Marta", 30, "Bogota");
        servicio.crearPersona("Sofia", 23, "Bogota");
        servicio.crearPersona("Pedro", 28, "Medellin");

        servicio.crearAmistad("Ana", "Luis");
        servicio.crearAmistad("Ana", "Juan");
        servicio.crearAmistad("Luis", "Marta");
        servicio.crearAmistad("Pedro", "Sofia");

        System.out.println("\n===== GRAFO DE PERSONAS Y CIUDADES (lista de adyacencia) =====");
        System.out.print(servicio.imprimir());
        System.out.println("\nPersonas que viven en Cali:   " + servicio.personasQueViven("Cali"));
        System.out.println("Personas que viven en Bogota: " + servicio.personasQueViven("Bogota"));
        System.out.println("Conexiones de Ana:            " + servicio.conexionesDe("Ana"));
        System.out.println("==============================================================");
        System.out.println("API lista en http://localhost:8080/api/grafo\n");
    }
}
