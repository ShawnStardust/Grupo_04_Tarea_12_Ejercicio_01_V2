package com.example.grupo_04_tarea_12_ejercicio_01.data.mock;

import com.example.grupo_04_tarea_12_ejercicio_01.domain.Repository.TarifaRepository;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Tarifa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class FakeTarifaRepositoryImpl implements TarifaRepository {

    private List<Tarifa> tarifas;
    private int nextId;

    @Inject
    public FakeTarifaRepositoryImpl() {

        this.tarifas = new ArrayList<>(MockDataProvider.getTarifas());

        this.nextId = tarifas.stream()
                .mapToInt(Tarifa::getIdTarifa)
                .max()
                .orElse(0) + 1;
    }

    @Override
    public void insert(Tarifa tarifa) {
        if (tarifa.getIdTarifa() == 0) {
            tarifa.setIdTarifa(nextId++);
        }
        tarifas.add(tarifa);
    }

    @Override
    public void update(Tarifa tarifa) {
        for (int i = 0; i < tarifas.size(); i++) {
            if (tarifas.get(i).getIdTarifa() == tarifa.getIdTarifa()) {
                tarifas.set(i, tarifa);
                return;
            }
        }
    }

    @Override
    public void delete(Tarifa tarifa) {
        tarifas.removeIf(t -> t.getIdTarifa() == tarifa.getIdTarifa());
    }

    @Override
    public List<Tarifa> getAll() {
        return new ArrayList<>(tarifas); // Devolver una copia
    }

    @Override
    public Tarifa getById(int id) {
        return tarifas.stream()
                .filter(t -> t.getIdTarifa() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Tarifa getByClase(String clase) {
        return (Tarifa) tarifas.stream()
                .filter(t -> t.getClase().equalsIgnoreCase(clase))
                .collect(Collectors.toList());
    }

    @Override
    public List<Tarifa> getAllOrdenadoPorPrecio() {
        return Collections.emptyList();
    }

    @Override
    public double getPrecioTotal(int idTarifa) {
        return tarifas.stream()
                .filter(t -> t.getIdTarifa() == idTarifa)
                .mapToDouble(t -> t.getPrecio() + t.getImpuesto())
                .findFirst()
                .orElse(0.0);
    }

    @Override
    public void deleteAll() {
        tarifas.clear();
    }
}