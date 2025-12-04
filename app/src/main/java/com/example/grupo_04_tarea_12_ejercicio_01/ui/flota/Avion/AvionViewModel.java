package com.example.grupo_04_tarea_12_ejercicio_01.ui.flota.Avion;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Avion;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.usecase.AvionUseCase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AvionViewModel extends ViewModel {

    private final AvionUseCase avionUseCase;
    private final ExecutorService executorService;

    // LiveData para la lista de aviones
    private final MutableLiveData<List<Avion>> _avionesLiveData = new MutableLiveData<>();
    public final LiveData<List<Avion>> avionesLiveData = _avionesLiveData;

    // LiveData para el estado de carga
    private final MutableLiveData<Boolean> _isLoadingLiveData = new MutableLiveData<>(false);
    public final LiveData<Boolean> isLoadingLiveData = _isLoadingLiveData;

    // LiveData para notificar errores o mensajes
    private final MutableLiveData<String> _errorLiveData = new MutableLiveData<>();
    public final LiveData<String> errorLiveData = _errorLiveData;

    @Inject
    public AvionViewModel(AvionUseCase avionUseCase) {
        this.avionUseCase = avionUseCase;
        this.executorService = Executors.newSingleThreadExecutor();

        // Cargar la lista inicial al crear el ViewModel
        loadAviones();
    }

    /**
     * Carga todos los aviones de forma asíncrona.
     */
    public void loadAviones() {
        _isLoadingLiveData.postValue(true);
        executorService.execute(() -> {
            try {
                List<Avion> aviones = avionUseCase.getAllAviones();
                _avionesLiveData.postValue(aviones);
                _isLoadingLiveData.postValue(false);
            } catch (Exception e) {
                _errorLiveData.postValue("Error al cargar aviones: " + e.getMessage());
                _isLoadingLiveData.postValue(false);
            }
        });
    }

    /**
     * Elimina un avión y recarga la lista.
     */
    public void deleteAvion(Avion avion) {
        _isLoadingLiveData.postValue(true);
        executorService.execute(() -> {
            try {
                avionUseCase.deleteAvion(avion);
                loadAviones(); // Actualizar la lista
            } catch (Exception e) {
                _errorLiveData.postValue("Error al eliminar avión: " + e.getMessage());
                _isLoadingLiveData.postValue(false);
            }
        });
    }

    /**
     * Guarda un avión (inserta si es nuevo, actualiza si ya existe).
     * La lógica se basa en si el ID es 0 o mayor.
     */
    public void saveAvion(Avion avion) {
        _isLoadingLiveData.postValue(true);
        executorService.execute(() -> {
            try {
                if (avion.getIdAvion() > 0) {
                    // Actualización
                    avionUseCase.updateAvion(avion);
                } else {
                    // Inserción
                    avionUseCase.insertAvion(avion);
                }
                loadAviones(); // Actualizar la lista
            } catch (Exception e) {
                _errorLiveData.postValue("Error al guardar avión: " + e.getMessage());
                _isLoadingLiveData.postValue(false);
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdown();
    }
}