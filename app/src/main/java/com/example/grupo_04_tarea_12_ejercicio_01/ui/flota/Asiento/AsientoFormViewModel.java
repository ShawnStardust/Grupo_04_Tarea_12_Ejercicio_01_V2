package com.example.grupo_04_tarea_12_ejercicio_01.ui.flota.Asiento;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Vuelo;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.usecase.VueloUseCase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AsientoFormViewModel extends ViewModel {

    private final VueloUseCase vueloUseCase;
    private final ExecutorService executorService;

    private final MutableLiveData<List<Vuelo>> _vuelosLiveData = new MutableLiveData<>();
    public final LiveData<List<Vuelo>> vuelosLiveData = _vuelosLiveData;

    private final MutableLiveData<String> _errorLiveData = new MutableLiveData<>();
    public final LiveData<String> errorLiveData = _errorLiveData;

    @Inject
    public AsientoFormViewModel(VueloUseCase vueloUseCase) {
        this.vueloUseCase = vueloUseCase;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public void loadVuelos() {
        executorService.execute(() -> {
            try {
                List<Vuelo> vuelos = vueloUseCase.getAllVuelos();
                _vuelosLiveData.postValue(vuelos);
            } catch (Exception e) {
                _errorLiveData.postValue("Error al cargar vuelos: " + e.getMessage());
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdown();
    }
}