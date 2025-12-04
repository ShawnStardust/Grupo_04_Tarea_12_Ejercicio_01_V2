package com.example.grupo_04_tarea_12_ejercicio_01.domain.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "aeropuerto",
        foreignKeys = {
                @ForeignKey(
                        entity = Pais.class,
                        parentColumns = "idpais",
                        childColumns = "idpais",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {@Index("idpais")}
)
public class Aeropuerto {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idaeropuerto")
    private int idAeropuerto;

    @ColumnInfo(name = "nombre")
    private String nombre;

    @ColumnInfo(name = "idpais")
    private int idPais;

    public Aeropuerto() {
    }

    public Aeropuerto(String nombre, int idPais) {
        this.nombre = nombre;
        this.idPais = idPais;
    }

    public int getIdAeropuerto() {
        return idAeropuerto;
    }

    public void setIdAeropuerto(int idAeropuerto) {
        this.idAeropuerto = idAeropuerto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }
}