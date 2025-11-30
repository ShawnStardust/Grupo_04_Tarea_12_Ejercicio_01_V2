package com.example.grupo_04_tarea_12_ejercicio_01.domain.usecase;

import com.example.grupo_04_tarea_12_ejercicio_01.domain.Repository.PasajeroRepository;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Pasajero;
import java.util.List;
import javax.inject.Inject;

public class PasajeroUseCase {

    private final PasajeroRepository repository;

    @Inject
    public PasajeroUseCase(PasajeroRepository repository) {
        this.repository = repository;
    }

    public void insertPasajero(Pasajero pasajero) {
        repository.insert(pasajero);
    }

    public void updatePasajero(Pasajero pasajero) {
        repository.update(pasajero);
    }

    public void deletePasajero(Pasajero pasajero) {
        repository.delete(pasajero);
    }

    public List<Pasajero> getAllPasajeros() {
        return repository.getAll();
    }
}