package com.example.grupo_04_tarea_12_ejercicio_01.ui.flota.Aeropuerto;

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
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Aeropuerto;

import java.util.Locale;

// Cambiado de Fragment a DialogFragment
public class AeropuertoDeleteConfirmationDialogFragment extends DialogFragment {

    private Aeropuerto aeropuerto;
    private OnDeleteConfirmedListener listener;

    /**
     * Interfaz para notificar cuando el usuario confirma la eliminación.
     */
    public interface OnDeleteConfirmedListener {
        void onDeleteConfirmed(Aeropuerto aeropuerto);
    }

    // Factory method adaptado para recibir el objeto Aeropuerto
    public static AeropuertoDeleteConfirmationDialogFragment newInstance(Aeropuerto aeropuerto) {
        AeropuertoDeleteConfirmationDialogFragment fragment = new AeropuertoDeleteConfirmationDialogFragment();
        fragment.aeropuerto = aeropuerto;
        return fragment;
    }

    public void setListener(OnDeleteConfirmedListener listener) {
        this.listener = listener;
    }

    // Constructor vacío requerido para DialogFragment
    public AeropuertoDeleteConfirmationDialogFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            // Aseguramos que el fondo sea transparente para que se vea el CardView del layout
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Asume que el layout se llama dialog_aeropuerto_delete_confirmation.xml
        return inflater.inflate(R.layout.fragment_aeropuerto_delete_confirmation_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // IDs del layout dialog_aeropuerto_delete_confirmation.xml
        TextView tvId = view.findViewById(R.id.tvDeleteAeropuertoId);
        TextView tvDetails = view.findViewById(R.id.tvDeleteAeropuertoDetails);
        Button btnConfirmar = view.findViewById(R.id.btnConfirmarDeleteAeropuerto);
        Button btnCancelar = view.findViewById(R.id.btnCancelarDeleteAeropuerto);

        if (aeropuerto != null) {
            tvId.setText("Aeropuerto ID: " + aeropuerto.getIdAeropuerto());

            // Construir la cadena de detalles (Nombre y País ID)
            String detalle = String.format(
                    Locale.getDefault(),
                    "Nombre: %s\nPaís ID: %d",
                    aeropuerto.getNombre(),
                    aeropuerto.getIdPais()
            );

            tvDetails.setText(detalle);
        } else {
            // Caso para cuando el objeto aeropuerto es null (prevención de errores)
            tvId.setText("Error de Carga");
            tvDetails.setText("No se pudo cargar la información del aeropuerto a eliminar.");
        }

        btnCancelar.setOnClickListener(v -> dismiss());

        btnConfirmar.setOnClickListener(v -> {
            if (listener != null && aeropuerto != null) {
                listener.onDeleteConfirmed(aeropuerto);
            }
            dismiss();
        });
    }
}