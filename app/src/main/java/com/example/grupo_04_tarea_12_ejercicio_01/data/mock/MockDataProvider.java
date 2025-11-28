package com.example.grupo_04_tarea_12_ejercicio_01.data.mock;


import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MockDataProvider {

    // PAÍSES
    public static List<Pais> getPaises() {
        List<Pais> paises = new ArrayList<>();

        paises.add(createPais(1, "Perú"));
        paises.add(createPais(2, "Colombia"));
        paises.add(createPais(3, "Chile"));
        paises.add(createPais(4, "Argentina"));
        paises.add(createPais(5, "Brasil"));
        paises.add(createPais(6, "México"));
        paises.add(createPais(7, "España"));
        paises.add(createPais(8, "Estados Unidos"));

        return paises;
    }

    private static Pais createPais(int id, String nombre) {
        Pais pais = new Pais();
        pais.setIdPais(id);
        pais.setNombre(nombre);
        return pais;
    }

    // AEROLÍNEAS
    public static List<Aerolinea> getAerolineas() {
        List<Aerolinea> aerolineas = new ArrayList<>();

        aerolineas.add(createAerolinea(1, "LATAM Airlines", 1));
        aerolineas.add(createAerolinea(2, "Avianca", 2));
        aerolineas.add(createAerolinea(3, "Sky Airline", 3));
        aerolineas.add(createAerolinea(4, "Aerolíneas Argentinas", 4));
        aerolineas.add(createAerolinea(5, "Gol Linhas Aéreas", 5));
        aerolineas.add(createAerolinea(6, "Aeroméxico", 6));
        aerolineas.add(createAerolinea(7, "Iberia", 7));
        aerolineas.add(createAerolinea(8, "American Airlines", 8));

        return aerolineas;
    }

    private static Aerolinea createAerolinea(int id, String nombre, int idPais) {
        Aerolinea aerolinea = new Aerolinea();
        aerolinea.setIdAerolinea(id);
        aerolinea.setNombre(nombre);
        aerolinea.setIdPais(idPais);
        return aerolinea;
    }

    // AEROPUERTOS
    public static List<Aeropuerto> getAeropuertos() {
        List<Aeropuerto> aeropuertos = new ArrayList<>();

        aeropuertos.add(createAeropuerto(1, "Jorge Chávez - Lima", 1));
        aeropuertos.add(createAeropuerto(2, "Rodríguez Ballón - Arequipa", 1));
        aeropuertos.add(createAeropuerto(3, "El Dorado - Bogotá", 2));
        aeropuertos.add(createAeropuerto(4, "Arturo Merino Benítez - Santiago", 3));
        aeropuertos.add(createAeropuerto(5, "Ezeiza - Buenos Aires", 4));
        aeropuertos.add(createAeropuerto(6, "Guarulhos - São Paulo", 5));
        aeropuertos.add(createAeropuerto(7, "Benito Juárez - Ciudad de México", 6));
        aeropuertos.add(createAeropuerto(8, "Barajas - Madrid", 7));
        aeropuertos.add(createAeropuerto(9, "JFK - Nueva York", 8));
        aeropuertos.add(createAeropuerto(10, "LAX - Los Ángeles", 8));

        return aeropuertos;
    }

    private static Aeropuerto createAeropuerto(int id, String nombre, int idPais) {
        Aeropuerto aeropuerto = new Aeropuerto();
        aeropuerto.setIdAeropuerto(id);
        aeropuerto.setNombre(nombre);
        aeropuerto.setIdPais(idPais);
        return aeropuerto;
    }

    // AVIONES
    public static List<Avion> getAviones() {
        List<Avion> aviones = new ArrayList<>();

        aviones.add(createAvion(1, 1, "Boeing 787", 250));
        aviones.add(createAvion(2, 1, "Airbus A320", 180));
        aviones.add(createAvion(3, 2, "Boeing 737", 160));
        aviones.add(createAvion(4, 2, "Airbus A330", 290));
        aviones.add(createAvion(5, 3, "Airbus A320neo", 186));
        aviones.add(createAvion(6, 4, "Boeing 737 MAX", 172));
        aviones.add(createAvion(7, 5, "Boeing 737-800", 186));
        aviones.add(createAvion(8, 6, "Boeing 787-9", 274));
        aviones.add(createAvion(9, 7, "Airbus A350", 325));
        aviones.add(createAvion(10, 8, "Boeing 777", 368));

        return aviones;
    }

    private static Avion createAvion(int id, int idAerolinea, String tipo, int capacidad) {
        Avion avion = new Avion();
        avion.setIdAvion(id);
        avion.setIdAerolinea(idAerolinea);
        avion.setTipoAvion(tipo);
        avion.setCapacidad(capacidad);
        return avion;
    }

    // --- 💰 TARIFAS CORREGIDAS ---
    public static List<Tarifa> getTarifas() {
        List<Tarifa> tarifas = new ArrayList<>();

        tarifas.add(createTarifa(1, "Económica", 150.00, 18.00));
        tarifas.add(createTarifa(2, "Premium Economy", 300.00, 36.00));
        tarifas.add(createTarifa(3, "Business", 800.00, 96.00));
        tarifas.add(createTarifa(4, "Primera Clase", 1500.00, 180.00));
        tarifas.add(createTarifa(5, "Light", 100.00, 12.00));
        tarifas.add(createTarifa(6, "Full", 450.00, 54.00));


        return tarifas;
    }

    private static Tarifa createTarifa(int id, String clase, double precio, double impuesto) {
        Tarifa tarifa = new Tarifa();
        tarifa.setIdTarifa(id);
        tarifa.setClase(clase);
        tarifa.setPrecio(precio);
        tarifa.setImpuesto(impuesto);
        return tarifa;
    }
    // -----------------------------------

    // VUELOS
    public static List<Vuelo> getVuelos() {
        List<Vuelo> vuelos = new ArrayList<>();

        vuelos.add(createVuelo(1, 1, 2, 1, 30));
        vuelos.add(createVuelo(2, 1, 3, 2, 35));
        vuelos.add(createVuelo(3, 1, 4, 3, 40));
        vuelos.add(createVuelo(4, 2, 1, 4, 30));
        vuelos.add(createVuelo(5, 3, 5, 5, 45));
        vuelos.add(createVuelo(6, 4, 6, 6, 38));
        vuelos.add(createVuelo(7, 1, 8, 7, 50));
        vuelos.add(createVuelo(8, 1, 9, 8, 55));
        vuelos.add(createVuelo(9, 8, 10, 9, 60));
        vuelos.add(createVuelo(10, 5, 1, 10, 42));

        return vuelos;
    }

    private static Vuelo createVuelo(int id, int origen, int destino, int idAvion, int filas) {
        Vuelo vuelo = new Vuelo();
        vuelo.setIdVuelo(id);
        vuelo.setIdAeropuertoOrigen(origen);
        vuelo.setIdAeropuertoDestino(destino);
        vuelo.setIdAvion(idAvion);
        vuelo.setIdFila(filas);
        return vuelo;
    }

    // PASAJEROS
    public static List<Pasajero> getPasajeros() {
        List<Pasajero> pasajeros = new ArrayList<>();

        pasajeros.add(createPasajero(1, "Juan Carlos", "García López", "DNI",
                "12345678", createDate(1985, 3, 15), 1, "987654321",
                "juan.garcia@email.com", "pass123"));

        pasajeros.add(createPasajero(2, "María Elena", "Rodríguez Pérez", "DNI",
                "23456789", createDate(1990, 7, 22), 1, "987654322",
                "maria.rodriguez@email.com", "pass456"));

        pasajeros.add(createPasajero(3, "Carlos Alberto", "Martínez Silva", "DNI",
                "34567890", createDate(1988, 11, 8), 1, "987654323",
                "carlos.martinez@email.com", "pass789"));

        pasajeros.add(createPasajero(4, "Ana Patricia", "Torres Vargas", "Pasaporte",
                "AB123456", createDate(1992, 2, 18), 2, "987654324",
                "ana.torres@email.com", "pass321"));

        pasajeros.add(createPasajero(5, "Luis Fernando", "Hernández Castro", "DNI",
                "45678901", createDate(1987, 9, 25), 3, "987654325",
                "luis.hernandez@email.com", "pass654"));

        return pasajeros;
    }

    private static Pasajero createPasajero(int id, String nombres, String apellidos,
                                           String tipoDoc, String numDoc, Date fechaNac, int idPais,
                                           String telefono, String email, String clave) {
        Pasajero pasajero = new Pasajero();
        pasajero.setIdPasajero(id);
        pasajero.setNombres(nombres);
        pasajero.setApellidos(apellidos);
        pasajero.setTipoDocumento(tipoDoc);
        pasajero.setNumDocumento(numDoc);
        pasajero.setFechaNacimiento(fechaNac);
        pasajero.setIdPais(idPais);
        pasajero.setTelefono(telefono);
        pasajero.setEmail(email);
        pasajero.setClave(clave);
        return pasajero;
    }

    // RESERVAS
    public static List<Reserva> getReservas() {
        List<Reserva> reservas = new ArrayList<>();

        reservas.add(createReserva(1, 1, 1, 168.00, createDate(2025, 11, 1),
                "Ventana preferida"));
        reservas.add(createReserva(2, 2, 3, 336.00, createDate(2025, 11, 5),
                "Comida vegetariana"));
        reservas.add(createReserva(3, 3, 5, 896.00, createDate(2025, 11, 10),
                "Asiento extra espacio"));
        reservas.add(createReserva(4, 4, 2, 168.00, createDate(2025, 11, 12),
                "Sin observaciones"));
        reservas.add(createReserva(5, 5, 7, 168.00, createDate(2025, 11, 15),
                "Asistencia especial"));

        return reservas;
    }

    private static Reserva createReserva(int id, int idPasajero, int idVuelo,
                                         double costo, Date fecha, String obs) {
        Reserva reserva = new Reserva();
        reserva.setIdReserva(id);
        reserva.setIdPasajero(idPasajero);
        reserva.setIdVuelo(idVuelo);
        reserva.setCosto(costo);
        reserva.setFecha(fecha);
        reserva.setObservacion(obs);
        return reserva;
    }

    // ASIENTOS
    public static List<Asiento> getAsientos() {
        List<Asiento> asientos = new ArrayList<>();

        asientos.add(createAsiento(1, 1, 1, 12, "Ocupado"));
        asientos.add(createAsiento(2, 1, 1, 12,  "Ocupado"));
        asientos.add(createAsiento(3, 1, null, 15,  "Disponible"));
        asientos.add(createAsiento(4, 1, null, 15,  "Disponible"));
        asientos.add(createAsiento(5, 2, 2, 8,  "Ocupado"));
        asientos.add(createAsiento(6, 3, 3, 5,  "Ocupado"));
        asientos.add(createAsiento(7, 3, 3, 5,  "Ocupado"));
        asientos.add(createAsiento(8, 5, null, 20, "Disponible"));

        return asientos;
    }

    private static Asiento createAsiento(int id, int idVuelo, Integer idReserva,
                                         int fila, String estado) {
        Asiento asiento = new Asiento();
        asiento.setIdAsiento(id);
        asiento.setIdVuelo(idVuelo);
        if (idReserva != null) {
            asiento.setIdReserva(idReserva);
        }
        asiento.setFila(fila);
        asiento.setEstado(estado);
        return asiento;
    }

    // PAGOS
    public static List<Pago> getPagos() {
        List<Pago> pagos = new ArrayList<>();

        pagos.add(createPago(1, 1, createDate(2025, 11, 1), "Boleta",
                "B001-00001", 168.00, 18.00));
        pagos.add(createPago(2, 2, createDate(2025, 11, 5), "Factura",
                "F001-00001", 336.00, 36.00));
        pagos.add(createPago(3, 3, createDate(2025, 11, 10), "Boleta",
                "B001-00002", 896.00, 96.00));
        pagos.add(createPago(4, 4, createDate(2025, 11, 12), "Boleta",
                "B001-00003", 168.00, 18.00));
        pagos.add(createPago(5, 5, createDate(2025, 11, 15), "Factura",
                "F001-00002", 168.00, 18.00));

        return pagos;
    }

    private static Pago createPago(int id, int idReserva, Date fecha,
                                   String tipoComp, String numComp, double monto, double impuesto) {
        Pago pago = new Pago();
        pago.setIdPago(id);
        pago.setIdReserva(idReserva);
        pago.setFecha(fecha);
        pago.setTipoComprobante(tipoComp);
        pago.setNumComprobante(numComp);
        pago.setMonto(monto);
        pago.setImpuesto(impuesto);
        return pago;
    }

    // UTILIDAD PARA CREAR FECHAS
    private static Date createDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}