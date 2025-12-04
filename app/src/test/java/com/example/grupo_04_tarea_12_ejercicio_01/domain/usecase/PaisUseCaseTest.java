package com.example.grupo_04_tarea_12_ejercicio_01.domain.usecase;

import com.example.grupo_04_tarea_12_ejercicio_01.domain.Repository.PaisRepository;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Pais;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PaisUseCaseTest {

    @Mock
    private PaisRepository mockRepository;

    private PaisUseCase useCase;

    @Before
    public void setUp() {
        useCase = new PaisUseCase(mockRepository);
    }

    // CREATE
    @Test
    public void insertPais_guardaCorrectamente() {
        Pais pais = crearPais(1, "Perú");

        useCase.insertPais(pais);

        System.out.println("✔ INSERT TEST");
        System.out.println("Request: " + pais.getNombre());
        System.out.println("Status: OK (insert ejecutado)");

        verify(mockRepository).insert(pais);
    }

    // READ - ALL
    @Test
    public void getAllPaises_retornaTodosLosPaises() {
        List<Pais> paises = Arrays.asList(
                crearPais(1, "Perú"),
                crearPais(2, "Colombia"),
                crearPais(3, "Chile")
        );

        when(mockRepository.getAll()).thenReturn(paises);

        List<Pais> resultado = useCase.getAllPaises();

        System.out.println("✔ GET ALL TEST");
        System.out.println("Response size: " + resultado.size());
        resultado.forEach(p -> System.out.println("- " + p.getNombre()));

        assertEquals(3, resultado.size());
    }


    // UPDATE
    @Test
    public void updatePais_actualizaCorrectamente() {
        Pais pais = crearPais(1, "Perú Actualizado");

        useCase.updatePais(pais);

        System.out.println("✔ UPDATE TEST");
        System.out.println("Updated: " + pais.getNombre());

        verify(mockRepository).update(pais);
    }

    // DELETE
    @Test
    public void deletePais_eliminaCorrectamente() {
        Pais pais = crearPais(1, "Perú");

        useCase.deletePais(pais);

        System.out.println("✔ DELETE TEST");
        System.out.println("Deleted: " + pais.getNombre());

        verify(mockRepository).delete(pais);
    }

    // EDGE CASES
    @Test
    public void getAllPaises_cuandoNoHayDatos_retornaListaVacia() {
        when(mockRepository.getAll()).thenReturn(Arrays.asList());

        List<Pais> resultado = useCase.getAllPaises();

        System.out.println("✔ EDGE CASE: GET ALL EMPTY");
        System.out.println("Response size: " + resultado.size());

        assertTrue(resultado.isEmpty());
    }

    // Helper
    private Pais crearPais(int id, String nombre) {
        Pais p = new Pais();
        p.setIdPais(id);
        p.setNombre(nombre);
        return p;
    }
}
