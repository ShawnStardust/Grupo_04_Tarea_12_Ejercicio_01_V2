package com.example.grupo_04_tarea_12_ejercicio_01.data.mock;

import com.example.grupo_04_tarea_12_ejercicio_01.domain.Repository.VueloRepository;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Vuelo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class FakeVueloRepositoryImpl implements VueloRepository {

    private List<Vuelo> vuelos;
    private int nextId;

    @Inject
    public FakeVueloRepositoryImpl() {
        this.vuelos = new ArrayList<>(MockDataProvider.getVuelos());
        this.nextId = vuelos.stream()
                .mapToInt(Vuelo::getIdVuelo)
                .max()
                .orElse(0) + 1;
    }

    @Override
    public void insert(Vuelo vuelo) {
        if (vuelo.getIdVuelo() == 0) {
            vuelo.setIdVuelo(nextId++);
        }
        vuelos.add(vuelo);
    }

    @Override
    public void update(Vuelo vuelo) {
        for (int i = 0; i < vuelos.size(); i++) {
            if (vuelos.get(i).getIdVuelo() == vuelo.getIdVuelo()) {
                vuelos.set(i, vuelo);
                break;
            }
        }
    }

    @Override
    public void delete(Vuelo vuelo) {
        vuelos.removeIf(v -> v.getIdVuelo() == vuelo.getIdVuelo());
    }

    @Override
    public List<Vuelo> getAll() {
        return new ArrayList<>(vuelos);
    }

    @Override
    public Vuelo getById(int id) {
        return vuelos.stream()
                .filter(v -> v.getIdVuelo() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Vuelo> getByOrigen(int idOrigen) {
        return vuelos.stream()
                .filter(v -> v.getIdAeropuertoOrigen() == idOrigen)
                .collect(Collectors.toList());
    }

    @Override
    public List<Vuelo> getByDestino(int idDestino) {
        return vuelos.stream()
                .filter(v -> v.getIdAeropuertoDestino() == idDestino)
                .collect(Collectors.toList());
    }

    @Override
    public List<Vuelo> getByOrigenDestino(int idOrigen, int idDestino) {
        return vuelos.stream()
                .filter(v -> v.getIdAeropuertoOrigen() == idOrigen && v.getIdAeropuertoDestino() == idDestino)
                .collect(Collectors.toList());
    }

    @Override
    public List<Vuelo> getByAvion(int idAvion) {
        return vuelos.stream()
                .filter(v -> v.getIdAvion() == idAvion)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAll() {
        vuelos.clear();
    }
}
