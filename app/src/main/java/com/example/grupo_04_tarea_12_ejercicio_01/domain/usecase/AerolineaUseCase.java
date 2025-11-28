package com.example.grupo_04_tarea_12_ejercicio_01.domain.usecase;

import com.example.grupo_04_tarea_12_ejercicio_01.domain.Repository.AerolineaRepository;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Aerolinea;
import java.util.List;
import javax.inject.Inject;

public class AerolineaUseCase {

    private final AerolineaRepository repository;

    @Inject
    public AerolineaUseCase(AerolineaRepository repository) {
        this.repository = repository;
    }

    public void insertAerolinea(Aerolinea aerolinea) {
        repository.insert(aerolinea);
    }

    public void updateAerolinea(Aerolinea aerolinea) {
        repository.update(aerolinea);
    }

    public void deleteAerolinea(Aerolinea aerolinea) {
        repository.delete(aerolinea);
    }

    public List<Aerolinea> getAllAerolineas() {
        return repository.getAll();
    }
}