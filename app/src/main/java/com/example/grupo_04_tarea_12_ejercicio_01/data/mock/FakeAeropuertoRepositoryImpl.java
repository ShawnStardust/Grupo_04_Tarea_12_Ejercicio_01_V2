package com.example.grupo_04_tarea_12_ejercicio_01.data.mock;

import com.example.grupo_04_tarea_12_ejercicio_01.domain.Repository.AeropuertoRepository;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Aeropuerto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class FakeAeropuertoRepositoryImpl implements AeropuertoRepository {

    private List<Aeropuerto> aeropuertos;
    private int nextId;

    @Inject
    public FakeAeropuertoRepositoryImpl() {
        // Datos mock
        this.aeropuertos = new ArrayList<>(MockDataProvider.getAeropuertos());

        // Siguiente ID disponible
        this.nextId = aeropuertos.stream()
                .mapToInt(Aeropuerto::getIdAeropuerto)
                .max()
                .orElse(0) + 1;
    }

    @Override
    public void insert(Aeropuerto aeropuerto) {
        if (aeropuerto.getIdAeropuerto() == 0) {
            aeropuerto.setIdAeropuerto(nextId++);
        }
        aeropuertos.add(aeropuerto);
    }

    @Override
    public void update(Aeropuerto aeropuerto) {
        for (int i = 0; i < aeropuertos.size(); i++) {
            if (aeropuertos.get(i).getIdAeropuerto() == aeropuerto.getIdAeropuerto()) {
                aeropuertos.set(i, aeropuerto);
                return;
            }
        }
    }

    @Override
    public void delete(Aeropuerto aeropuerto) {
        aeropuertos.removeIf(a -> a.getIdAeropuerto() == aeropuerto.getIdAeropuerto());
    }

    @Override
    public List<Aeropuerto> getAll() {
        return new ArrayList<>(aeropuertos);
    }

    @Override
    public Aeropuerto getById(int id) {
        return aeropuertos.stream()
                .filter(a -> a.getIdAeropuerto() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Aeropuerto> getByPais(int idPais) {
        return aeropuertos.stream()
                .filter(a -> a.getIdPais() == idPais)
                .collect(Collectors.toList());
    }

    @Override
    public List<Aeropuerto> searchByNombre(String nombre) {
        String lower = nombre.toLowerCase();
        return aeropuertos.stream()
                .filter(a -> a.getNombre().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAll() {
        aeropuertos.clear();
    }
}
