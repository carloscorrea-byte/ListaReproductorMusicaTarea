package com.ejemplo.cajero;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Persona {
    private final String nombre;
    private final int montoRetiro;
    private final LocalDateTime fechaHoraLlegada;

    public Persona(String nombre, int montoRetiro, LocalDateTime fechaHoraLlegada) {
        this.nombre = nombre;
        this.montoRetiro = montoRetiro;
        this.fechaHoraLlegada = fechaHoraLlegada;
    }

    public String getNombre() {
        return nombre;
    }

    public int getMontoRetiro() {
        return montoRetiro;
    }

    public LocalDateTime getFechaHoraLlegada() {
        return fechaHoraLlegada;
    }

    @Override
    public String toString() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return String.format("Nombre: %-12s | Retiro: $%,d | Llegada: %s",
                nombre, montoRetiro, fechaHoraLlegada.format(formato));
    }
}
