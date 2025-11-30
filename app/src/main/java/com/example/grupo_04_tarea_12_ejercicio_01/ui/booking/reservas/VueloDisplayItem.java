package com.example.grupo_04_tarea_12_ejercicio_01.ui.booking.reservas;

import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Aeropuerto;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Vuelo;

/**
 * Clase helper para mostrar información de vuelos en la UI
 * No modifica las entidades originales
 */
public class VueloDisplayItem {
    private final Vuelo vuelo;
    private final String displayText;

    public VueloDisplayItem(Vuelo vuelo, Aeropuerto origen, Aeropuerto destino) {
        this.vuelo = vuelo;

        // Construir texto descriptivo
        String nombreOrigen = origen != null ? origen.getNombre() : "Origen desconocido";
        String nombreDestino = destino != null ? destino.getNombre() : "Destino desconocido";

        // Formato: "Lima → Buenos Aires (ID: 1)"
        this.displayText = String.format("%s → %s (ID: %d)",
                nombreOrigen,
                nombreDestino,
                vuelo.getIdVuelo()
        );
    }

    public Vuelo getVuelo() {
        return vuelo;
    }

    public int getIdVuelo() {
        return vuelo.getIdVuelo();
    }

    public String getDisplayText() {
        return displayText;
    }

    @Override
    public String toString() {
        return displayText;
    }
}