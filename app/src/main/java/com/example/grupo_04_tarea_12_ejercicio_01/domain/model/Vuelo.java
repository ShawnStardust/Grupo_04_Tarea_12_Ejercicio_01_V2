package com.example.grupo_04_tarea_12_ejercicio_01.domain.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "vuelo",
        foreignKeys = {
                @ForeignKey(
                        entity = Aeropuerto.class,
                        parentColumns = "idaeropuerto",
                        childColumns = "idaeropuerto_origen",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Aeropuerto.class,
                        parentColumns = "idaeropuerto",
                        childColumns = "idaeropuerto_destino",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Avion.class,
                        parentColumns = "idavion",
                        childColumns = "idavion",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {
                @Index("idaeropuerto_origen"),
                @Index("idaeropuerto_destino"),
                @Index("idavion")
        }
)
public class Vuelo {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idvuelo")
    private int idVuelo;

    @ColumnInfo(name = "idaeropuerto_origen")
    private int idAeropuertoOrigen;

    @ColumnInfo(name = "idaeropuerto_destino")
    private int idAeropuertoDestino;

    @ColumnInfo(name = "idavion")
    private int idAvion;

    @ColumnInfo(name = "idfila")
    private int idFila;

    public Vuelo() {
    }

    public Vuelo(int idAeropuertoOrigen, int idAeropuertoDestino, int idAvion, int idFila) {
        this.idAeropuertoOrigen = idAeropuertoOrigen;
        this.idAeropuertoDestino = idAeropuertoDestino;
        this.idAvion = idAvion;
        this.idFila = idFila;
    }

    public int getIdVuelo() {
        return idVuelo;
    }

    public void setIdVuelo(int idVuelo) {
        this.idVuelo = idVuelo;
    }

    public int getIdAeropuertoOrigen() {
        return idAeropuertoOrigen;
    }

    public void setIdAeropuertoOrigen(int idAeropuertoOrigen) {
        this.idAeropuertoOrigen = idAeropuertoOrigen;
    }

    public int getIdAeropuertoDestino() {
        return idAeropuertoDestino;
    }

    public void setIdAeropuertoDestino(int idAeropuertoDestino) {
        this.idAeropuertoDestino = idAeropuertoDestino;
    }

    public int getIdAvion() {
        return idAvion;
    }

    public void setIdAvion(int idAvion) {
        this.idAvion = idAvion;
    }

    public int getIdFila() {
        return idFila;
    }

    public void setIdFila(int idFila) {
        this.idFila = idFila;
    }
}