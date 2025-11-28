package com.example.grupo_04_tarea_12_ejercicio_01.data.repository;

import com.example.grupo_04_tarea_12_ejercicio_01.data.local.dao.AerolineaDao;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.Repository.AerolineaRepository;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Aerolinea;

import java.util.List;

import javax.inject.Inject;

public class AerolineaRepositoryImpl implements AerolineaRepository {

    private final AerolineaDao aerolineaDao;

    @Inject
    public AerolineaRepositoryImpl(AerolineaDao aerolineaDao) {
        this.aerolineaDao = aerolineaDao;
    }

    @Override
    public void insert(Aerolinea aerolinea) {
        aerolineaDao.insert(aerolinea);
    }

    @Override
    public void update(Aerolinea aerolinea) {
        aerolineaDao.update(aerolinea);
    }

    @Override
    public void delete(Aerolinea aerolinea) {
        aerolineaDao.delete(aerolinea);
    }

    @Override
    public List<Aerolinea> getAll() {
        return aerolineaDao.getAll();
    }

    @Override
    public Aerolinea getById(int id) {
        return aerolineaDao.getById(id);
    }

    @Override
    public List<Aerolinea> searchByNombre(String nombre) {
        return aerolineaDao.searchByNombre(nombre);
    }

    @Override
    public void deleteAll() {
        aerolineaDao.deleteAll();
    }
}
