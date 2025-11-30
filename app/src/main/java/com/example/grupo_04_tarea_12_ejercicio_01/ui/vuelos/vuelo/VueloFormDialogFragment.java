package com.example.grupo_04_tarea_12_ejercicio_01.ui.vuelos.vuelo;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.grupo_04_tarea_12_ejercicio_01.R;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Vuelo;
import com.google.android.material.textfield.TextInputEditText;

public class VueloFormDialogFragment extends DialogFragment {

    private TextInputEditText etIdVuelo, etIdOrigen, etIdDestino, etIdAvion, etIdFila;
    private TextView tvDialogTitle;
    private Button btnGuardar, btnCancelar;
    private View tilIdVuelo;

    private Vuelo vueloActual;
    private OnVueloSavedListener listener;

    public interface OnVueloSavedListener {
        void onVueloSaved(Vuelo vuelo);
    }

    public void setListener(OnVueloSavedListener listener) {
        this.listener = listener;
    }

    public static VueloFormDialogFragment newInstance(Vuelo vuelo) {
        VueloFormDialogFragment fragment = new VueloFormDialogFragment();
        if (vuelo != null) fragment.vueloActual = vuelo;
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_vuelo_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

        if (vueloActual != null && vueloActual.getIdVuelo() > 0) {
            populateFields();
            tvDialogTitle.setText("Editar Vuelo #" + vueloActual.getIdVuelo());
            tilIdVuelo.setVisibility(View.VISIBLE); // Mostrar ID
        } else {
            tvDialogTitle.setText("Nuevo Vuelo");
            tilIdVuelo.setVisibility(View.GONE); // Ocultar ID
        }

        btnCancelar.setOnClickListener(v -> dismiss());
        btnGuardar.setOnClickListener(v -> guardarVuelo());
    }

    private void initViews(View view) {
        tvDialogTitle = view.findViewById(R.id.tvDialogTitleVuelo);

        tilIdVuelo = view.findViewById(R.id.tilIdVuelo);
        etIdVuelo = view.findViewById(R.id.etIdVuelo);

        etIdOrigen = view.findViewById(R.id.etIdAeropuertoOrigen);
        etIdDestino = view.findViewById(R.id.etIdAeropuertoDestino);
        etIdAvion = view.findViewById(R.id.etIdAvion);
        etIdFila = view.findViewById(R.id.etIdFila);

        btnGuardar = view.findViewById(R.id.btnGuardarVuelo);
        btnCancelar = view.findViewById(R.id.btnCancelarVuelo);
    }

    private void populateFields() {
        if (vueloActual.getIdVuelo() > 0) {
            etIdVuelo.setText(String.valueOf(vueloActual.getIdVuelo()));
        }
        etIdOrigen.setText(String.valueOf(vueloActual.getIdAeropuertoOrigen()));
        etIdDestino.setText(String.valueOf(vueloActual.getIdAeropuertoDestino()));
        etIdAvion.setText(String.valueOf(vueloActual.getIdAvion()));
        etIdFila.setText(String.valueOf(vueloActual.getIdFila()));
    }

    private void guardarVuelo() {
        try {
            int idOrigen = Integer.parseInt(etIdOrigen.getText().toString());
            int idDestino = Integer.parseInt(etIdDestino.getText().toString());
            int idAvion = Integer.parseInt(etIdAvion.getText().toString());
            int idFila = Integer.parseInt(etIdFila.getText().toString());

            if (idOrigen <= 0 || idDestino <= 0 || idAvion <= 0 || idFila <= 0) {
                Toast.makeText(getContext(), "Todos los IDs y el número de filas deben ser positivos.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (idOrigen == idDestino) {
                Toast.makeText(getContext(), "El aeropuerto de origen y destino no pueden ser el mismo.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (vueloActual == null) {
                vueloActual = new Vuelo();
            }

            vueloActual.setIdAeropuertoOrigen(idOrigen);
            vueloActual.setIdAeropuertoDestino(idDestino);
            vueloActual.setIdAvion(idAvion);
            vueloActual.setIdFila(idFila);

            if (listener != null) listener.onVueloSaved(vueloActual);

            dismiss();

        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Revise que todos los campos sean números válidos.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), "Error al guardar el vuelo: complete todos los campos.", Toast.LENGTH_SHORT).show();
        }
    }
}