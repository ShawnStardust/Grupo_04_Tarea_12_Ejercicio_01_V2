package com.example.grupo_04_tarea_12_ejercicio_01.ui.flota.Avion;

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

import com.example.grupo_04_tarea_12_ejercicio_01.MainActivity;
import com.example.grupo_04_tarea_12_ejercicio_01.R;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Avion;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AvionListFragment extends Fragment {

    private AvionViewModel viewModel;
    private RecyclerView recyclerViewAviones;
    private TextView tvEmptyAviones;
    private FloatingActionButton fabAddAvion;
    private ImageButton btnBack;

    private AvionAdapter adapter;
    private List<Avion> avionesList;

    public AvionListFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_avion_list, container, false);

        // 1. Inicializar ViewModel
        viewModel = new ViewModelProvider(this).get(AvionViewModel.class);

        // 2. Vincular Vistas
        recyclerViewAviones = view.findViewById(R.id.recyclerViewAviones);
        tvEmptyAviones = view.findViewById(R.id.tvEmptyAviones);
        fabAddAvion = view.findViewById(R.id.fabAddAvion);
        btnBack = view.findViewById(R.id.btnBackAviones);

        // 3. Listener Botón Atrás
        btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        // 4. Configurar RecyclerView
        recyclerViewAviones.setLayoutManager(new LinearLayoutManager(getContext()));
        avionesList = new ArrayList<>();

        // Adapter Simple (3 argumentos: Lista + 2 Listeners)
        adapter = new AvionAdapter(avionesList,
                this::onEditAvion,
                this::onDeleteAvion);
        recyclerViewAviones.setAdapter(adapter);

        // 5. Configurar FAB
        fabAddAvion.setOnClickListener(v -> openAvionForm(null));

        // 6. Observar datos y cargar
        observeViewModel();
        viewModel.loadAviones();

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
        // Observar lista
        viewModel.avionesLiveData.observe(getViewLifecycleOwner(), aviones -> {
            if (aviones != null) {
                avionesList.clear();
                avionesList.addAll(aviones);
                adapter.updateList(aviones);

                if (avionesList.isEmpty()) {
                    recyclerViewAviones.setVisibility(View.GONE);
                    tvEmptyAviones.setVisibility(View.VISIBLE);
                } else {
                    recyclerViewAviones.setVisibility(View.VISIBLE);
                    tvEmptyAviones.setVisibility(View.GONE);
                }
            }
        });

        // Observar errores
        viewModel.errorLiveData.observe(getViewLifecycleOwner(), error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openAvionForm(Avion avion) {
        AvionFormDialogFragment dialog = AvionFormDialogFragment.newInstance(avion);

        dialog.setListener(avionGuardado -> {
            viewModel.saveAvion(avionGuardado);
        });
        dialog.show(getChildFragmentManager(), "AvionFormDialog");
    }

    private void onEditAvion(Avion avion) {
        openAvionForm(avion);
    }

    private void onDeleteAvion(Avion avion) {
        AvionDeleteConfirmationDialog dialog =
                AvionDeleteConfirmationDialog.newInstance(avion);

        dialog.setListener(avionAEliminar -> {
            viewModel.deleteAvion(avionAEliminar);
        });

        dialog.show(getChildFragmentManager(), "DeleteConfirmDialog");
    }
}