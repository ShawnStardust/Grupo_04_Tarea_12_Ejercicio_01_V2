package com.example.grupo_04_tarea_12_ejercicio_01.data.repository;

import com.example.grupo_04_tarea_12_ejercicio_01.data.local.dao.TarifaDao;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.Repository.TarifaRepository;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Tarifa;

import java.util.List;

import javax.inject.Inject;

public class TarifaRepositoryImpl implements TarifaRepository {

    private final TarifaDao tarifaDao;

    @Inject
    public TarifaRepositoryImpl(TarifaDao tarifaDao) {
        this.tarifaDao = tarifaDao;
    }

    @Override
    public void insert(Tarifa tarifa) {
        tarifaDao.insert(tarifa);
    }

    @Override
    public void update(Tarifa tarifa) {
        tarifaDao.update(tarifa);
    }

    @Override
    public void delete(Tarifa tarifa) {
        tarifaDao.delete(tarifa);
    }

    @Override
    public List<Tarifa> getAll() {
        return tarifaDao.getAll();
    }

    @Override
    public Tarifa getById(int id) {
        return tarifaDao.getById(id);
    }

    @Override
    public Tarifa getByClase(String clase) {
        return tarifaDao.getByClase(clase);
    }

    @Override
    public List<Tarifa> getAllOrdenadoPorPrecio() {
        return tarifaDao.getAllOrdenadoPorPrecio();
    }

    @Override
    public double getPrecioTotal(int idTarifa) {
        return 0;
    }

    @Override
    public void deleteAll() {
        tarifaDao.deleteAll();
    }
}