package com.example.grupo_04_tarea_12_ejercicio_01.ui.vuelos;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.grupo_04_tarea_12_ejercicio_01.R;
import com.example.grupo_04_tarea_12_ejercicio_01.ui.vuelos.vuelo.VuelosListFragment;
import com.example.grupo_04_tarea_12_ejercicio_01.ui.vuelos.tarifas.TarifasListFragment;

public class VuelosFragment extends Fragment {

    private Button btnVerVuelos;
    private Button btnVerTarifas;

    public VuelosFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vuelos, container, false);

        btnVerVuelos = view.findViewById(R.id.btnVerVuelos);
        btnVerTarifas = view.findViewById(R.id.btnVerTarifas);

        btnVerVuelos.setOnClickListener(v -> navigateToVuelos());
        btnVerTarifas.setOnClickListener(v -> navigateToTarifas());

        return view;
    }

    private void navigateToVuelos() {

        Fragment vuelosListFragment = new VuelosListFragment();

        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

        transaction.replace(R.id.fragmentContainer, vuelosListFragment);

        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void navigateToTarifas() {

        Fragment tarifasListFragment = new TarifasListFragment();

        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

        transaction.replace(R.id.fragmentContainer, tarifasListFragment);

        transaction.addToBackStack(null);
        transaction.commit();
    }
}