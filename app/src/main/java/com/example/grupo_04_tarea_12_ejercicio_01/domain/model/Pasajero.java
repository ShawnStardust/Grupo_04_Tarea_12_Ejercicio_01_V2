package com.example.grupo_04_tarea_12_ejercicio_01.domain.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pasajero")
public class Pasajero {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idpasajero")
    private int idPasajero;

    @ColumnInfo(name = "nombre")
    private String nombre;

    @ColumnInfo(name = "apaterno")
    private String apaterno;

    @ColumnInfo(name = "amaterno")
    private String amaterno;

    @ColumnInfo(name = "tipo_documento")
    private String tipoDocumento;

    @ColumnInfo(name = "num_documento")
    private String numDocumento;

    @ColumnInfo(name = "fecha_nacimiento")
    private String fechaNacimiento;

    @ColumnInfo(name = "idpais")
    private int idPais;

    @ColumnInfo(name = "telefono")
    private String telefono;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "clave")
    private String clave;

    public Pasajero() {
    }

    public Pasajero(String nombre, String apaterno, String amaterno, String tipoDocumento, String numDocumento, String fechaNacimiento, int idPais, String telefono, String email, String clave) {
        this.nombre = nombre;
        this.apaterno = apaterno;
        this.amaterno = amaterno;
        this.tipoDocumento = tipoDocumento;
        this.numDocumento = numDocumento;
        this.fechaNacimiento = fechaNacimiento;
        this.idPais = idPais;
        this.telefono = telefono;
        this.email = email;
        this.clave = clave;
    }

    public int getIdPasajero() { return idPasajero; }
    public void setIdPasajero(int idPasajero) { this.idPasajero = idPasajero; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApaterno() { return apaterno; }
    public void setApaterno(String apaterno) { this.apaterno = apaterno; }

    public String getAmaterno() { return amaterno; }
    public void setAmaterno(String amaterno) { this.amaterno = amaterno; }

    public String getTipoDocumento() { return tipoDocumento; }
    public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }

    public String getNumDocumento() { return numDocumento; }
    public void setNumDocumento(String numDocumento) { this.numDocumento = numDocumento; }

    public String getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public int getIdPais() { return idPais; }
    public void setIdPais(int idPais) { this.idPais = idPais; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }
}