package com.example.grupo_04_tarea_12_ejercicio_01.domain.Repository;

import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Vuelo;

import java.util.List;

public interface VueloRepository {
    void insert(Vuelo vuelo);
    void update(Vuelo vuelo);
    void delete(Vuelo vuelo);
    List<Vuelo> getAll();
    Vuelo getById(int id);
    List<Vuelo> getByOrigen(int idOrigen);
    List<Vuelo> getByDestino(int idDestino);
    List<Vuelo> getByOrigenDestino(int idOrigen, int idDestino);

    List<Vuelo> getByRuta(int idAeropuertoOrigen, int idAeropuertoDestino);

    List<Vuelo> getByAvion(int idAvion);
    void deleteAll();
}