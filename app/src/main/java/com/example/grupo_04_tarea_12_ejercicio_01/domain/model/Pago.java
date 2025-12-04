package com.example.grupo_04_tarea_12_ejercicio_01.domain.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(
        tableName = "pago",
        foreignKeys = {
                @ForeignKey(
                        entity = Reserva.class,
                        parentColumns = "idreserva",
                        childColumns = "idreserva",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {@Index("idreserva")}
)
public class Pago {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idpago")
    private int idPago;

    @ColumnInfo(name = "idreserva")
    private int idReserva;

    @ColumnInfo(name = "fecha")
    private Date fecha;

    @ColumnInfo(name = "tipo_comprobante")
    private String tipoComprobante;

    @ColumnInfo(name = "num_comprobante")
    private String numComprobante;

    @ColumnInfo(name = "monto")
    private double monto;

    @ColumnInfo(name = "impuesto")
    private double impuesto;

    public Pago() {
    }

    public Pago(int idReserva, Date fecha, String tipoComprobante, String numComprobante,
                double monto, double impuesto) {
        this.idReserva = idReserva;
        this.fecha = fecha;
        this.tipoComprobante = tipoComprobante;
        this.numComprobante = numComprobante;
        this.monto = monto;
        this.impuesto = impuesto;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(String tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public String getNumComprobante() {
        return numComprobante;
    }

    public void setNumComprobante(String numComprobante) {
        this.numComprobante = numComprobante;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public double getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(double impuesto) {
        this.impuesto = impuesto;
    }
}