package com.example.grupo_04_tarea_12_ejercicio_01.ui.vuelos.vuelo;

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
public class VuelosViewModel extends ViewModel {

    private final VueloUseCase vueloUseCase;
    private final ExecutorService executorService;

    private final MutableLiveData<List<Vuelo>> _vuelosLiveData = new MutableLiveData<>();
    public final LiveData<List<Vuelo>> vuelosLiveData = _vuelosLiveData;

    private final MutableLiveData<Boolean> _isLoadingLiveData = new MutableLiveData<>(false);
    public final LiveData<Boolean> isLoadingLiveData = _isLoadingLiveData;

    private final MutableLiveData<String> _errorLiveData = new MutableLiveData<>();
    public final LiveData<String> errorLiveData = _errorLiveData;

    @Inject
    public VuelosViewModel(VueloUseCase vueloUseCase) {
        this.vueloUseCase = vueloUseCase;
        this.executorService = Executors.newSingleThreadExecutor();

        loadVuelos();
    }

    public void loadVuelos() {
        _isLoadingLiveData.postValue(true);
        executorService.execute(() -> {
            try {

                List<Vuelo> vuelos = vueloUseCase.getAllVuelos();
                _vuelosLiveData.postValue(vuelos);
                _isLoadingLiveData.postValue(false);
            } catch (Exception e) {
                _errorLiveData.postValue("Error al cargar vuelos: " + e.getMessage());
                _isLoadingLiveData.postValue(false);
            }
        });
    }

    public void deleteVuelo(Vuelo vuelo) {
        _isLoadingLiveData.postValue(true);
        executorService.execute(() -> {
            try {
                vueloUseCase.deleteVuelo(vuelo);
                loadVuelos();
            } catch (Exception e) {
                _errorLiveData.postValue("Error al eliminar vuelo: " + e.getMessage());
                _isLoadingLiveData.postValue(false);
            }
        });
    }

    public void saveVuelo(Vuelo vuelo) {
        _isLoadingLiveData.postValue(true);
        executorService.execute(() -> {
            try {
                if (vuelo.getIdVuelo() > 0) {
                    vueloUseCase.updateVuelo(vuelo);
                } else {
                    vueloUseCase.insertVuelo(vuelo);
                }
                loadVuelos();
            } catch (Exception e) {
                _errorLiveData.postValue("Error al guardar vuelo: " + e.getMessage());
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