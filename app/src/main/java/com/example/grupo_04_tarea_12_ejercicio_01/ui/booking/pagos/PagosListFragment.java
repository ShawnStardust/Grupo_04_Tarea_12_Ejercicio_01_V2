package com.example.grupo_04_tarea_12_ejercicio_01.ui.booking.pagos;

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
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Pago;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PagosListFragment extends Fragment {

    private PagosViewModel viewModel;
    private RecyclerView recyclerViewPagos;
    private TextView tvEmptyPagos;
    private FloatingActionButton fabAddPago;
    private PagosAdapter adapter;
    private List<Pago> pagosList;

    public PagosListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pagos_list, container, false);

        // Inicializar ViewModel
        viewModel = new ViewModelProvider(this).get(PagosViewModel.class);

        // Inicializar vistas
        recyclerViewPagos = view.findViewById(R.id.recyclerViewPagos);
        tvEmptyPagos = view.findViewById(R.id.tvEmptyPagos);
        fabAddPago = view.findViewById(R.id.fabAddPago);

        // Configurar RecyclerView
        recyclerViewPagos.setLayoutManager(new LinearLayoutManager(getContext()));
        pagosList = new ArrayList<>();
        adapter = new PagosAdapter(pagosList, this::onEditPago, this::onDeletePago);
        recyclerViewPagos.setAdapter(adapter);

        // Configurar FAB
        fabAddPago.setOnClickListener(v -> openPagoForm(null));

        // Observar LiveData
        observeViewModel();

        return view;
    }

    private void observeViewModel() {
        // Observar lista de pagos
        viewModel.pagosLiveData.observe(getViewLifecycleOwner(), pagos -> {
            if (pagos != null) {
                pagosList.clear();
                pagosList.addAll(pagos);
                adapter.notifyDataSetChanged();

                // Mostrar mensaje si está vacío
                if (pagosList.isEmpty()) {
                    recyclerViewPagos.setVisibility(View.GONE);
                    tvEmptyPagos.setVisibility(View.VISIBLE);
                } else {
                    recyclerViewPagos.setVisibility(View.VISIBLE);
                    tvEmptyPagos.setVisibility(View.GONE);
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

    private void openPagoForm(Pago pago) {
        // Aquí abrirías un dialog o fragment para crear/editar
        // Por ahora solo recargaremos la lista
        viewModel.loadPagos();
    }

    private void onEditPago(Pago pago) {
        openPagoForm(pago);
    }

    private void onDeletePago(Pago pago) {
        viewModel.deletePago(pago);
    }
}