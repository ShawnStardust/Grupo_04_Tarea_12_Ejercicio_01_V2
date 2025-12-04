package com.example.grupo_04_tarea_12_ejercicio_01.domain.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "avion",
        foreignKeys = {
                @ForeignKey(
                        entity = Aerolinea.class,
                        parentColumns = "idaerolinea",
                        childColumns = "idaerolinea",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {@Index("idaerolinea")}
)
public class Avion {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idavion")
    private int idAvion;

    @ColumnInfo(name = "idaerolinea")
    private int idAerolinea;

    @ColumnInfo(name = "tipo_avion")
    private String tipoAvion;

    @ColumnInfo(name = "capacidad")
    private int capacidad;

    public Avion() {
    }

    public Avion(int idAerolinea, String tipoAvion, int capacidad) {
        this.idAerolinea = idAerolinea;
        this.tipoAvion = tipoAvion;
        this.capacidad = capacidad;
    }

    public int getIdAvion() {
        return idAvion;
    }

    public void setIdAvion(int idAvion) {
        this.idAvion = idAvion;
    }

    public int getIdAerolinea() {
        return idAerolinea;
    }

    public void setIdAerolinea(int idAerolinea) {
        this.idAerolinea = idAerolinea;
    }

    public String getTipoAvion() {
        return tipoAvion;
    }

    public void setTipoAvion(String tipoAvion) {
        this.tipoAvion = tipoAvion;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
}