package com.example.grupo_04_tarea_12_ejercicio_01.ui.clientes.pasajeros;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grupo_04_tarea_12_ejercicio_01.R;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Pasajero;
import com.example.grupo_04_tarea_12_ejercicio_01.ui.clientes.ClientesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PasajeroFragment extends Fragment {

    private ClientesViewModel viewModel;
    private PasajeroAdapter adapter;
    private TextView tvEmptyView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pasajero, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rvPasajeros);
        FloatingActionButton fab = view.findViewById(R.id.fabAddPasajero);
        tvEmptyView = view.findViewById(R.id.tvEmptyPasajeros);
        ImageButton btnVolver = view.findViewById(R.id.btnVolver);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new PasajeroAdapter(new PasajeroAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(Pasajero pasajero) {
                mostrarConfirmacionBorrar(pasajero);
            }

            @Override
            public void onEditClick(Pasajero pasajero) {
                AgregarPasajeroDialog dialog = new AgregarPasajeroDialog();
                dialog.setPasajeroAEditar(pasajero); // Pasamos los datos
                dialog.show(getParentFragmentManager(), "EditarPasajero");
            }
        });
        recyclerView.setAdapter(adapter);


        viewModel = new ViewModelProvider(requireActivity()).get(ClientesViewModel.class);

        viewModel.getListaPasajeros().observe(getViewLifecycleOwner(), lista -> {
            adapter.setPasajeros(lista);

            if (lista.isEmpty()) {
                tvEmptyView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                tvEmptyView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });

        fab.setOnClickListener(v -> {
            AgregarPasajeroDialog dialog = new AgregarPasajeroDialog();
            dialog.show(getParentFragmentManager(), "AgregarPasajeroDialog");
        });

        btnVolver.setOnClickListener(v -> {
            getParentFragmentManager().popBackStack();
        });

        return view;
    }

    private void mostrarConfirmacionBorrar(Pasajero pasajero) {
        new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setTitle("Eliminar Pasajero")
                .setMessage("¿Estás seguro de eliminar a " + pasajero.getNombre() + " " + pasajero.getApaterno() + "?\nCon ID: " + pasajero.getIdPasajero())
                .setPositiveButton("Sí, Eliminar", (dialog, which) -> {
                    viewModel.eliminarPasajero(pasajero);
                    Toast.makeText(getContext(), "Pasajero eliminado", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

}