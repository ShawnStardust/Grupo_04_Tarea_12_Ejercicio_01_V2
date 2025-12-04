package com.example.grupo_04_tarea_12_ejercicio_01.domain.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "asiento",
        foreignKeys = {
                @ForeignKey(
                        entity = Vuelo.class,
                        parentColumns = "idvuelo",
                        childColumns = "idvuelo",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Reserva.class,
                        parentColumns = "idreserva",
                        childColumns = "idreserva",
                        onDelete = ForeignKey.SET_NULL
                )
        },
        indices = {
                @Index("idvuelo"),
                @Index("idreserva")
        }
)
public class Asiento {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idasiento")
    private int idAsiento;

    @ColumnInfo(name = "idvuelo")
    private int idVuelo;

    @ColumnInfo(name = "idreserva")
    private Integer idReserva;

    @ColumnInfo(name = "fila")
    private int fila;

    @ColumnInfo(name = "estado")
    private String estado;

    public Asiento() {
        this.idReserva = 0;
    }

    public Asiento(int idVuelo, Integer idReserva, int fila, String estado) {
        this.idVuelo = idVuelo;
        this.idReserva = (idReserva != null && idReserva > 0) ? idReserva : 0;
        this.fila = fila;
        this.estado = estado;
    }

    public int getIdAsiento() {
        return idAsiento;
    }

    public void setIdAsiento(int idAsiento) {
        this.idAsiento = idAsiento;
    }

    public int getIdVuelo() {
        return idVuelo;
    }

    public void setIdVuelo(int idVuelo) {
        this.idVuelo = idVuelo;
    }

    public int getIdReserva() {
        return (idReserva != null) ? idReserva : 0;
    }

    public void setIdReserva(int idReserva) {

        this.idReserva = (idReserva > 0) ? idReserva : 0;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean tieneReserva() {
        return idReserva != null && idReserva > 0;
    }
}