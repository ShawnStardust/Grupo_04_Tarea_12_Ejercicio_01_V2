package com.example.grupo_04_tarea_12_ejercicio_01.ui.clientes.paises;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.grupo_04_tarea_12_ejercicio_01.R;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Pais;
import com.example.grupo_04_tarea_12_ejercicio_01.ui.clientes.ClientesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PaisFragment extends Fragment {

    private ClientesViewModel viewModel;
    private PaisAdapter adapter;
    private TextView tvEmptyView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pais, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rvPaises);
        FloatingActionButton fab = view.findViewById(R.id.fabAddPais);
        tvEmptyView = view.findViewById(R.id.tvEmptyPaises);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new PaisAdapter(new PaisAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(Pais pais) {
                mostrarConfirmacionBorrar(pais);
            }

            @Override
            public void onEditClick(Pais pais) {
                // Abrir diálogo en modo edición
                AgregarPaisDialog dialog = new AgregarPaisDialog();
                dialog.setpaisAEditar(pais); // Pasamos los datos
                dialog.show(getParentFragmentManager(), "EditarPais");
            }
        });
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(requireActivity()).get(ClientesViewModel.class);

        // Observar lista de Países
        viewModel.getListaPaises().observe(getViewLifecycleOwner(), lista -> {
            adapter.setPaises(lista);
            if (lista.isEmpty()) {
                tvEmptyView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                tvEmptyView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });

        fab.setOnClickListener(v -> {
            AgregarPaisDialog dialog = new AgregarPaisDialog();
            dialog.show(getParentFragmentManager(), "AgregarPaisDialog");
        });

        view.findViewById(R.id.btnVolver).setOnClickListener(v -> {
            getParentFragmentManager().popBackStack();
        });

        return view;
    }
    private void mostrarConfirmacionBorrar(Pais pais) {
        new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setTitle("Eliminar Pais")
                .setMessage("¿Estás seguro de eliminar a " + pais.getNombre() + "?\nCon ID: " + pais.getIdPais())
                .setPositiveButton("Sí, Eliminar", (dialog, which) -> {
                    viewModel.eliminarPais(pais);
                    Toast.makeText(getContext(), "Pais eliminado", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}