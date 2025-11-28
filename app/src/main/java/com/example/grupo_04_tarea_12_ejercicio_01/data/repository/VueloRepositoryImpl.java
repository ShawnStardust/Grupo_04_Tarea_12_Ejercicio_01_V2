package com.example.grupo_04_tarea_12_ejercicio_01.data.repository;

import com.example.grupo_04_tarea_12_ejercicio_01.data.local.dao.VueloDao;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.Repository.VueloRepository;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Vuelo;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class VueloRepositoryImpl implements VueloRepository {

    private final VueloDao vueloDao;

    @Inject
    public VueloRepositoryImpl(VueloDao vueloDao) {
        this.vueloDao = vueloDao;
    }

    @Override
    public void insert(Vuelo vuelo) {
        vueloDao.insert(vuelo);
    }

    @Override
    public void update(Vuelo vuelo) {
        vueloDao.update(vuelo);
    }

    @Override
    public void delete(Vuelo vuelo) {
        vueloDao.delete(vuelo);
    }

    @Override
    public List<Vuelo> getAll() {
        return vueloDao.getAll();
    }

    @Override
    public Vuelo getById(int id) {
        return vueloDao.getById(id);
    }

    @Override
    public List<Vuelo> getByOrigen(int idOrigen) {
        return vueloDao.getByOrigen(idOrigen);
    }

    @Override
    public List<Vuelo> getByDestino(int idDestino) {
        return vueloDao.getByDestino(idDestino);
    }

    @Override
    public List<Vuelo> getByOrigenDestino(int idOrigen, int idDestino) {
        return vueloDao.getByOrigenDestino(idOrigen, idDestino);
    }

    @Override
    public List<Vuelo> getByRuta(int idAeropuertoOrigen, int idAeropuertoDestino) {
        return Collections.emptyList();
    }

    @Override
    public List<Vuelo> getByAvion(int idAvion) {
        return vueloDao.getByAvion(idAvion);
    }

    @Override
    public void deleteAll() {
        vueloDao.deleteAll();
    }
}