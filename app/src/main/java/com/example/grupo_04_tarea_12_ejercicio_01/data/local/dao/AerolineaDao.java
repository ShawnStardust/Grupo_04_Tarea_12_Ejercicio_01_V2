package com.example.grupo_04_tarea_12_ejercicio_01.data.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Aerolinea;

import java.util.List;

@Dao
public interface AerolineaDao {
    @Insert
    void insert(Aerolinea aerolinea);

    @Update
    void update(Aerolinea aerolinea);

    @Delete
    void delete(Aerolinea aerolinea);

    @Query("SELECT * FROM aerolinea")
    List<Aerolinea> getAll();

    @Query("SELECT * FROM aerolinea WHERE idaerolinea = :id")
    Aerolinea getById(int id);

    @Query("SELECT * FROM aerolinea WHERE nombre LIKE :nombre || '%'")
    List<Aerolinea> searchByNombre(String nombre);

    @Query("DELETE FROM aerolinea")
    void deleteAll();
}
