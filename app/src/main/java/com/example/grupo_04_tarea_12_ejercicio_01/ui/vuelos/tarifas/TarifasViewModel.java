package com.example.grupo_04_tarea_12_ejercicio_01.ui.vuelos.tarifas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Tarifa;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.usecase.TarifaUseCase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class TarifasViewModel extends ViewModel {

    private final TarifaUseCase tarifaUseCase;
    private final ExecutorService executorService;

    private final MutableLiveData<List<Tarifa>> _tarifasLiveData = new MutableLiveData<>();
    public final LiveData<List<Tarifa>> tarifasLiveData = _tarifasLiveData;

    private final MutableLiveData<Boolean> _isLoadingLiveData = new MutableLiveData<>(false);
    public final LiveData<Boolean> isLoadingLiveData = _isLoadingLiveData;

    private final MutableLiveData<String> _errorLiveData = new MutableLiveData<>();
    public final LiveData<String> errorLiveData = _errorLiveData;

    @Inject
    public TarifasViewModel(TarifaUseCase tarifaUseCase) {
        this.tarifaUseCase = tarifaUseCase;
        this.executorService = Executors.newSingleThreadExecutor();
        loadTarifas();
    }

    public void loadTarifas() {
        _isLoadingLiveData.postValue(true);
        executorService.execute(() -> {
            try {
                List<Tarifa> tarifas = tarifaUseCase.getAllTarifas();
                _tarifasLiveData.postValue(tarifas);
                _isLoadingLiveData.postValue(false);
            } catch (Exception e) {
                _errorLiveData.postValue("Error al cargar tarifas: " + e.getMessage());
                _isLoadingLiveData.postValue(false);
            }
        });
    }

    public void deleteTarifa(Tarifa tarifa) {
        _isLoadingLiveData.postValue(true);
        executorService.execute(() -> {
            try {
                tarifaUseCase.deleteTarifa(tarifa);
                loadTarifas();
            } catch (Exception e) {
                _errorLiveData.postValue("Error al eliminar tarifa: " + e.getMessage());
                _isLoadingLiveData.postValue(false);
            }
        });
    }

    public void saveTarifa(Tarifa tarifa) {
        _isLoadingLiveData.postValue(true);
        executorService.execute(() -> {
            try {
                if (tarifa.getIdTarifa() > 0) {
                    tarifaUseCase.updateTarifa(tarifa);
                } else {
                    tarifaUseCase.insertTarifa(tarifa);
                }
                loadTarifas();
            } catch (Exception e) {
                _errorLiveData.postValue("Error al guardar tarifa: " + e.getMessage());
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