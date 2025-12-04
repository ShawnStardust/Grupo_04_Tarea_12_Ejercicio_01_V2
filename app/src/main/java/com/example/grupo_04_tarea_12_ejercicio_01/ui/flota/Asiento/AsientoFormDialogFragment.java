package com.example.grupo_04_tarea_12_ejercicio_01.ui.flota.Asiento;

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
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Asiento;
import com.google.android.material.textfield.TextInputEditText;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AsientoFormDialogFragment extends DialogFragment {

    private TextInputEditText etIdVuelo, etFila, etEstado, etIdReserva;
    private TextView tvDialogTitle;
    private Button btnGuardar, btnCancelar;

    private Asiento asientoActual;
    private OnAsientoSavedListener listener;

    public interface OnAsientoSavedListener {
        void onAsientoSaved(Asiento asiento);
    }

    public void setListener(OnAsientoSavedListener listener) {
        this.listener = listener;
    }

    public static AsientoFormDialogFragment newInstance(Asiento asiento) {
        AsientoFormDialogFragment fragment = new AsientoFormDialogFragment();
        if (asiento != null) fragment.asientoActual = asiento;
        return fragment;
    }

    public AsientoFormDialogFragment() {}

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
        return inflater.inflate(R.layout.fragment_asiento_form_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

        if (asientoActual != null && asientoActual.getIdAsiento() > 0) {
            populateFields();
            tvDialogTitle.setText("Editar Asiento #" + asientoActual.getIdAsiento());
        } else {
            tvDialogTitle.setText("Nuevo Asiento");
        }

        btnCancelar.setOnClickListener(v -> dismiss());
        btnGuardar.setOnClickListener(v -> guardarAsiento());
    }

    private void initViews(View view) {
        tvDialogTitle = view.findViewById(R.id.tvDialogTitleAsiento);

        etIdVuelo = view.findViewById(R.id.etIdVueloAsiento);
        etFila = view.findViewById(R.id.etFilaAsiento);
        etEstado = view.findViewById(R.id.etEstadoAsiento);
        etIdReserva = view.findViewById(R.id.etIdReservaAsiento);

        btnGuardar = view.findViewById(R.id.btnGuardarAsiento);
        btnCancelar = view.findViewById(R.id.btnCancelarAsiento);
    }

    private void populateFields() {
        etIdVuelo.setText(String.valueOf(asientoActual.getIdVuelo()));
        etFila.setText(String.valueOf(asientoActual.getFila()));
        etEstado.setText(asientoActual.getEstado());

        if (asientoActual.getIdReserva() > 0) {
            etIdReserva.setText(String.valueOf(asientoActual.getIdReserva()));
        }
    }

    private void guardarAsiento() {
        try {
            int idVuelo = Integer.parseInt(etIdVuelo.getText().toString());
            int fila = Integer.parseInt(etFila.getText().toString());
            String estado = etEstado.getText().toString().trim();

            // ID Reserva es opcional (puede ser 0 si no está reservado)
            int idReserva = 0;
            String idReservaStr = etIdReserva.getText().toString();
            if (!idReservaStr.isEmpty()) {
                idReserva = Integer.parseInt(idReservaStr);
            }

            if (estado.isEmpty()) {
                estado = "Disponible"; // Valor por defecto
            }

            if (asientoActual == null) {
                asientoActual = new Asiento();
            }

            asientoActual.setIdVuelo(idVuelo);
            asientoActual.setFila(fila);
            asientoActual.setEstado(estado);
            asientoActual.setIdReserva(idReserva);

            if (listener != null) listener.onAsientoSaved(asientoActual);

            dismiss();

        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Revise los campos numéricos (Vuelo, Fila, Reserva)", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), "Error al guardar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}