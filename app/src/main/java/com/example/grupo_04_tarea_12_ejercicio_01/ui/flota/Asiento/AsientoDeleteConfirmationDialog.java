package com.example.grupo_04_tarea_12_ejercicio_01.ui.flota.Asiento;

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
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Asiento;

import java.util.Locale;

public class AsientoDeleteConfirmationDialog extends DialogFragment {

    private Asiento asiento;
    private OnDeleteConfirmedListener listener;

    public interface OnDeleteConfirmedListener {
        void onDeleteConfirmed(Asiento asiento);
    }

    public static AsientoDeleteConfirmationDialog newInstance(Asiento asiento) {
        AsientoDeleteConfirmationDialog fragment = new AsientoDeleteConfirmationDialog();
        fragment.asiento = asiento;
        return fragment;
    }

    public void setListener(OnDeleteConfirmedListener listener) {
        this.listener = listener;
    }

    public AsientoDeleteConfirmationDialog() {}

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
        return inflater.inflate(R.layout.fragment_asiento_delete_confirmation_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvId = view.findViewById(R.id.tvDeleteAsientoId);
        TextView tvDetails = view.findViewById(R.id.tvDeleteAsientoDetails);
        Button btnConfirmar = view.findViewById(R.id.btnConfirmarDeleteAsiento);
        Button btnCancelar = view.findViewById(R.id.btnCancelarDeleteAsiento);

        if (asiento != null) {
            tvId.setText("Asiento ID: " + asiento.getIdAsiento());
            String detalle = String.format(Locale.getDefault(), "Vuelo: %d | Fila: %d | Estado: %s",
                    asiento.getIdVuelo(), asiento.getFila(), asiento.getEstado());
            tvDetails.setText(detalle);
        }

        btnCancelar.setOnClickListener(v -> dismiss());

        btnConfirmar.setOnClickListener(v -> {
            if (listener != null) listener.onDeleteConfirmed(asiento);
            dismiss();
        });
    }
}