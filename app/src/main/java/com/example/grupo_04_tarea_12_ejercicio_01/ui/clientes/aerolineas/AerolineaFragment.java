package com.example.grupo_04_tarea_12_ejercicio_01.ui.clientes.aerolineas;

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
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Aerolinea;
import com.example.grupo_04_tarea_12_ejercicio_01.ui.clientes.ClientesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AerolineaFragment extends Fragment {

    private ClientesViewModel viewModel;
    private AerolineaAdapter adapter;
    private TextView tvEmpty;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aerolinea, container, false);

        RecyclerView rv = view.findViewById(R.id.rvAerolineas);
        FloatingActionButton fab = view.findViewById(R.id.fabAdd);
        tvEmpty = view.findViewById(R.id.tvEmpty);

        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new AerolineaAdapter(new AerolineaAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(Aerolinea aerolinea) {
                mostrarConfirmacion(aerolinea);
            }
            @Override
            public void onEditClick(Aerolinea aerolinea) {
                AgregarAerolineaDialog dialog = new AgregarAerolineaDialog();
                dialog.setAerolineaEditar(aerolinea);
                dialog.show(getParentFragmentManager(), "EditarAero");
            }
        });
        rv.setAdapter(adapter);

        // Aquí se usa requireActivity para compartir el ViewModel:
        viewModel = new ViewModelProvider(requireActivity()).get(ClientesViewModel.class);

        viewModel.getListaAerolineas().observe(getViewLifecycleOwner(), lista -> {
            adapter.setAerolineas(lista);
            if (lista.isEmpty()) {
                tvEmpty.setVisibility(View.VISIBLE);
                rv.setVisibility(View.GONE);
            } else {
                tvEmpty.setVisibility(View.GONE);
                rv.setVisibility(View.VISIBLE);
            }
        });

        fab.setOnClickListener(v -> {
            AgregarAerolineaDialog dialog = new AgregarAerolineaDialog();
            dialog.show(getParentFragmentManager(), "AgregarAero");
        });

        view.findViewById(R.id.btnVolver).setOnClickListener(v -> {
            getParentFragmentManager().popBackStack();
        });

        return view;
    }

    private void mostrarConfirmacion(Aerolinea aerolinea) {
        new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setTitle("Eliminar Aerolínea")
                .setMessage("¿Eliminar " + aerolinea.getNombre() + "?")
                .setPositiveButton("Sí", (d, w) -> {
                    viewModel.eliminarAerolinea(aerolinea);
                    Toast.makeText(getContext(), "Eliminado", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", null)
                .show();
    }
}