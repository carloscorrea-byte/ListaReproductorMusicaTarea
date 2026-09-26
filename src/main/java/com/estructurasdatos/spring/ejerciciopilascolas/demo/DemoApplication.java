package com.estructurasdatos.spring.ejerciciopilascolas.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {

        SpringApplication.run(DemoApplication.class, args);

        System.out.println("=== EJERCICIO DE PILAS Y COLAS ===");

        // Aquí probaremos la Pila
        Pila pila = new Pila();

        pila.apilar(10);
        pila.apilar(20);
		pila.apilar(28);
        pila.apilar(38);

        System.out.println("\nPILA:");
        pila.mostrar();

        System.out.println("Elemento retirado: " + pila.desapilar());

        System.out.println("\nPila después de desapilar:");
        pila.mostrar();


        // Aquí probaremos la Cola
        Cola cola = new Cola();

        cola.encolar("La Iliada");
        cola.encolar("La Odisea");
        cola.encolar("El Quijote");
		cola.encolar("Cien Años de Soledad");
		cola.encolar("El Principito");

        System.out.println("\nCOLA:");
        cola.mostrar();

        System.out.println("Elemento retirado: " + cola.desencolar());

        System.out.println("\nCola después de desencolar:");
        cola.mostrar();
    }
}
