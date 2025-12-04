package com.example.grupo_04_tarea_12_ejercicio_01.ui.flota.Aeropuerto;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Aeropuerto;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.usecase.AeropuertoUseCase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AeropuertoViewModel extends ViewModel {

    private final AeropuertoUseCase aeropuertoUseCase;
    private final ExecutorService executorService;

    // LiveData para la lista de aeropuertos (datos observables)
    private final MutableLiveData<List<Aeropuerto>> _aeropuertosLiveData = new MutableLiveData<>();
    public final LiveData<List<Aeropuerto>> aeropuertosLiveData = _aeropuertosLiveData;

    // LiveData para el estado de carga
    private final MutableLiveData<Boolean> _isLoadingLiveData = new MutableLiveData<>(false);
    public final LiveData<Boolean> isLoadingLiveData = _isLoadingLiveData;

    // LiveData para notificar errores
    private final MutableLiveData<String> _errorLiveData = new MutableLiveData<>();
    public final LiveData<String> errorLiveData = _errorLiveData;

    @Inject
    public AeropuertoViewModel(AeropuertoUseCase aeropuertoUseCase) {
        this.aeropuertoUseCase = aeropuertoUseCase;
        // Se usa un ExecutorService para correr las operaciones de repositorio en segundo plano
        this.executorService = Executors.newSingleThreadExecutor();
    }

    /**
     * Carga todos los aeropuertos de forma asíncrona.
     */
    public void loadAeropuertos() {
        _isLoadingLiveData.postValue(true);
        executorService.execute(() -> {
            try {
                // Llama al UseCase para obtener la lista
                List<Aeropuerto> aeropuertos = aeropuertoUseCase.getAllAeropuertos();
                _aeropuertosLiveData.postValue(aeropuertos);
                _isLoadingLiveData.postValue(false);
            } catch (Exception e) {
                _errorLiveData.postValue("Error al cargar aeropuertos: " + e.getMessage());
                _isLoadingLiveData.postValue(false);
            }
        });
    }

    /**
     * Elimina un aeropuerto de forma asíncrona.
     */
    public void deleteAeropuerto(Aeropuerto aeropuerto) {
        _isLoadingLiveData.postValue(true);
        executorService.execute(() -> {
            try {
                aeropuertoUseCase.deleteAeropuerto(aeropuerto);
                // Recargar la lista para actualizar la vista
                loadAeropuertos();
            } catch (Exception e) {
                _errorLiveData.postValue("Error al eliminar aeropuerto: " + e.getMessage());
                _isLoadingLiveData.postValue(false);
            }
        });
    }

    /**
     * Guarda (inserta si es nuevo, actualiza si existe) un aeropuerto de forma asíncrona.
     * La lógica de Upsert se maneja aquí al revisar el ID.
     */
    public void saveAeropuerto(Aeropuerto aeropuerto) {
        _isLoadingLiveData.postValue(true);
        executorService.execute(() -> {
            try {
                if (aeropuerto.getIdAeropuerto() > 0) {
                    // Si tiene ID, es una actualización
                    aeropuertoUseCase.updateAeropuerto(aeropuerto);
                } else {
                    // Si no tiene ID (o es 0), es una inserción
                    aeropuertoUseCase.insertAeropuerto(aeropuerto);
                }
                loadAeropuertos(); // Recargar la lista
            } catch (Exception e) {
                _errorLiveData.postValue("Error al guardar aeropuerto: " + e.getMessage());
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