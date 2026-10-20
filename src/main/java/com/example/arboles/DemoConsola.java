package com.example.arboles;

import com.example.arboles.service.ArbolService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Se ejecuta automaticamente al arrancar la app y muestra el arbol en la terminal.
 */
@Component
public class DemoConsola implements CommandLineRunner {

    private final ArbolService servicio;

    public DemoConsola(ArbolService servicio) {
        this.servicio = servicio;
    }

    @Override
    public void run(String... args) {
        int[] valores = {50, 30, 70, 20, 40, 60, 80};
        for (int v : valores) {
            servicio.insertar(v);
        }

        System.out.println("\n===== DEMO ARBOL BINARIO DE BUSQUEDA =====");
        System.out.println("Insertados en este orden: 50, 30, 70, 20, 40, 60, 80\n");
        System.out.println("Arbol (raiz a la izquierda, derechos arriba, izquierdos abajo):");
        System.out.println(servicio.dibujo());
        System.out.println("Recorridos: " + servicio.recorridos());
        System.out.println("Info: " + servicio.info());
        System.out.println("Buscar 40: " + servicio.buscar(40));
        System.out.println("Buscar 99: " + servicio.buscar(99));
        System.out.println("==========================================\n");
        System.out.println("API lista en http://localhost:8080/api/arbol/info\n");
    }
}
