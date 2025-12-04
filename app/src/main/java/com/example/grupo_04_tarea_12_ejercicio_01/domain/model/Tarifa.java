package com.example.grupo_04_tarea_12_ejercicio_01.domain.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tarifa")
public class Tarifa {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idtarifa")
    private int idTarifa;

    @ColumnInfo(name = "clase")
    private String clase;

    @ColumnInfo(name = "precio")
    private double precio;

    @ColumnInfo(name = "impuesto")
    private double impuesto;

    public Tarifa() {
    }

    public Tarifa(String clase, double precio, double impuesto) {
        this.clase = clase;
        this.precio = precio;
        this.impuesto = impuesto;
    }

    public int getIdTarifa() {
        return idTarifa;
    }

    public void setIdTarifa(int idTarifa) {
        this.idTarifa = idTarifa;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(double impuesto) {
        this.impuesto = impuesto;
    }
}