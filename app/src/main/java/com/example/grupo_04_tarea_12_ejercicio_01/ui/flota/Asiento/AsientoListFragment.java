package com.example.grupo_04_tarea_12_ejercicio_01.ui.flota.Asiento;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.example.grupo_04_tarea_12_ejercicio_01.MainActivity;
import com.example.grupo_04_tarea_12_ejercicio_01.R;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Asiento;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AsientoListFragment extends Fragment {

    private AsientoViewModel viewModel;
    private RecyclerView recyclerViewAsientos;
    private TextView tvEmptyAsientos;
    private FloatingActionButton fabAddAsiento;
    private ImageButton btnBack;

    private AsientoAdapter adapter;
    private List<Asiento> asientosList;

    public AsientoListFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_asiento_list, container, false);

        // Inicialización
        viewModel = new ViewModelProvider(this).get(AsientoViewModel.class);

        recyclerViewAsientos = view.findViewById(R.id.recyclerViewAsientos);
        tvEmptyAsientos = view.findViewById(R.id.tvEmptyAsientos);
        fabAddAsiento = view.findViewById(R.id.fabAddAsiento);
        btnBack = view.findViewById(R.id.btnBackAsientos);

        // Listeners
        btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        // Setup Recycler
        recyclerViewAsientos.setLayoutManager(new LinearLayoutManager(getContext()));
        asientosList = new ArrayList<>();

        // Constructor simple del Adapter (3 argumentos)
        adapter = new AsientoAdapter(asientosList,
                this::onEditAsiento,
                this::onDeleteAsiento);
        recyclerViewAsientos.setAdapter(adapter);

        fabAddAsiento.setOnClickListener(v -> openAsientoForm(null));

        observeViewModel();

        // Carga inicial (aunque el ViewModel ya lo hace en su constructor, no está de más)
        viewModel.loadAsientos();

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
        viewModel.asientosLiveData.observe(getViewLifecycleOwner(), asientos -> {
            if (asientos != null) {
                asientosList.clear();
                asientosList.addAll(asientos);
                adapter.updateList(asientos);

                if (asientosList.isEmpty()) {
                    recyclerViewAsientos.setVisibility(View.GONE);
                    tvEmptyAsientos.setVisibility(View.VISIBLE);
                } else {
                    recyclerViewAsientos.setVisibility(View.VISIBLE);
                    tvEmptyAsientos.setVisibility(View.GONE);
                }
            }
        });

        viewModel.errorLiveData.observe(getViewLifecycleOwner(), error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openAsientoForm(Asiento asiento) {
        // Debes crear AsientoFormDialogFragment en el paquete Asiento
        AsientoFormDialogFragment dialog = AsientoFormDialogFragment.newInstance(asiento);

        dialog.setListener(asientoGuardado -> {
            viewModel.saveAsiento(asientoGuardado);
        });
        dialog.show(getChildFragmentManager(), "AsientoFormDialog");
    }

    private void onEditAsiento(Asiento asiento) {
        openAsientoForm(asiento);
    }

    private void onDeleteAsiento(Asiento asiento) {
        // Debes crear AsientoDeleteConfirmationDialogFragment en el paquete Asiento
        AsientoDeleteConfirmationDialog dialog =
                AsientoDeleteConfirmationDialog.newInstance(asiento);

        dialog.setListener(asientoAEliminar -> {
            viewModel.deleteAsiento(asientoAEliminar);
        });

        dialog.show(getChildFragmentManager(), "DeleteConfirmDialog");
    }
}