package com.example.grupo_04_tarea_12_ejercicio_01.domain.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "aerolinea")
public class Aerolinea {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idaerolinea")
    private int idAerolinea;

    @ColumnInfo(name = "ruc")
    private String ruc;

    @ColumnInfo(name = "nombre")
    private String nombre;

    public Aerolinea() { }

    public Aerolinea(String ruc, String nombre) {
        this.ruc = ruc;
        this.nombre = nombre;
    }

    public int getIdAerolinea() { return idAerolinea; }
    public void setIdAerolinea(int idAerolinea) { this.idAerolinea = idAerolinea; }

    public String getRuc() { return ruc; }
    public void setRuc(String ruc) { this.ruc = ruc; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    @Override
    public String toString() { return nombre; }
}