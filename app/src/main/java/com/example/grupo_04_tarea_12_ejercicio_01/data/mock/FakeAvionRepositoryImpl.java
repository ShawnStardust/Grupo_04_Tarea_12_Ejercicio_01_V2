package com.example.grupo_04_tarea_12_ejercicio_01.data.mock;

import com.example.grupo_04_tarea_12_ejercicio_01.domain.Repository.AvionRepository;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Avion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

/**
 * Implementación de repositorio simulado (Mock) para la entidad Avion.
 * Implementa los métodos presentes en el contrato AvionRepository, usando datos en memoria.
 */
public class FakeAvionRepositoryImpl implements AvionRepository {

    private List<Avion> aviones;
    private int nextId;

    @Inject
    public FakeAvionRepositoryImpl() {
        // Cargar mock data desde MockDataProvider
        // Nota: Asume que MockDataProvider.getAviones() existe y devuelve una List<Avion>.
        this.aviones = new ArrayList<>(MockDataProvider.getAviones());

        // Detectar el siguiente ID disponible (simulación de AUTOINCREMENT)
        this.nextId = aviones.stream()
                .mapToInt(Avion::getIdAvion)
                .max()
                .orElse(0) + 1;
    }

    // ─────────────────────────────
    // CRUD Básico
    // ─────────────────────────────

    @Override
    public void insert(Avion avion) {
        if (avion.getIdAvion() == 0) {
            avion.setIdAvion(nextId++);
        }
        aviones.add(avion);
    }

    @Override
    public void update(Avion avion) {
        for (int i = 0; i < aviones.size(); i++) {
            if (aviones.get(i).getIdAvion() == avion.getIdAvion()) {
                aviones.set(i, avion);
                return;
            }
        }
    }

    @Override
    public void delete(Avion avion) {
        aviones.removeIf(a -> a.getIdAvion() == avion.getIdAvion());
    }

    @Override
    public List<Avion> getAll() {
        return new ArrayList<>(aviones);
    }

    @Override
    public Avion getById(int id) {
        return aviones.stream()
                .filter(a -> a.getIdAvion() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void deleteAll() {
        aviones.clear();
    }

    // ─────────────────────────────
    // MÉTODOS DE BÚSQUEDA LÓGICA
    // ─────────────────────────────

    /**
     * Obtiene todos los aviones que pertenecen a una aerolínea específica.
     */
    @Override
    public List<Avion> getByAerolinea(int idAerolinea) {
        return aviones.stream()
                .filter(a -> a.getIdAerolinea() == idAerolinea)
                .collect(Collectors.toList());
    }

    @Override
    public List<Avion> getByTipo(String tipoAvion) {
        return Collections.emptyList();
    }

    @Override
    public List<Avion> getByCapacidadMinima(int capacidadMinima) {
        return Collections.emptyList();
    }
}