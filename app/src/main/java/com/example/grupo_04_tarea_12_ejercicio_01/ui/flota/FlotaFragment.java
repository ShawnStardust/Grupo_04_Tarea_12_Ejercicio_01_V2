package com.example.grupo_04_tarea_12_ejercicio_01.ui.flota;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.grupo_04_tarea_12_ejercicio_01.R;
// Importamos el fragmento de lista que ya tenemos listo
import com.example.grupo_04_tarea_12_ejercicio_01.ui.flota.Aeropuerto.AeropuertoListFragment;
import com.example.grupo_04_tarea_12_ejercicio_01.ui.flota.Asiento.AsientoListFragment;
import com.example.grupo_04_tarea_12_ejercicio_01.ui.flota.Avion.AvionListFragment;


// import com.example.grupo_04_tarea_12_ejercicio_01.ui.flota.avion.AvionesListFragment;
// import com.example.grupo_04_tarea_12_ejercicio_01.ui.flota.asiento.AsientosListFragment;

public class FlotaFragment extends Fragment {

    private Button btnVerAeropuertos;
    private Button btnVerAviones;
    private Button btnVerAsientos;

    public FlotaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflamos el layout que contiene los botones (fragment_flota_menu.xml o fragment_flota.xml)
        View view = inflater.inflate(R.layout.fragment_flota, container, false);

        // 1. Inicializar botones con los IDs del XML
        btnVerAeropuertos = view.findViewById(R.id.btnVerAeropuertos);
        btnVerAviones = view.findViewById(R.id.btnVerAviones);
        btnVerAsientos = view.findViewById(R.id.btnVerAsientos);

        // 2. Configurar listeners para la navegación
        btnVerAeropuertos.setOnClickListener(v -> navigateToAeropuertos());
        btnVerAviones.setOnClickListener(v -> navigateToAviones());
        btnVerAsientos.setOnClickListener(v -> navigateToAsientos());

        return view;
    }

    private void navigateToAeropuertos() {
        // Instanciamos el fragmento de lista de Aeropuertos
        Fragment aeropuertosListFragment = new AeropuertoListFragment();

        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, aeropuertosListFragment);
        transaction.addToBackStack(null); // Permite regresar al menú con el botón 'Atrás'
        transaction.commit();
    }

    private void navigateToAviones() {


        Fragment avionesListFragment = new AvionListFragment();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, avionesListFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void navigateToAsientos() {

        Fragment asientosListFragment = new AsientoListFragment();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, asientosListFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}