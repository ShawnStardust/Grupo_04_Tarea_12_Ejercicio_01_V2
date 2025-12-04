package com.example.grupo_04_tarea_12_ejercicio_01.ui.flota.Avion;

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
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Avion;

import java.util.Locale;

public class AvionDeleteConfirmationDialog extends DialogFragment {

    private Avion avion;
    private OnDeleteConfirmedListener listener;

    public interface OnDeleteConfirmedListener {
        void onDeleteConfirmed(Avion avion);
    }

    public static AvionDeleteConfirmationDialog newInstance(Avion avion) {
        AvionDeleteConfirmationDialog fragment = new AvionDeleteConfirmationDialog();
        fragment.avion = avion;
        return fragment;
    }

    public void setListener(OnDeleteConfirmedListener listener) {
        this.listener = listener;
    }

    public AvionDeleteConfirmationDialog() {
        // Constructor vacío requerido
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
        return inflater.inflate(R.layout.fragment_avion_delete_confirmation_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvId = view.findViewById(R.id.tvDeleteAvionId);
        TextView tvDetails = view.findViewById(R.id.tvDeleteAvionDetails);
        Button btnConfirmar = view.findViewById(R.id.btnConfirmarDeleteAvion);
        Button btnCancelar = view.findViewById(R.id.btnCancelarDeleteAvion);

        if (avion != null) {
            tvId.setText("Avión ID: " + avion.getIdAvion());

            String detalle = String.format(
                    Locale.getDefault(),
                    "Modelo: %s\nAerolínea ID: %d | Capacidad: %d",
                    avion.getTipoAvion(),
                    avion.getIdAerolinea(),
                    avion.getCapacidad()
            );

            tvDetails.setText(detalle);
        }

        btnCancelar.setOnClickListener(v -> dismiss());

        btnConfirmar.setOnClickListener(v -> {
            if (listener != null && avion != null) {
                listener.onDeleteConfirmed(avion);
            }
            dismiss();
        });
    }
}