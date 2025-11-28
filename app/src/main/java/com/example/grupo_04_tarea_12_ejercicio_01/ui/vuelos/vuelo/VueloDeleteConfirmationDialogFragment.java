package com.example.grupo_04_tarea_12_ejercicio_01.ui.vuelos.vuelo;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.grupo_04_tarea_12_ejercicio_01.R;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Vuelo;

import java.util.Locale;

public class VueloDeleteConfirmationDialogFragment extends DialogFragment {

    private Vuelo vuelo;
    private OnDeleteConfirmedListener listener;

    public interface OnDeleteConfirmedListener {
        void onDeleteConfirmed(Vuelo vuelo);
    }

    public static VueloDeleteConfirmationDialogFragment newInstance(Vuelo vuelo) {
        VueloDeleteConfirmationDialogFragment fragment = new VueloDeleteConfirmationDialogFragment();
        fragment.vuelo = vuelo;
        return fragment;
    }

    public void setListener(OnDeleteConfirmedListener listener) {
        this.listener = listener;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_vuelo_delete_confirmation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvId = view.findViewById(R.id.tvDeleteVueloId);
        TextView tvDetails = view.findViewById(R.id.tvDeleteVueloDetails);
        Button btnConfirmar = view.findViewById(R.id.btnConfirmarDeleteVuelo);
        Button btnCancelar = view.findViewById(R.id.btnCancelarDeleteVuelo);

        if (vuelo != null) {
            tvId.setText("Eliminar Vuelo #" + vuelo.getIdVuelo());

            String detalle = String.format(
                    Locale.getDefault(),
                    "Ruta: Aeropuerto %d → Aeropuerto %d\nAvión ID: %d | Filas: %d",
                    vuelo.getIdAeropuertoOrigen(),
                    vuelo.getIdAeropuertoDestino(),
                    vuelo.getIdAvion(),
                    vuelo.getIdFila()
            );

            tvDetails.setText(detalle);
        }

        btnCancelar.setOnClickListener(v -> dismiss());

        btnConfirmar.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteConfirmed(vuelo);
            }
            dismiss();
        });
    }
}