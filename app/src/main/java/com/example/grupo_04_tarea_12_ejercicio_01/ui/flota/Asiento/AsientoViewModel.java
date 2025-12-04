package com.example.grupo_04_tarea_12_ejercicio_01.ui.flota.Asiento;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Asiento;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.usecase.AsientoUseCase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AsientoViewModel extends ViewModel {

    private final AsientoUseCase asientoUseCase;
    private final ExecutorService executorService;

    // LiveData para la lista de asientos (lo observa el Fragment)
    private final MutableLiveData<List<Asiento>> _asientosLiveData = new MutableLiveData<>();
    public final LiveData<List<Asiento>> asientosLiveData = _asientosLiveData;

    // LiveData para el estado de carga (opcional, para ProgressBar)
    private final MutableLiveData<Boolean> _isLoadingLiveData = new MutableLiveData<>(false);
    public final LiveData<Boolean> isLoadingLiveData = _isLoadingLiveData;

    // LiveData para notificar errores (lo observa el Fragment para mostrar Toasts)
    private final MutableLiveData<String> _errorLiveData = new MutableLiveData<>();
    public final LiveData<String> errorLiveData = _errorLiveData;

    @Inject
    public AsientoViewModel(AsientoUseCase asientoUseCase) {
        this.asientoUseCase = asientoUseCase;
        // Executor para operaciones en segundo plano (simulando corrutinas en Java)
        this.executorService = Executors.newSingleThreadExecutor();

        // Cargar datos automáticamente al crear el ViewModel
        loadAsientos();
    }

    /**
     * Carga la lista completa de asientos desde el repositorio.
     */
    public void loadAsientos() {
        _isLoadingLiveData.postValue(true);
        executorService.execute(() -> {
            try {
                List<Asiento> asientos = asientoUseCase.getAllAsientos();
                _asientosLiveData.postValue(asientos);
                _isLoadingLiveData.postValue(false);
            } catch (Exception e) {
                _errorLiveData.postValue("Error al cargar asientos: " + e.getMessage());
                _isLoadingLiveData.postValue(false);
            }
        });
    }

    /**
     * Elimina un asiento y recarga la lista.
     */
    public void deleteAsiento(Asiento asiento) {
        _isLoadingLiveData.postValue(true);
        executorService.execute(() -> {
            try {
                asientoUseCase.deleteAsiento(asiento);
                loadAsientos(); // Refrescar la lista
            } catch (Exception e) {
                _errorLiveData.postValue("Error al eliminar asiento: " + e.getMessage());
                _isLoadingLiveData.postValue(false);
            }
        });
    }

    /**
     * Guarda un asiento (inserta si es nuevo, actualiza si ya existe).
     * La lógica se basa en si el ID es 0 o mayor.
     */
    public void saveAsiento(Asiento asiento) {
        _isLoadingLiveData.postValue(true);
        executorService.execute(() -> {
            try {
                if (asiento.getIdAsiento() > 0) {
                    // Si tiene ID, es una actualización
                    asientoUseCase.updateAsiento(asiento);
                } else {
                    // Si el ID es 0, es una inserción nueva
                    asientoUseCase.insertAsiento(asiento);
                }
                loadAsientos(); // Refrescar la lista
            } catch (Exception e) {
                _errorLiveData.postValue("Error al guardar asiento: " + e.getMessage());
                _isLoadingLiveData.postValue(false);
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdown(); // Limpieza de recursos
    }
}