package com.example.grupo_04_tarea_12_ejercicio_01.data.mock;

import com.example.grupo_04_tarea_12_ejercicio_01.domain.Repository.PaisRepository;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Pais;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

/**
 * Mock repository implementation for the Pais entity.
 * Provides data operations (CRUD, search) using an in-memory list (MockDataProvider).
 */
public class FakePaisRepositoryImpl implements PaisRepository {

    private List<Pais> paises;
    private int nextId;

    @Inject
    public FakePaisRepositoryImpl() {
        // Load mock data from MockDataProvider (assuming getPaises exists)
        this.paises = new ArrayList<>(MockDataProvider.getPaises());

        // Determine the next available ID for simulation of AUTOINCREMENT
        this.nextId = paises.stream()
                .mapToInt(Pais::getIdPais)
                .max()
                .orElse(0) + 1;
    }

    // ─────────────────────────────
    // CRUD
    // ─────────────────────────────

    @Override
    public void insert(Pais pais) {
        if (pais.getIdPais() == 0) {
            pais.setIdPais(nextId++);
        }
        paises.add(pais);
    }

    @Override
    public void update(Pais pais) {
        for (int i = 0; i < paises.size(); i++) {
            if (paises.get(i).getIdPais() == pais.getIdPais()) {
                paises.set(i, pais);
                return;
            }
        }
    }

    @Override
    public void delete(Pais pais) {
        paises.removeIf(p -> p.getIdPais() == pais.getIdPais());
    }

    @Override
    public List<Pais> getAll() {
        return new ArrayList<>(paises); // Return a copy
    }

    @Override
    public Pais getById(int id) {
        return paises.stream()
                .filter(p -> p.getIdPais() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void deleteAll() {
        paises.clear();
    }

    // ─────────────────────────────
    // SEARCH
    // ─────────────────────────────

    @Override
    public List<Pais> searchByNombre(String nombre) {
        // Simulates database search by name (case-insensitive containment)
        String lowerCaseNombre = nombre.toLowerCase();
        return paises.stream()
                .filter(p -> p.getNombre().toLowerCase().contains(lowerCaseNombre))
                .collect(Collectors.toList());
    }
}