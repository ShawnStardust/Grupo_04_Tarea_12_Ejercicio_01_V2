package com.example.grupo_04_tarea_12_ejercicio_01.ui.vuelos.tarifas;

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
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Tarifa;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class TarifasListFragment extends Fragment {

    private TarifasViewModel viewModel;
    private RecyclerView recyclerViewTarifas;
    private TextView tvEmptyTarifas;
    private FloatingActionButton fabAddTarifa;
    private ImageButton btnBack;

    private TarifasAdapter adapter;
    private List<Tarifa> tarifasList;

    public TarifasListFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tarifas_list, container, false);

        viewModel = new ViewModelProvider(this).get(TarifasViewModel.class);
        recyclerViewTarifas = view.findViewById(R.id.recyclerViewTarifas);
        tvEmptyTarifas = view.findViewById(R.id.tvEmptyTarifas);
        fabAddTarifa = view.findViewById(R.id.fabAddTarifa);
        btnBack = view.findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        recyclerViewTarifas.setLayoutManager(new LinearLayoutManager(getContext()));
        tarifasList = new ArrayList<>();
        adapter = new TarifasAdapter(tarifasList, this::onEditTarifa, this::onDeleteTarifa);
        recyclerViewTarifas.setAdapter(adapter);

        fabAddTarifa.setOnClickListener(v -> openTarifaForm(null));

        observeViewModel();

        viewModel.loadTarifas();

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
        viewModel.tarifasLiveData.observe(getViewLifecycleOwner(), tarifas -> {
            if (tarifas != null) {
                tarifasList.clear();
                tarifasList.addAll(tarifas);
                adapter.notifyDataSetChanged();

                if (tarifasList.isEmpty()) {
                    recyclerViewTarifas.setVisibility(View.GONE);
                    tvEmptyTarifas.setVisibility(View.VISIBLE);
                } else {
                    recyclerViewTarifas.setVisibility(View.VISIBLE);
                    tvEmptyTarifas.setVisibility(View.GONE);
                }
            }
        });

        viewModel.errorLiveData.observe(getViewLifecycleOwner(), error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openTarifaForm(Tarifa tarifa) {
        TarifaFormDialogFragment dialog = TarifaFormDialogFragment.newInstance(tarifa);

        dialog.setListener(tarifaGuardada -> {
            viewModel.saveTarifa(tarifaGuardada);
        });
        dialog.show(getChildFragmentManager(), "TarifaFormDialog");
    }

    private void onEditTarifa(Tarifa tarifa) {
        openTarifaForm(tarifa);
    }

    private void onDeleteTarifa(Tarifa tarifa) {
        TarifaDeleteConfirmationDialogFragment dialog =
                TarifaDeleteConfirmationDialogFragment.newInstance(tarifa);

        dialog.setListener(tarifaAEliminar -> {
            viewModel.deleteTarifa(tarifaAEliminar);
        });

        dialog.show(getChildFragmentManager(), "DeleteConfirmDialog");
    }
}