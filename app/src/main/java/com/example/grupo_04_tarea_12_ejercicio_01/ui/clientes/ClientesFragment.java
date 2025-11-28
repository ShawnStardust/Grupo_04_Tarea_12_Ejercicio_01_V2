package com.example.grupo_04_tarea_12_ejercicio_01.ui.clientes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.grupo_04_tarea_12_ejercicio_01.R;
import com.example.grupo_04_tarea_12_ejercicio_01.ui.clientes.aerolineas.AerolineaFragment;
import com.example.grupo_04_tarea_12_ejercicio_01.ui.clientes.paises.PaisFragment;
import com.example.grupo_04_tarea_12_ejercicio_01.ui.clientes.pasajeros.PasajeroFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ClientesFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clientes, container, false);

        Button btnPasajeros = view.findViewById(R.id.btnVerPasajeros);
        Button btnAerolineas = view.findViewById(R.id.btnVerAerolineas);
        Button btnPaises = view.findViewById(R.id.btnVerPaises);

        btnPasajeros.setOnClickListener(v -> {
            navegarA(new PasajeroFragment());
        });

        btnAerolineas.setOnClickListener(v -> {
            navegarA(new AerolineaFragment());
        });

        btnPaises.setOnClickListener(v -> {
            navegarA(new PaisFragment());
        });

        return view;
    }

    private void navegarA(Fragment fragment) {
        if (getActivity() != null) {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}