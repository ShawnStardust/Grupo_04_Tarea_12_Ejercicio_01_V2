package com.example.grupo_04_tarea_12_ejercicio_01.domain.usecase;

import com.example.grupo_04_tarea_12_ejercicio_01.domain.Repository.VueloRepository;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Vuelo;

import java.util.List;

import javax.inject.Inject;

public class VueloUseCase {

    private final VueloRepository repository;

    @Inject
    public VueloUseCase(VueloRepository repository) {
        this.repository = repository;
    }

    public void insertVuelo(Vuelo vuelo) {
        repository.insert(vuelo);
    }

    public void updateVuelo(Vuelo vuelo) {
        repository.update(vuelo);
    }

    public void deleteVuelo(Vuelo vuelo) {
        repository.delete(vuelo);
    }

    public List<Vuelo> getAllVuelos() {
        return repository.getAll();
    }

    public Vuelo getVueloById(int id) {
        return repository.getById(id);
    }

    public List<Vuelo> getVuelosByOrigen(int idOrigen) {
        return repository.getByOrigen(idOrigen);
    }

    public List<Vuelo> getVuelosByDestino(int idDestino) {
        return repository.getByDestino(idDestino);
    }

    public List<Vuelo> getVuelosByOrigenDestino(int idOrigen, int idDestino) {
        return repository.getByOrigenDestino(idOrigen, idDestino);
    }

    public List<Vuelo> getVuelosByAvion(int idAvion) {
        return repository.getByAvion(idAvion);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
