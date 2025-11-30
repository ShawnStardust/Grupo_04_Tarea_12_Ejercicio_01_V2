package com.example.grupo_04_tarea_12_ejercicio_01.domain.model;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pais")
public class Pais {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idpais")
    private int idPais;
    @ColumnInfo(name = "nombre")
    private String nombre;

    public Pais() {}
    public Pais(String nombre) { this.nombre = nombre; }

    public int getIdPais() { return idPais; }
    public void setIdPais(int idPais) { this.idPais = idPais; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    @Override
    public String toString() { return nombre; }
}