package com.example.grupo_04_tarea_12_ejercicio_01.ui.flota.Avion;

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
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Avion;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AvionFormDialogFragment extends DialogFragment {

    private TextInputEditText etIdAvion, etIdAerolinea, etTipoAvion, etCapacidad;
    private TextInputLayout tilIdAvion;
    private TextView tvDialogTitle;
    private Button btnGuardar, btnCancelar;

    private Avion avionActual;
    private OnAvionSavedListener listener;

    public interface OnAvionSavedListener {
        void onAvionSaved(Avion avion);
    }

    public void setListener(OnAvionSavedListener listener) {
        this.listener = listener;
    }

    public static AvionFormDialogFragment newInstance(Avion avion) {
        AvionFormDialogFragment fragment = new AvionFormDialogFragment();
        if (avion != null) fragment.avionActual = avion;
        return fragment;
    }

    public AvionFormDialogFragment() {
        // Constructor vacío requerido
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
        return inflater.inflate(R.layout.fragment_avion_form_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

        if (avionActual != null && avionActual.getIdAvion() > 0) {
            populateFields();
            tvDialogTitle.setText("Editar Avión #" + avionActual.getIdAvion());
            tilIdAvion.setVisibility(View.VISIBLE);
        } else {
            tvDialogTitle.setText("Registrar Avión");
            tilIdAvion.setVisibility(View.GONE);
        }

        btnCancelar.setOnClickListener(v -> dismiss());
        btnGuardar.setOnClickListener(v -> guardarAvion());
    }

    private void initViews(View view) {
        tvDialogTitle = view.findViewById(R.id.tvDialogTitleAvion);

        tilIdAvion = view.findViewById(R.id.tilIdAvion);
        etIdAvion = view.findViewById(R.id.etIdAvion);

        etIdAerolinea = view.findViewById(R.id.etIdAerolineaAvion);
        etTipoAvion = view.findViewById(R.id.etTipoAvion);
        etCapacidad = view.findViewById(R.id.etCapacidadAvion);

        btnGuardar = view.findViewById(R.id.btnGuardarAvion);
        btnCancelar = view.findViewById(R.id.btnCancelarAvion);
    }

    private void populateFields() {
        etIdAvion.setText(String.valueOf(avionActual.getIdAvion()));
        etIdAerolinea.setText(String.valueOf(avionActual.getIdAerolinea()));
        etTipoAvion.setText(avionActual.getTipoAvion());
        etCapacidad.setText(String.valueOf(avionActual.getCapacidad()));
    }

    private void guardarAvion() {
        try {
            // Validar entradas
            int idAerolinea = Integer.parseInt(etIdAerolinea.getText().toString());
            String tipo = etTipoAvion.getText().toString().trim();
            int capacidad = Integer.parseInt(etCapacidad.getText().toString());

            if (tipo.isEmpty()) {
                Toast.makeText(getContext(), "El tipo de avión es obligatorio", Toast.LENGTH_SHORT).show();
                return;
            }
            if (idAerolinea <= 0 || capacidad <= 0) {
                Toast.makeText(getContext(), "ID Aerolínea y Capacidad deben ser mayores a 0", Toast.LENGTH_SHORT).show();
                return;
            }

            if (avionActual == null) {
                avionActual = new Avion();
            }

            avionActual.setIdAerolinea(idAerolinea);
            avionActual.setTipoAvion(tipo);
            avionActual.setCapacidad(capacidad);

            if (listener != null) listener.onAvionSaved(avionActual);

            dismiss();

        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Revise que los campos numéricos sean correctos", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), "Error al guardar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}