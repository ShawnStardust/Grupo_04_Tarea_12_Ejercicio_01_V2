package com.example.grupo_04_tarea_12_ejercicio_01.ui.clientes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Aerolinea;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Pais;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Pasajero;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.usecase.AerolineaUseCase;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.usecase.PaisUseCase;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.usecase.PasajeroUseCase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ClientesViewModel extends ViewModel {

    private final PasajeroUseCase pasajeroUseCase;
    private final PaisUseCase paisUseCase;
    private final AerolineaUseCase aerolineaUseCase;

    private final MutableLiveData<List<Pasajero>> listaPasajeros = new MutableLiveData<>();
    private final MutableLiveData<List<Pais>> listaPaises = new MutableLiveData<>();
    private final MutableLiveData<List<Aerolinea>> listaAerolineas = new MutableLiveData<>();

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Inject
    public ClientesViewModel(PasajeroUseCase pasajeroUseCase, PaisUseCase paisUseCase, AerolineaUseCase aerolineaUseCase) {
        this.pasajeroUseCase = pasajeroUseCase;
        this.paisUseCase = paisUseCase;
        this.aerolineaUseCase = aerolineaUseCase;

        cargarPasajeros();
        cargarPaises();
        cargarAerolineas();
    }

    public LiveData<List<Pasajero>> getListaPasajeros() { return listaPasajeros; }
    public LiveData<List<Pais>> getListaPaises() { return listaPaises; }
    public LiveData<List<Aerolinea>> getListaAerolineas() { return listaAerolineas; }

    public void cargarPasajeros() {
        executor.execute(() -> {
            List<Pasajero> resultado = pasajeroUseCase.getAllPasajeros();
            listaPasajeros.postValue(resultado);
        });
    }

    public void cargarPaises() {
        executor.execute(() -> {
            List<Pais> resultado = paisUseCase.getAllPaises();
            listaPaises.postValue(resultado);
        });
    }

    public void registrarPasajero(Pasajero pasajero) {
        executor.execute(() -> {
            pasajeroUseCase.insertPasajero(pasajero);
            cargarPasajeros();
        });
    }

    public void actualizarPasajero(Pasajero pasajero) {
        executor.execute(() -> {
            pasajeroUseCase.updatePasajero(pasajero);
            cargarPasajeros();
        });
    }

    public void eliminarPasajero(Pasajero pasajero) {
        executor.execute(() -> {
            pasajeroUseCase.deletePasajero(pasajero);
            cargarPasajeros();
        });
    }

    public void registrarPais(Pais pais) {
        executor.execute(() -> {
            paisUseCase.insertPais(pais);
            cargarPaises();
        });
    }

    public void actualizarPais(Pais pais) {
        executor.execute(() -> {
            paisUseCase.updatePais(pais);
            cargarPaises();
        });
    }

    public void eliminarPais(Pais pais) {
        executor.execute(() -> {
            paisUseCase.deletePais(pais);
            cargarPaises();
        });
    }

    public void cargarAerolineas() {
        executor.execute(() -> {
            List<Aerolinea> resultado = aerolineaUseCase.getAllAerolineas();
            listaAerolineas.postValue(resultado);
        });
    }

    public void registrarAerolinea(Aerolinea aerolinea) {
        executor.execute(() -> {
            aerolineaUseCase.insertAerolinea(aerolinea);
            cargarAerolineas();
        });
    }

    public void actualizarAerolinea(Aerolinea aerolinea) {
        executor.execute(() -> {
            aerolineaUseCase.updateAerolinea(aerolinea);
            cargarAerolineas();
        });
    }

    public void eliminarAerolinea(Aerolinea aerolinea) {
        executor.execute(() -> {
            aerolineaUseCase.deleteAerolinea(aerolinea);
            cargarAerolineas();
        });
    }
}