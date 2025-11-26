package com.example.grupo_04_tarea_12_ejercicio_01.domain.usecase;

import com.example.grupo_04_tarea_12_ejercicio_01.domain.Repository.ReservaRepository;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Reserva;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReservaUseCaseTest {

    @Mock
    private ReservaRepository mockRepository;

    private ReservaUseCase useCase;

    @Before
    public void setUp() {
        useCase = new ReservaUseCase(mockRepository);
    }

    // CREATE
    @Test
    public void insertReserva_guardaCorrectamente() {
        Reserva reserva = crearReserva(1, 1, 1, 500.0, "Reserva estándar");

        useCase.insertReserva(reserva);

        System.out.println("✔ INSERT TEST");
        System.out.println("Request: Reserva ID " + reserva.getIdReserva());
        System.out.println("Status: OK (insert ejecutado)");

        verify(mockRepository).insert(reserva);
    }

    // READ - ALL
    @Test
    public void getAllReservas_retornaTodasLasReservas() {
        List<Reserva> reservas = Arrays.asList(
                crearReserva(1, 1, 1, 500.0, "Reserva 1"),
                crearReserva(2, 1, 2, 750.0, "Reserva 2"),
                crearReserva(3, 2, 1, 600.0, "Reserva 3")
        );

        when(mockRepository.getAll()).thenReturn(reservas);

        List<Reserva> resultado = useCase.getAllReservas();

        System.out.println("✔ GET ALL TEST");
        System.out.println("Response size: " + resultado.size());
        resultado.forEach(r -> System.out.println("- Reserva " + r.getIdReserva() + ": $" + r.getCosto()));

        assertEquals(3, resultado.size());
    }

    // READ - ONE
    @Test
    public void getReservaById_retornaReservaCorrecta() {
        Reserva esperada = crearReserva(1, 1, 1, 500.0, "Reserva test");
        when(mockRepository.getById(1)).thenReturn(esperada);

        Reserva resultado = useCase.getReservaById(1);

        System.out.println("✔ GET BY ID TEST");
        System.out.println("Request: 1");
        System.out.println("Response: Reserva con costo $" + resultado.getCosto());

        assertEquals(500.0, resultado.getCosto(), 0.01);
    }

    // READ - BY PASAJERO
    @Test
    public void getReservasByPasajero_retornaReservasDelPasajero() {
        List<Reserva> reservas = Arrays.asList(
                crearReserva(1, 1, 1, 500.0, "Reserva 1"),
                crearReserva(2, 1, 2, 750.0, "Reserva 2")
        );
        when(mockRepository.getByPasajero(1)).thenReturn(reservas);

        List<Reserva> resultado = useCase.getReservasByPasajero(1);

        System.out.println("✔ GET BY PASAJERO TEST");
        System.out.println("Pasajero ID: 1");
        System.out.println("Reservas encontradas: " + resultado.size());

        assertEquals(2, resultado.size());
    }

    // READ - BY VUELO
    @Test
    public void getReservasByVuelo_retornaReservasDelVuelo() {
        List<Reserva> reservas = Arrays.asList(
                crearReserva(1, 1, 1, 500.0, "Reserva 1"),
                crearReserva(2, 2, 1, 600.0, "Reserva 2")
        );
        when(mockRepository.getByVuelo(1)).thenReturn(reservas);

        List<Reserva> resultado = useCase.getReservasByVuelo(1);

        System.out.println("✔ GET BY VUELO TEST");
        System.out.println("Vuelo ID: 1");
        System.out.println("Reservas encontradas: " + resultado.size());

        assertEquals(2, resultado.size());
    }

    // READ - ORDENADAS
    @Test
    public void getReservasPasajeroOrdenadas_retornaReservasOrdenadas() {
        List<Reserva> reservas = Arrays.asList(
                crearReserva(3, 1, 3, 800.0, "Reserva más reciente"),
                crearReserva(1, 1, 1, 500.0, "Reserva más antigua")
        );
        when(mockRepository.getReservasPasajeroOrdenadas(1)).thenReturn(reservas);

        List<Reserva> resultado = useCase.getReservasPasajeroOrdenadas(1);

        System.out.println("✔ GET ORDENADAS TEST");
        System.out.println("Pasajero ID: 1");
        System.out.println("Reservas ordenadas: " + resultado.size());

        assertEquals(2, resultado.size());
    }

    // UPDATE
    @Test
    public void updateReserva_actualizaCorrectamente() {
        Reserva reserva = crearReserva(1, 1, 1, 550.0, "Reserva actualizada");

        useCase.updateReserva(reserva);

        System.out.println("✔ UPDATE TEST");
        System.out.println("Updated: Reserva con nuevo costo $" + reserva.getCosto());

        verify(mockRepository).update(reserva);
    }

    // DELETE
    @Test
    public void deleteReserva_eliminaCorrectamente() {
        Reserva reserva = crearReserva(1, 1, 1, 500.0, "Reserva a eliminar");

        useCase.deleteReserva(reserva);

        System.out.println("✔ DELETE TEST");
        System.out.println("Deleted: Reserva ID " + reserva.getIdReserva());

        verify(mockRepository).delete(reserva);
    }

    @Test
    public void deleteAll_eliminaTodas() {
        useCase.deleteAll();

        System.out.println("✔ DELETE ALL TEST");
        System.out.println("Deleted all reservas");

        verify(mockRepository).deleteAll();
    }

    // EDGE CASES
    @Test
    public void getAllReservas_cuandoNoHayDatos_retornaListaVacia() {
        when(mockRepository.getAll()).thenReturn(Arrays.asList());

        List<Reserva> resultado = useCase.getAllReservas();

        System.out.println("✔ EDGE CASE: GET ALL EMPTY");
        System.out.println("Response size: " + resultado.size());

        assertTrue(resultado.isEmpty());
    }

    @Test
    public void getReservaById_cuandoNoExiste_retornaNull() {
        when(mockRepository.getById(999)).thenReturn(null);

        Reserva resultado = useCase.getReservaById(999);

        System.out.println("✔ EDGE CASE: GET ID NOT FOUND");
        System.out.println("ID: 999");
        System.out.println("Response: null");

        assertNull(resultado);
    }

    @Test
    public void getReservasByPasajero_sinReservas_retornaListaVacia() {
        when(mockRepository.getByPasajero(999)).thenReturn(Arrays.asList());

        List<Reserva> resultado = useCase.getReservasByPasajero(999);

        System.out.println("✔ EDGE CASE: GET BY PASAJERO EMPTY");
        System.out.println("Pasajero ID: 999");
        System.out.println("Results: " + resultado.size());

        assertTrue(resultado.isEmpty());
    }

    @Test
    public void getReservasByVuelo_sinReservas_retornaListaVacia() {
        when(mockRepository.getByVuelo(999)).thenReturn(Arrays.asList());

        List<Reserva> resultado = useCase.getReservasByVuelo(999);

        System.out.println("✔ EDGE CASE: GET BY VUELO EMPTY");
        System.out.println("Vuelo ID: 999");
        System.out.println("Results: " + resultado.size());

        assertTrue(resultado.isEmpty());
    }

    // Helper
    private Reserva crearReserva(int id, int idPasajero, int idVuelo, double costo, String observacion) {
        Reserva r = new Reserva();
        r.setIdReserva(id);
        r.setIdPasajero(idPasajero);
        r.setIdVuelo(idVuelo);
        r.setCosto(costo);
        r.setFecha(new Date());
        r.setObservacion(observacion);
        return r;
    }
}