package com.example.grupo_04_tarea_12_ejercicio_01.ui.booking.reservas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Reserva;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.usecase.ReservaUseCase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ReservasViewModel extends ViewModel {

    private final ReservaUseCase reservaUseCase;
    private final ExecutorService executorService;

    private final MutableLiveData<List<Reserva>> _reservasLiveData = new MutableLiveData<>();
    public final LiveData<List<Reserva>> reservasLiveData = _reservasLiveData;

    private final MutableLiveData<Boolean> _isLoadingLiveData = new MutableLiveData<>(false);
    public final LiveData<Boolean> isLoadingLiveData = _isLoadingLiveData;

    private final MutableLiveData<String> _errorLiveData = new MutableLiveData<>();
    public final LiveData<String> errorLiveData = _errorLiveData;

    @Inject
    public ReservasViewModel(ReservaUseCase reservaUseCase) {
        this.reservaUseCase = reservaUseCase;
        this.executorService = Executors.newSingleThreadExecutor();
        loadReservas();
    }

    public void loadReservas() {
        _isLoadingLiveData.postValue(true);
        executorService.execute(() -> {
            try {
                List<Reserva> reservas = reservaUseCase.getAllReservas();
                _reservasLiveData.postValue(reservas);
                _isLoadingLiveData.postValue(false);
            } catch (Exception e) {
                _errorLiveData.postValue("Error al cargar reservas: " + e.getMessage());
                _isLoadingLiveData.postValue(false);
            }
        });
    }

    public void deleteReserva(Reserva reserva) {
        _isLoadingLiveData.postValue(true);
        executorService.execute(() -> {
            try {
                reservaUseCase.deleteReserva(reserva);
                loadReservas();
            } catch (Exception e) {
                _errorLiveData.postValue("Error al eliminar reserva: " + e.getMessage());
                _isLoadingLiveData.postValue(false);
            }
        });
    }

    public void saveReserva(Reserva reserva) {
        _isLoadingLiveData.postValue(true);
        executorService.execute(() -> {
            try {
                if (reserva.getIdReserva() > 0) {
                    reservaUseCase.updateReserva(reserva);
                } else {
                    reservaUseCase.insertReserva(reserva);
                }
                loadReservas();
            } catch (Exception e) {
                _errorLiveData.postValue("Error al guardar reserva: " + e.getMessage());
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