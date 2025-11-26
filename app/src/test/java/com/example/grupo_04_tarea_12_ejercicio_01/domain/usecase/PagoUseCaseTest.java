package com.example.grupo_04_tarea_12_ejercicio_01.domain.usecase;

import com.example.grupo_04_tarea_12_ejercicio_01.domain.Repository.PagoRepository;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Pago;

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
public class PagoUseCaseTest {

    @Mock
    private PagoRepository mockRepository;

    private PagoUseCase useCase;

    @Before
    public void setUp() {
        useCase = new PagoUseCase(mockRepository);
    }

    // CREATE
    @Test
    public void insertPago_guardaCorrectamente() {
        Pago pago = crearPago(1, 1, "Factura", "F001-00001", 500.0, 90.0);

        useCase.insertPago(pago);

        System.out.println("✔ INSERT TEST");
        System.out.println("Request: " + pago.getNumComprobante());
        System.out.println("Status: OK (insert ejecutado)");

        verify(mockRepository).insert(pago);
    }

    // READ - ALL
    @Test
    public void getAllPagos_retornaTodosLosPagos() {
        List<Pago> pagos = Arrays.asList(
                crearPago(1, 1, "Factura", "F001-00001", 500.0, 90.0),
                crearPago(2, 1, "Boleta", "B001-00001", 300.0, 54.0),
                crearPago(3, 2, "Factura", "F001-00002", 750.0, 135.0)
        );

        when(mockRepository.getAll()).thenReturn(pagos);

        List<Pago> resultado = useCase.getAllPagos();

        System.out.println("✔ GET ALL TEST");
        System.out.println("Response size: " + resultado.size());
        resultado.forEach(p -> System.out.println("- " + p.getNumComprobante() + ": $" + p.getMonto()));

        assertEquals(3, resultado.size());
    }

    // READ - ONE
    @Test
    public void getPagoById_retornaPagoCorrecto() {
        Pago esperado = crearPago(1, 1, "Factura", "F001-00001", 500.0, 90.0);
        when(mockRepository.getById(1)).thenReturn(esperado);

        Pago resultado = useCase.getPagoById(1);

        System.out.println("✔ GET BY ID TEST");
        System.out.println("Request: 1");
        System.out.println("Response: " + resultado.getNumComprobante());

        assertEquals("F001-00001", resultado.getNumComprobante());
    }

    // READ - BY RESERVA
    @Test
    public void getPagosByReserva_retornaPagosDeReserva() {
        List<Pago> pagos = Arrays.asList(
                crearPago(1, 1, "Factura", "F001-00001", 300.0, 54.0),
                crearPago(2, 1, "Factura", "F001-00002", 200.0, 36.0)
        );
        when(mockRepository.getByReserva(1)).thenReturn(pagos);

        List<Pago> resultado = useCase.getPagosByReserva(1);

        System.out.println("✔ GET BY RESERVA TEST");
        System.out.println("Reserva ID: 1");
        System.out.println("Pagos encontrados: " + resultado.size());

        assertEquals(2, resultado.size());
    }

    // READ - BY COMPROBANTE
    @Test
    public void getPagoByComprobante_retornaPagoCorrecto() {
        Pago esperado = crearPago(1, 1, "Factura", "F001-00001", 500.0, 90.0);
        when(mockRepository.getByComprobante("F001-00001")).thenReturn(esperado);

        Pago resultado = useCase.getPagoByComprobante("F001-00001");

        System.out.println("✔ GET BY COMPROBANTE TEST");
        System.out.println("Request: F001-00001");
        System.out.println("Response: $" + resultado.getMonto());

        assertEquals(500.0, resultado.getMonto(), 0.01);
    }

    // BUSINESS LOGIC
    @Test
    public void getTotalPagadoReserva_calculaCorrectamente() {
        when(mockRepository.getTotalPagadoReserva(1)).thenReturn(1250.0);

        double total = useCase.getTotalPagadoReserva(1);

        System.out.println("✔ GET TOTAL PAGADO TEST");
        System.out.println("Reserva ID: 1");
        System.out.println("Total pagado: $" + total);

        assertEquals(1250.0, total, 0.01);
        verify(mockRepository).getTotalPagadoReserva(1);
    }

