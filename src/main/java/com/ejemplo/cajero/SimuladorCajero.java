package com.ejemplo.cajero;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class SimuladorCajero {
    private final Queue<Persona> cola = new LinkedList<>();
    private final Random random = new Random();

    private final String[] nombres = {
            "Ana", "Carlos", "Lucia", "Mateo", "Sofia",
            "Andres", "Valentina", "Juan", "Camila", "Daniel"
    };

    public void ejecutar() {
        System.out.println("\n==============================================");
        System.out.println("   SIMULADOR DE FILA - CAJERO AUTOMATICO");
        System.out.println("==============================================");

        generarPersonas(8);
        System.out.println("\nPersonas organizadas por fecha y hora de llegada:");
        mostrarCola();

        System.out.println("\n--- Atendiendo personas (FIFO) ---");
        atenderPersonas();

        System.out.println("\nPersonas pendientes en la cola: " + cola.size());
        System.out.println("Simulacion finalizada.");
    }

    private void generarPersonas(int cantidad) {
        LocalDateTime ahora = LocalDateTime.now();
        List<Persona> personas = new ArrayList<>();

        // Se generan horas aleatorias dentro de los 60 minutos anteriores.
        for (int i = 0; i < cantidad; i++) {
            String nombre = nombres[random.nextInt(nombres.length)] + (i + 1);
            int monto = (random.nextInt(18) + 1) * 50000; // $50.000 a $900.000
            int minutosAntes = random.nextInt(60);
            int segundos = random.nextInt(60);
            LocalDateTime llegada = ahora.minusMinutes(minutosAntes).minusSeconds(segundos);

            personas.add(new Persona(nombre, monto, llegada));
        }

        // Una cola atiende en orden de llegada. Por eso ordenamos primero
        // las llegadas generadas aleatoriamente y luego las encolamos.
        personas.sort(Comparator.comparing(Persona::getFechaHoraLlegada));
        for (Persona persona : personas) {
            cola.offer(persona); // offer agrega al final de la cola
        }
    }

    private void mostrarCola() {
        int posicion = 1;
        for (Persona persona : cola) {
            System.out.println(posicion + ". " + persona);
            posicion++;
        }
    }

    private void atenderPersonas() {
        while (!cola.isEmpty()) {
            Persona persona = cola.poll(); // poll retira la primera persona
            System.out.println("Atendiendo -> " + persona);
        }
    }
}
