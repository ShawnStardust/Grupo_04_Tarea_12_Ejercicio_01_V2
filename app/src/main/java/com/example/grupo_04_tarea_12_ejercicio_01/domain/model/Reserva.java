package com.example.grupo_04_tarea_12_ejercicio_01.domain.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(
        tableName = "reserva",
        foreignKeys = {
                @ForeignKey(
                        entity = Pasajero.class,
                        parentColumns = "idpasajero",
                        childColumns = "idpasajero",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Vuelo.class,
                        parentColumns = "idvuelo",
                        childColumns = "idvuelo",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {
                @Index("idpasajero"),
                @Index("idvuelo")
        }
)
public class Reserva {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idreserva")
    private int idReserva;

    @ColumnInfo(name = "idpasajero")
    private int idPasajero;

    @ColumnInfo(name = "idvuelo")
    private int idVuelo;

    @ColumnInfo(name = "costo")
    private double costo;

    @ColumnInfo(name = "fecha")
    private Date fecha;

    @ColumnInfo(name = "observacion")
    private String observacion;

    public Reserva() {
    }

    public Reserva(int idPasajero, int idVuelo, double costo, Date fecha, String observacion) {
        this.idPasajero = idPasajero;
        this.idVuelo = idVuelo;
        this.costo = costo;
        this.fecha = fecha;
        this.observacion = observacion;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public int getIdPasajero() {
        return idPasajero;
    }

    public void setIdPasajero(int idPasajero) {
        this.idPasajero = idPasajero;
    }

    public int getIdVuelo() {
        return idVuelo;
    }

    public void setIdVuelo(int idVuelo) {
        this.idVuelo = idVuelo;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}