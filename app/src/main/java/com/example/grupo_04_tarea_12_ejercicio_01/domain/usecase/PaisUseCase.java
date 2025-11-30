package com.example.grupo_04_tarea_12_ejercicio_01.domain.usecase;

import com.example.grupo_04_tarea_12_ejercicio_01.domain.Repository.PaisRepository;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Pais;
import java.util.List;
import javax.inject.Inject;

public class PaisUseCase {

    private final PaisRepository repository;

    @Inject
    public PaisUseCase(PaisRepository repository) {
        this.repository = repository;
    }

    public void insertPais(Pais pais) {
        repository.insert(pais);
    }

    public void updatePais(Pais pais) {
        repository.update(pais);
    }

    public void deletePais(Pais pais) {
        repository.delete(pais);
    }

    public List<Pais> getAllPaises() {
        return repository.getAll();
    }
}