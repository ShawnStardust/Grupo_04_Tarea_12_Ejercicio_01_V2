package com.example.grupo_04_tarea_12_ejercicio_01.domain.Repository;

import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Pasajero;
import java.util.List;

public interface PasajeroRepository {
    void insert(Pasajero pasajero);
    void update(Pasajero pasajero);
    void delete(Pasajero pasajero);
    List<Pasajero> getAll();
    Pasajero getById(int id);
    List<Pasajero> searchByNombre(String nombre);
    void deleteAll();
}