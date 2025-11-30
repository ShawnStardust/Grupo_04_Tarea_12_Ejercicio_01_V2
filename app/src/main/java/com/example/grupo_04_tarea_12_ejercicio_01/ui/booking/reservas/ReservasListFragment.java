package com.example.grupo_04_tarea_12_ejercicio_01.ui.booking.reservas;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.grupo_04_tarea_12_ejercicio_01.MainActivity;
import com.example.grupo_04_tarea_12_ejercicio_01.R;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Aeropuerto;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Pasajero;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Reserva;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Vuelo;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ReservasListFragment extends Fragment {

    private ReservasViewModel viewModel;
    private RecyclerView recyclerViewReservas;
    private TextView tvEmptyReservas;
    private FloatingActionButton fabAddReserva;
    private ImageButton btnBack;
    private ReservasAdapter adapter;

    // Listas de datos
    private List<Reserva> reservasList = new ArrayList<>();
    private List<Pasajero> pasajerosList = new ArrayList<>();
    private List<Vuelo> vuelosList = new ArrayList<>();
    private List<Aeropuerto> aeropuertosList = new ArrayList<>();

    public ReservasListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservas_list, container, false);

        // Inicializar ViewModel
        viewModel = new ViewModelProvider(this).get(ReservasViewModel.class);

        // Inicializar vistas
        recyclerViewReservas = view.findViewById(R.id.recyclerViewReservas);
        tvEmptyReservas = view.findViewById(R.id.tvEmptyReservas);
        fabAddReserva = view.findViewById(R.id.fabAddReserva);
        btnBack = view.findViewById(R.id.btnBack);

        // Configurar botón de retroceso
        btnBack.setOnClickListener(v -> {
            getParentFragmentManager().popBackStack();
        });

        // Configurar RecyclerView
        recyclerViewReservas.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ReservasAdapter(reservasList, this::onEditReserva, this::onDeleteReserva);
        recyclerViewReservas.setAdapter(adapter);

        // Configurar botón para agregar reserva
        fabAddReserva.setOnClickListener(v -> openReservaForm(null));

        // Observar cambios en el ViewModel
        observeViewModel();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setBottomNavVisibility(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setBottomNavVisibility(true);
        }
    }

    private void observeViewModel() {
        // Observar cambios en las reservas
        viewModel.reservasLiveData.observe(getViewLifecycleOwner(), reservas -> {
            if (reservas != null) {
                reservasList.clear();
                reservasList.addAll(reservas);
                adapter.notifyDataSetChanged();

                // Mostrar mensaje si no hay reservas
                if (reservasList.isEmpty()) {
                    recyclerViewReservas.setVisibility(View.GONE);
                    tvEmptyReservas.setVisibility(View.VISIBLE);
                } else {
                    recyclerViewReservas.setVisibility(View.VISIBLE);
                    tvEmptyReservas.setVisibility(View.GONE);
                }
            }
        });

        // Observar cambios en los pasajeros
        viewModel.pasajerosLiveData.observe(getViewLifecycleOwner(), pasajeros -> {
            if (pasajeros != null) {
                this.pasajerosList = pasajeros;
            }
        });

        // Observar cambios en los vuelos
        viewModel.vuelosLiveData.observe(getViewLifecycleOwner(), vuelos -> {
            if (vuelos != null) {
                this.vuelosList = vuelos;
            }
        });

        // Observar cambios en los aeropuertos
        viewModel.aeropuertosLiveData.observe(getViewLifecycleOwner(), aeropuertos -> {
            if (aeropuertos != null) {
                this.aeropuertosList = aeropuertos;
            }
        });

        // Observar errores
        viewModel.errorLiveData.observe(getViewLifecycleOwner(), error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                viewModel.clearError(); // Limpiar error después de mostrarlo
            }
        });

        // Observar mensajes de éxito
        viewModel.successLiveData.observe(getViewLifecycleOwner(), success -> {
            if (success != null && !success.isEmpty()) {
                Toast.makeText(getContext(), success, Toast.LENGTH_SHORT).show();
                viewModel.clearSuccess(); // Limpiar mensaje después de mostrarlo
            }
        });

        // Observar estado de carga
        viewModel.isLoadingLiveData.observe(getViewLifecycleOwner(), isLoading -> {
            // Aquí puedes mostrar/ocultar un ProgressBar si lo tienes
            // progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });
    }

    /**
     * Abre el formulario de reserva (crear o editar)
     */
    private void openReservaForm(Reserva reserva) {
        // Verificar que los datos necesarios estén cargados
        if (pasajerosList.isEmpty()) {
            Toast.makeText(getContext(), "No hay pasajeros disponibles. Agregue pasajeros primero.", Toast.LENGTH_LONG).show();
            return;
        }

        if (vuelosList.isEmpty()) {
            Toast.makeText(getContext(), "No hay vuelos disponibles. Agregue vuelos primero.", Toast.LENGTH_LONG).show();
            return;
        }

        if (aeropuertosList.isEmpty()) {
            Toast.makeText(getContext(), "Cargando información de aeropuertos...", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear y mostrar el diálogo con todos los datos necesarios
        ReservaFormDialogFragment dialog = ReservaFormDialogFragment.newInstance(
                reserva,           // null para nueva reserva, o la reserva a editar
                pasajerosList,
                vuelosList,
                aeropuertosList
        );

        dialog.setListener(reservaGuardada -> {
            viewModel.saveReserva(reservaGuardada);
        });

        dialog.show(getChildFragmentManager(), "ReservaFormDialog");
    }

    /**
     * Maneja el clic en el botón de editar reserva
     */
    private void onEditReserva(Reserva reserva) {
        openReservaForm(reserva);
    }

    /**
     * Maneja el clic en el botón de eliminar reserva
     */
    private void onDeleteReserva(Reserva reserva) {
        ReservaDeleteConfirmationDialogFragment dialog =
                ReservaDeleteConfirmationDialogFragment.newInstance(reserva);

        dialog.setListener(reservaAEliminar -> {
            viewModel.deleteReserva(reservaAEliminar);
        });

        dialog.show(getChildFragmentManager(), "DeleteConfirmDialog");
    }
}