package com.ejemplo.cajero;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CajeroColasApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CajeroColasApplication.class, args);
    }

    @Override
    public void run(String... args) {
        SimuladorCajero simulador = new SimuladorCajero();
        simulador.ejecutar();
    }
}
