package com.example.grupo_04_tarea_12_ejercicio_01.ui.booking.reservas;

import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Pasajero;

/**
 * Clase helper para mostrar información de pasajeros en la UI
 * No modifica las entidades originales
 */
public class PasajeroDisplayItem {
    private final Pasajero pasajero;
    private final String displayText;

    public PasajeroDisplayItem(Pasajero pasajero) {
        this.pasajero = pasajero;

        // Construir texto descriptivo
        String nombres = pasajero.getNombre() != null ? pasajero.getNombre() : "";
        String apellidos = pasajero.getApaterno() != null ? pasajero.getApaterno() : "";

        // Formato: "Juan Pérez (DNI: 12345678)"
        this.displayText = String.format("%s %s (DNI: %s)",
                nombres,
                apellidos,
                pasajero.getNumDocumento() != null ? pasajero.getNumDocumento() : "N/A"
        ).trim();
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public int getIdPasajero() {
        return pasajero.getIdPasajero();
    }

    public String getDisplayText() {
        return displayText;
    }

    @Override
    public String toString() {
        return displayText;
    }
}