package com.example.grupo_04_tarea_12_ejercicio_01.data.mock;

import com.example.grupo_04_tarea_12_ejercicio_01.domain.Repository.AsientoRepository;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Asiento;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

/**
 * Implementación de repositorio simulado (Mock) para la entidad Asiento.
 * Implementa los métodos presentes en el contrato AsientoRepository.
 */
public class FakeAsientoRepositoryImpl implements AsientoRepository {

    private List<Asiento> asientos;
    private int nextId;

    @Inject
    public FakeAsientoRepositoryImpl() {
        // Cargar mock data desde MockDataProvider
        // Nota: Asegúrate de que MockDataProvider.getAsientos() existe y devuelve una List<Asiento>.
        this.asientos = new ArrayList<>(MockDataProvider.getAsientos());

        // Detectar el siguiente ID disponible
        this.nextId = asientos.stream()
                .mapToInt(Asiento::getIdAsiento)
                .max()
                .orElse(0) + 1;
    }

    // ─────────────────────────────
    // CRUD Básico
    // ─────────────────────────────

    @Override
    public void insert(Asiento asiento) {
        if (asiento.getIdAsiento() == 0) {
            asiento.setIdAsiento(nextId++);
        }
        asientos.add(asiento);
    }

    @Override
    public void update(Asiento asiento) {
        for (int i = 0; i < asientos.size(); i++) {
            if (asientos.get(i).getIdAsiento() == asiento.getIdAsiento()) {
                asientos.set(i, asiento);
                return;
            }
        }
    }

    @Override
    public void delete(Asiento asiento) {
        asientos.removeIf(a -> a.getIdAsiento() == asiento.getIdAsiento());
    }

    @Override
    public List<Asiento> getAll() {
        return new ArrayList<>(asientos);
    }

    @Override
    public Asiento getById(int id) {
        return asientos.stream()
                .filter(a -> a.getIdAsiento() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void deleteAll() {
        asientos.clear();
    }

    // ─────────────────────────────
    // MÉTODOS DE BÚSQUEDA DEL CONTRATO
    // ─────────────────────────────

    // 1. Obtener todos los asientos de un Vuelo
    @Override
    public List<Asiento> getByVuelo(int idVuelo) {
        return asientos.stream()
                .filter(a -> a.getIdVuelo() == idVuelo)
                .collect(Collectors.toList());
    }

    // 2. Obtener asientos por Reserva
    @Override
    public List<Asiento> getByReserva(int idReserva) {
        return asientos.stream()
                .filter(a -> a.getIdReserva() == idReserva)
                .collect(Collectors.toList());
    }

    // 3. Obtener asientos por Vuelo y Estado
    @Override
    public List<Asiento> getByVueloYEstado(int idVuelo, String estado) {
        return asientos.stream()
                .filter(a -> a.getIdVuelo() == idVuelo && a.getEstado().equalsIgnoreCase(estado))
                .collect(Collectors.toList());
    }

    // 4. Contar asientos disponibles
    @Override
    public int getAsientosDisponibles(int idVuelo) {
        // Asume que "Disponible" es el estado clave para contar los asientos libres.
        return (int) asientos.stream()
                .filter(a -> a.getIdVuelo() == idVuelo && a.getEstado().equalsIgnoreCase("Disponible"))
                .count();
    }
}