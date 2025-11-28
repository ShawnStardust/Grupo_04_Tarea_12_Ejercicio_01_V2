package com.example.grupo_04_tarea_12_ejercicio_01.ui.vuelos.tarifas;

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
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Tarifa;

import java.util.Locale;

public class TarifaDeleteConfirmationDialogFragment extends DialogFragment {

    private Tarifa tarifa;
    private OnDeleteConfirmedListener listener;

    public interface OnDeleteConfirmedListener {
        void onDeleteConfirmed(Tarifa tarifa);
    }

    public static TarifaDeleteConfirmationDialogFragment newInstance(Tarifa tarifa) {
        TarifaDeleteConfirmationDialogFragment fragment = new TarifaDeleteConfirmationDialogFragment();
        fragment.tarifa = tarifa;
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
        return inflater.inflate(R.layout.dialog_tarifa_delete_confirmation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvId = view.findViewById(R.id.tvDeleteTarifaId);
        TextView tvDetails = view.findViewById(R.id.tvDeleteTarifaDetails);
        Button btnConfirmar = view.findViewById(R.id.btnConfirmarDeleteTarifa);
        Button btnCancelar = view.findViewById(R.id.btnCancelarDeleteTarifa);

        if (tarifa != null) {
            tvId.setText("Eliminar Tarifa #" + tarifa.getIdTarifa());

            String detalle = String.format(
                    Locale.getDefault(),
                    "Clase: %s\nPrecio Base: $%.2f\nImpuesto: $%.2f\nTotal: $%.2f",
                    tarifa.getClase(),
                    tarifa.getPrecio(),
                    tarifa.getImpuesto(),
                    tarifa.getPrecio() + tarifa.getImpuesto()
            );

            tvDetails.setText(detalle);
        }

        btnCancelar.setOnClickListener(v -> dismiss());

        btnConfirmar.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteConfirmed(tarifa);
            }
            dismiss();
        });
    }
}