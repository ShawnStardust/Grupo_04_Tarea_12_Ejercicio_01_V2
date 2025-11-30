package com.example.grupo_04_tarea_12_ejercicio_01.ui.booking.reservas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Aeropuerto;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Pasajero;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Reserva;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Vuelo;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.usecase.AeropuertoUseCase;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.usecase.PasajeroUseCase;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.usecase.ReservaUseCase;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.usecase.VueloUseCase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ReservasViewModel extends ViewModel {

    private final ReservaUseCase reservaUseCase;
    private final PasajeroUseCase pasajeroUseCase;
    private final VueloUseCase vueloUseCase;
    private final AeropuertoUseCase aeropuertoUseCase;
    private final ExecutorService executorService;

    // LiveData para Reservas
    private final MutableLiveData<List<Reserva>> _reservasLiveData = new MutableLiveData<>();
    public final LiveData<List<Reserva>> reservasLiveData = _reservasLiveData;

    // LiveData para Pasajeros
    private final MutableLiveData<List<Pasajero>> _pasajerosLiveData = new MutableLiveData<>();
    public final LiveData<List<Pasajero>> pasajerosLiveData = _pasajerosLiveData;

    // LiveData para Vuelos
    private final MutableLiveData<List<Vuelo>> _vuelosLiveData = new MutableLiveData<>();
    public final LiveData<List<Vuelo>> vuelosLiveData = _vuelosLiveData;

    // LiveData para Aeropuertos
    private final MutableLiveData<List<Aeropuerto>> _aeropuertosLiveData = new MutableLiveData<>();
    public final LiveData<List<Aeropuerto>> aeropuertosLiveData = _aeropuertosLiveData;

    // LiveData para Loading
    private final MutableLiveData<Boolean> _isLoadingLiveData = new MutableLiveData<>(false);
    public final LiveData<Boolean> isLoadingLiveData = _isLoadingLiveData;

    // LiveData para Errores
    private final MutableLiveData<String> _errorLiveData = new MutableLiveData<>();
    public final LiveData<String> errorLiveData = _errorLiveData;

    // LiveData para mensajes de éxito
    private final MutableLiveData<String> _successLiveData = new MutableLiveData<>();
    public final LiveData<String> successLiveData = _successLiveData;

    @Inject
    public ReservasViewModel(ReservaUseCase reservaUseCase,
                             PasajeroUseCase pasajeroUseCase,
                             VueloUseCase vueloUseCase,
                             AeropuertoUseCase aeropuertoUseCase) {
        this.reservaUseCase = reservaUseCase;
        this.pasajeroUseCase = pasajeroUseCase;
        this.vueloUseCase = vueloUseCase;
        this.aeropuertoUseCase = aeropuertoUseCase;
        this.executorService = Executors.newSingleThreadExecutor();
        loadAllData();
    }

    /**
     * Carga todos los datos necesarios (Reservas, Pasajeros, Vuelos y Aeropuertos)
     */
    public void loadAllData() {
        loadReservas();
        loadPasajeros();
        loadVuelos();
        loadAeropuertos();
    }

    /**
     * Carga todas las reservas
     */
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

    /**
     * Carga todos los pasajeros
     */
    public void loadPasajeros() {
        executorService.execute(() -> {
            try {
                List<Pasajero> pasajeros = pasajeroUseCase.getAllPasajeros();
                _pasajerosLiveData.postValue(pasajeros);
            } catch (Exception e) {
                _errorLiveData.postValue("Error al cargar pasajeros: " + e.getMessage());
            }
        });
    }

    /**
     * Carga todos los vuelos
     */
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

    /**
     * Carga todos los aeropuertos
     */
    public void loadAeropuertos() {
        executorService.execute(() -> {
            try {
                List<Aeropuerto> aeropuertos = aeropuertoUseCase.getAllAeropuertos();
                _aeropuertosLiveData.postValue(aeropuertos);
            } catch (Exception e) {
                _errorLiveData.postValue("Error al cargar aeropuertos: " + e.getMessage());
            }
        });
    }

    /**
     * Elimina una reserva
     */
    public void deleteReserva(Reserva reserva) {
        _isLoadingLiveData.postValue(true);
        executorService.execute(() -> {
            try {
                reservaUseCase.deleteReserva(reserva);
                _successLiveData.postValue("Reserva eliminada correctamente");
                loadReservas();
            } catch (Exception e) {
                _errorLiveData.postValue("Error al eliminar reserva: " + e.getMessage());
                _isLoadingLiveData.postValue(false);
            }
        });
    }

    /**
     * Guarda o actualiza una reserva
     */
    public void saveReserva(Reserva reserva) {
        _isLoadingLiveData.postValue(true);
        executorService.execute(() -> {
            try {
                if (reserva.getIdReserva() > 0) {
                    reservaUseCase.updateReserva(reserva);
                    _successLiveData.postValue("Reserva actualizada correctamente");
                } else {
                    reservaUseCase.insertReserva(reserva);
                    _successLiveData.postValue("Reserva creada correctamente");
                }
                loadReservas();
            } catch (Exception e) {
                _errorLiveData.postValue("Error al guardar reserva: " + e.getMessage());
                _isLoadingLiveData.postValue(false);
            }
        });
    }

    /**
     * Obtiene reservas por pasajero
     */
    public void loadReservasByPasajero(int idPasajero) {
        _isLoadingLiveData.postValue(true);
        executorService.execute(() -> {
            try {
                List<Reserva> reservas = reservaUseCase.getReservasByPasajero(idPasajero);
                _reservasLiveData.postValue(reservas);
                _isLoadingLiveData.postValue(false);
            } catch (Exception e) {
                _errorLiveData.postValue("Error al cargar reservas del pasajero: " + e.getMessage());
                _isLoadingLiveData.postValue(false);
            }
        });
    }

    /**
     * Obtiene reservas por vuelo
     */
    public void loadReservasByVuelo(int idVuelo) {
        _isLoadingLiveData.postValue(true);
        executorService.execute(() -> {
            try {
                List<Reserva> reservas = reservaUseCase.getReservasByVuelo(idVuelo);
                _reservasLiveData.postValue(reservas);
                _isLoadingLiveData.postValue(false);
            } catch (Exception e) {
                _errorLiveData.postValue("Error al cargar reservas del vuelo: " + e.getMessage());
                _isLoadingLiveData.postValue(false);
            }
        });
    }

    /**
     * Obtiene reservas de un pasajero ordenadas
     */
    public void loadReservasPasajeroOrdenadas(int idPasajero) {
        _isLoadingLiveData.postValue(true);
        executorService.execute(() -> {
            try {
                List<Reserva> reservas = reservaUseCase.getReservasPasajeroOrdenadas(idPasajero);
                _reservasLiveData.postValue(reservas);
                _isLoadingLiveData.postValue(false);
            } catch (Exception e) {
                _errorLiveData.postValue("Error al cargar reservas ordenadas: " + e.getMessage());
                _isLoadingLiveData.postValue(false);
            }
        });
    }

    /**
     * Elimina todas las reservas
     */
    public void deleteAllReservas() {
        _isLoadingLiveData.postValue(true);
        executorService.execute(() -> {
            try {
                reservaUseCase.deleteAll();
                _successLiveData.postValue("Todas las reservas fueron eliminadas");
                loadReservas();
            } catch (Exception e) {
                _errorLiveData.postValue("Error al eliminar todas las reservas: " + e.getMessage());
                _isLoadingLiveData.postValue(false);
            }
        });
    }

    /**
     * Limpia el mensaje de error
     */
    public void clearError() {
        _errorLiveData.postValue(null);
    }

    /**
     * Limpia el mensaje de éxito
     */
    public void clearSuccess() {
        _successLiveData.postValue(null);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdown();
    }
}