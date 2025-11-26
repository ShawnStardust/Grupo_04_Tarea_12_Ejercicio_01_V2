package com.example.grupo_04_tarea_12_ejercicio_01.ui.booking.pagos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Pago;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.usecase.PagoUseCase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class PagosViewModel extends ViewModel {

    private final PagoUseCase pagoUseCase;
    private final ExecutorService executorService;

    private final MutableLiveData<List<Pago>> _pagosLiveData = new MutableLiveData<>();
    public final LiveData<List<Pago>> pagosLiveData = _pagosLiveData;

    private final MutableLiveData<Boolean> _isLoadingLiveData = new MutableLiveData<>(false);
    public final LiveData<Boolean> isLoadingLiveData = _isLoadingLiveData;

    private final MutableLiveData<String> _errorLiveData = new MutableLiveData<>();
    public final LiveData<String> errorLiveData = _errorLiveData;

    @Inject
    public PagosViewModel(PagoUseCase pagoUseCase) {
        this.pagoUseCase = pagoUseCase;
        this.executorService = Executors.newSingleThreadExecutor();
        loadPagos();
    }

    public void loadPagos() {
        _isLoadingLiveData.postValue(true);
        executorService.execute(() -> {
            try {
                List<Pago> pagos = pagoUseCase.getAllPagos();
                _pagosLiveData.postValue(pagos);
                _isLoadingLiveData.postValue(false);
            } catch (Exception e) {
                _errorLiveData.postValue("Error al cargar pagos: " + e.getMessage());
                _isLoadingLiveData.postValue(false);
            }
        });
    }

    public void deletePago(Pago pago) {
        _isLoadingLiveData.postValue(true);
        executorService.execute(() -> {
            try {
                pagoUseCase.deletePago(pago);
                loadPagos();
            } catch (Exception e) {
                _errorLiveData.postValue("Error al eliminar pago: " + e.getMessage());
                _isLoadingLiveData.postValue(false);
            }
        });
    }

    public void savePago(Pago pago) {
        _isLoadingLiveData.postValue(true);
        executorService.execute(() -> {
            try {
                if (pago.getIdPago() > 0) {
                    pagoUseCase.updatePago(pago);
                } else {
                    pagoUseCase.insertPago(pago);
                }
                loadPagos();
            } catch (Exception e) {
                _errorLiveData.postValue("Error al guardar pago: " + e.getMessage());
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