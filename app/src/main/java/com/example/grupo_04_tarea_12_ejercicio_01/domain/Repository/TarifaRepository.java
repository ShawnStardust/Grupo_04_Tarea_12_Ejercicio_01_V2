package com.example.grupo_04_tarea_12_ejercicio_01.domain.Repository;

import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Tarifa;

import java.util.List;

public interface TarifaRepository {
    void insert(Tarifa tarifa);
    void update(Tarifa tarifa);
    void delete(Tarifa tarifa);
    List<Tarifa> getAll();
    Tarifa getById(int id);
    Tarifa getByClase(String clase);
    List<Tarifa> getAllOrdenadoPorPrecio();

    double getPrecioTotal(int idTarifa);

    void deleteAll();
}