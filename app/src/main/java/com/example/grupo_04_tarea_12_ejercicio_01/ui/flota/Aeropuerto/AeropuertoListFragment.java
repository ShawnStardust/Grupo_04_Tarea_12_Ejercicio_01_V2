package com.example.grupo_04_tarea_12_ejercicio_01.ui.flota.Aeropuerto;

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
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Aeropuerto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AeropuertoListFragment extends Fragment {

    private AeropuertoViewModel viewModel;
    private RecyclerView recyclerViewAeropuertos;
    private TextView tvEmptyAeropuertos;
    private FloatingActionButton fabAddAeropuerto;
    private ImageButton btnBack;
    private AeropuertoAdapter adapter;
    private List<Aeropuerto> aeropuertosList;

    // ❌ Eliminamos la inyección de PaisRepository para usar el constructor simple

    public AeropuertoListFragment() {
        // Constructor vacío requerido por el sistema Android y Hilt
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Usamos el layout fragment_aeropuerto_list
        View view = inflater.inflate(R.layout.fragment_aeropuerto_list, container, false);

        // 1. Inicialización de ViewModel y Vistas
        viewModel = new ViewModelProvider(this).get(AeropuertoViewModel.class);
        recyclerViewAeropuertos = view.findViewById(R.id.recyclerViewAeropuertos);
        tvEmptyAeropuertos = view.findViewById(R.id.tvEmptyAeropuertos);
        fabAddAeropuerto = view.findViewById(R.id.fabAddAeropuerto);
        btnBack = view.findViewById(R.id.btnBackAeropuertos);

        // 2. Configuración de Listeners y Recycler
        btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        recyclerViewAeropuertos.setLayoutManager(new LinearLayoutManager(getContext()));
        aeropuertosList = new ArrayList<>();

        // ✅ Creamos el Adapter con el constructor SIMPLE (3 argumentos)
        // Ya no se requiere la inyección de PaisRepository aquí.
        adapter = new AeropuertoAdapter(aeropuertosList,
                this::onEditAeropuerto,
                this::onDeleteAeropuerto);

        recyclerViewAeropuertos.setAdapter(adapter);

        fabAddAeropuerto.setOnClickListener(v -> openAeropuertoForm(null));

        // 3. Observación y Carga
        observeViewModel();
        viewModel.loadAeropuertos();

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
        viewModel.aeropuertosLiveData.observe(getViewLifecycleOwner(), aeropuertos -> {
            if (aeropuertos != null) {
                aeropuertosList.clear();
                aeropuertosList.addAll(aeropuertos);
                adapter.updateList(aeropuertos);

                if (aeropuertosList.isEmpty()) {
                    recyclerViewAeropuertos.setVisibility(View.GONE);
                    tvEmptyAeropuertos.setVisibility(View.VISIBLE);
                } else {
                    recyclerViewAeropuertos.setVisibility(View.VISIBLE);
                    tvEmptyAeropuertos.setVisibility(View.GONE);
                }
            }
        });

        viewModel.errorLiveData.observe(getViewLifecycleOwner(), error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openAeropuertoForm(Aeropuerto aeropuerto) {
        // Asumimos que la clase AeropuertoFormDialogFragment es accesible
        AeropuertoFormDialogFragment dialog = AeropuertoFormDialogFragment.newInstance(aeropuerto);

        dialog.setListener(aeropuertoGuardado -> {
            viewModel.saveAeropuerto(aeropuertoGuardado);
        });
        dialog.show(getChildFragmentManager(), "AeropuertoFormDialog");
    }

    private void onEditAeropuerto(Aeropuerto aeropuerto) {
        openAeropuertoForm(aeropuerto);
    }

    private void onDeleteAeropuerto(Aeropuerto aeropuerto) {
        // Asumimos que la clase AeropuertoDeleteConfirmationDialogFragment es accesible
        AeropuertoDeleteConfirmationDialogFragment dialog =
                AeropuertoDeleteConfirmationDialogFragment.newInstance(aeropuerto);

        dialog.setListener(aeropuertoAEliminar -> {
            viewModel.deleteAeropuerto(aeropuertoAEliminar);
        });

        dialog.show(getChildFragmentManager(), "DeleteConfirmDialog");
    }
}