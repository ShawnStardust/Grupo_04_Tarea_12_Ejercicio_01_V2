package com.example.grupo_04_tarea_12_ejercicio_01.ui.booking.reservas;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.grupo_04_tarea_12_ejercicio_01.R;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Reserva;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ReservasListFragment extends Fragment {

    private ReservasViewModel viewModel;
    private RecyclerView recyclerViewReservas;
    private TextView tvEmptyReservas;
    private FloatingActionButton fabAddReserva;
    private ReservasAdapter adapter;
    private List<Reserva> reservasList;

    public ReservasListFragment() {
        // Required empty public constructor
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

        // Configurar RecyclerView
        recyclerViewReservas.setLayoutManager(new LinearLayoutManager(getContext()));
        reservasList = new ArrayList<>();
        adapter = new ReservasAdapter(reservasList, this::onEditReserva, this::onDeleteReserva);
        recyclerViewReservas.setAdapter(adapter);

        // Configurar FAB
        fabAddReserva.setOnClickListener(v -> openReservaForm(null));

        // Observar LiveData
        observeViewModel();

        return view;
    }

    private void observeViewModel() {
        // Observar lista de reservas
        viewModel.reservasLiveData.observe(getViewLifecycleOwner(), reservas -> {
            if (reservas != null) {
                reservasList.clear();
                reservasList.addAll(reservas);
                adapter.notifyDataSetChanged();

                // Mostrar mensaje si está vacío
                if (reservasList.isEmpty()) {
                    recyclerViewReservas.setVisibility(View.GONE);
                    tvEmptyReservas.setVisibility(View.VISIBLE);
                } else {
                    recyclerViewReservas.setVisibility(View.VISIBLE);
                    tvEmptyReservas.setVisibility(View.GONE);
                }
            }
        });

        // Observar estado de carga
        viewModel.isLoadingLiveData.observe(getViewLifecycleOwner(), isLoading -> {
            // Aquí puedes mostrar/ocultar un ProgressBar si lo tienes
            if (isLoading != null && isLoading) {
                // Mostrar loading
            } else {
                // Ocultar loading
            }
        });

        // Observar errores
        viewModel.errorLiveData.observe(getViewLifecycleOwner(), error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openReservaForm(Reserva reserva) {
        // Aquí abrirías un dialog o fragment para crear/editar
        // Por ahora solo recargaremos la lista
        viewModel.loadReservas();
    }

    private void onEditReserva(Reserva reserva) {
        openReservaForm(reserva);
    }

    private void onDeleteReserva(Reserva reserva) {
        viewModel.deleteReserva(reserva);
    }
}