    @Test
    public void getTotalPagadoReserva_sinPagos_retornaCero() {
        when(mockRepository.getTotalPagadoReserva(999)).thenReturn(0.0);

        double total = useCase.getTotalPagadoReserva(999);

        System.out.println("✔ GET TOTAL PAGADO EMPTY TEST");
        System.out.println("Reserva ID: 999");
        System.out.println("Total pagado: $" + total);

        assertEquals(0.0, total, 0.01);
    }

    // UPDATE
    @Test
    public void updatePago_actualizaCorrectamente() {
        Pago pago = crearPago(1, 1, "Factura", "F001-00001", 550.0, 99.0);

        useCase.updatePago(pago);

        System.out.println("✔ UPDATE TEST");
        System.out.println("Updated: " + pago.getNumComprobante() + " - $" + pago.getMonto());

        verify(mockRepository).update(pago);
    }

    // DELETE
    @Test
    public void deletePago_eliminaCorrectamente() {
        Pago pago = crearPago(1, 1, "Factura", "F001-00001", 500.0, 90.0);

        useCase.deletePago(pago);

        System.out.println("✔ DELETE TEST");
        System.out.println("Deleted: " + pago.getNumComprobante());

        verify(mockRepository).delete(pago);
    }

    @Test
    public void deleteAll_eliminaTodos() {
        useCase.deleteAll();

        System.out.println("✔ DELETE ALL TEST");
        System.out.println("Deleted all pagos");

        verify(mockRepository).deleteAll();
    }

    // EDGE CASES
    @Test
    public void getAllPagos_cuandoNoHayDatos_retornaListaVacia() {
        when(mockRepository.getAll()).thenReturn(Arrays.asList());

        List<Pago> resultado = useCase.getAllPagos();

        System.out.println("✔ EDGE CASE: GET ALL EMPTY");
        System.out.println("Response size: " + resultado.size());

        assertTrue(resultado.isEmpty());
    }

    @Test
    public void getPagoById_cuandoNoExiste_retornaNull() {
        when(mockRepository.getById(999)).thenReturn(null);

        Pago resultado = useCase.getPagoById(999);

        System.out.println("✔ EDGE CASE: GET ID NOT FOUND");
        System.out.println("ID: 999");
        System.out.println("Response: null");

        assertNull(resultado);
    }

    @Test
    public void getPagosByReserva_sinPagos_retornaListaVacia() {
        when(mockRepository.getByReserva(999)).thenReturn(Arrays.asList());

        List<Pago> resultado = useCase.getPagosByReserva(999);

        System.out.println("✔ EDGE CASE: GET BY RESERVA EMPTY");
        System.out.println("Reserva ID: 999");
        System.out.println("Results: " + resultado.size());

        assertTrue(resultado.isEmpty());
    }

    @Test
    public void getPagoByComprobante_cuandoNoExiste_retornaNull() {
        when(mockRepository.getByComprobante("XXX-999")).thenReturn(null);

        Pago resultado = useCase.getPagoByComprobante("XXX-999");

        System.out.println("✔ EDGE CASE: GET BY COMPROBANTE NOT FOUND");
        System.out.println("Comprobante: XXX-999");
        System.out.println("Response: null");

        assertNull(resultado);
    }

    // Helper
    private Pago crearPago(int id, int idReserva, String tipoComprobante,
                           String numComprobante, double monto, double impuesto) {
        Pago p = new Pago();
        p.setIdPago(id);
        p.setIdReserva(idReserva);
        p.setFecha(new Date());
        p.setTipoComprobante(tipoComprobante);
        p.setNumComprobante(numComprobante);
        p.setMonto(monto);
        p.setImpuesto(impuesto);
        return p;
    }
}