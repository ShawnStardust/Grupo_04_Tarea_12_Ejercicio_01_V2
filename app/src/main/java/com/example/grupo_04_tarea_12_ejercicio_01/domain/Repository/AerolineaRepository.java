package com.example.grupo_04_tarea_12_ejercicio_01.domain.Repository;

import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Aerolinea;

import java.util.List;

public interface AerolineaRepository {
    void insert(Aerolinea aerolinea);
    void update(Aerolinea aerolinea);
    void delete(Aerolinea aerolinea);
    List<Aerolinea> getAll();
    Aerolinea getById(int id);
    List<Aerolinea> searchByNombre(String nombre);
    void deleteAll();
}
