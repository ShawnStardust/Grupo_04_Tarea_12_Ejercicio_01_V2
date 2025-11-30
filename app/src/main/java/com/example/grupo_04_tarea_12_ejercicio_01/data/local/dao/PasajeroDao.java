package com.example.grupo_04_tarea_12_ejercicio_01.data.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Pasajero;

import java.util.List;

@Dao
public interface PasajeroDao {

    @Insert
    void insert(Pasajero pasajero);

    @Update
    void update(Pasajero pasajero);

    @Delete
    void delete(Pasajero pasajero);

    @Query("SELECT * FROM pasajero")
    List<Pasajero> getAll();

    @Query("SELECT * FROM pasajero WHERE idpasajero = :id")
    Pasajero getById(int id);

    // Búsqueda por nombre o apellido
    @Query("SELECT * FROM pasajero WHERE nombre LIKE :busqueda || '%' OR apaterno LIKE :busqueda || '%'")
    List<Pasajero> searchByNombre(String busqueda);

    @Query("DELETE FROM pasajero")
    void deleteAll();
}