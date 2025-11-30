package com.example.grupo_04_tarea_12_ejercicio_01.ui.vuelos.vuelo;

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
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Vuelo;
import com.example.grupo_04_tarea_12_ejercicio_01.ui.vuelos.vuelo.VuelosAdapter;
import com.example.grupo_04_tarea_12_ejercicio_01.ui.vuelos.vuelo.VuelosViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class VuelosListFragment extends Fragment {

    private VuelosViewModel viewModel;
    private RecyclerView recyclerViewVuelos;
    private TextView tvEmptyVuelos;
    private FloatingActionButton fabAddVuelo;
    private ImageButton btnBack;

    private VuelosAdapter adapter;
    private List<Vuelo> vuelosList;

    public VuelosListFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vuelos_list, container, false);

        viewModel = new ViewModelProvider(this).get(VuelosViewModel.class);
        recyclerViewVuelos = view.findViewById(R.id.recyclerViewVuelos);
        tvEmptyVuelos = view.findViewById(R.id.tvEmptyVuelos);
        fabAddVuelo = view.findViewById(R.id.fabAddVuelo);
        btnBack = view.findViewById(R.id.btnBackVuelos);

        btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        recyclerViewVuelos.setLayoutManager(new LinearLayoutManager(getContext()));
        vuelosList = new ArrayList<>();

        adapter = new VuelosAdapter(vuelosList, this::onEditVuelo, this::onDeleteVuelo);
        recyclerViewVuelos.setAdapter(adapter);

        fabAddVuelo.setOnClickListener(v -> openVueloForm(null));

        observeViewModel();

        viewModel.loadVuelos();

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
        viewModel.vuelosLiveData.observe(getViewLifecycleOwner(), vuelos -> {
            if (vuelos != null) {
                vuelosList.clear();
                vuelosList.addAll(vuelos);
                adapter.notifyDataSetChanged();

                if (vuelosList.isEmpty()) {
                    recyclerViewVuelos.setVisibility(View.GONE);
                    tvEmptyVuelos.setVisibility(View.VISIBLE);
                } else {
                    recyclerViewVuelos.setVisibility(View.VISIBLE);
                    tvEmptyVuelos.setVisibility(View.GONE);
                }
            }
        });

        viewModel.errorLiveData.observe(getViewLifecycleOwner(), error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openVueloForm(Vuelo vuelo) {
        VueloFormDialogFragment dialog = VueloFormDialogFragment.newInstance(vuelo);

        dialog.setListener(vueloGuardado -> {
            viewModel.saveVuelo(vueloGuardado);
        });
        dialog.show(getChildFragmentManager(), "VueloFormDialog");
    }

    private void onEditVuelo(Vuelo vuelo) {
        openVueloForm(vuelo);
    }

    private void onDeleteVuelo(Vuelo vuelo) {
        com.example.grupo_04_tarea_12_ejercicio_01.ui.vuelos.vuelo.VueloDeleteConfirmationDialogFragment dialog =
                com.example.grupo_04_tarea_12_ejercicio_01.ui.vuelos.vuelo.VueloDeleteConfirmationDialogFragment.newInstance(vuelo);

        dialog.setListener(vueloAEliminar -> {
            viewModel.deleteVuelo(vueloAEliminar);
        });

        dialog.show(getChildFragmentManager(), "DeleteConfirmDialog");
    }
}