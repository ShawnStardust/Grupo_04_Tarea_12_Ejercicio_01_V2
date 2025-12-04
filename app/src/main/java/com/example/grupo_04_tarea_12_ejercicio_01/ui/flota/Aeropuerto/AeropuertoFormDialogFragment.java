package com.example.grupo_04_tarea_12_ejercicio_01.ui.flota.Aeropuerto;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.grupo_04_tarea_12_ejercicio_01.R;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Aeropuerto;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Pais;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.usecase.PaisUseCase;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AeropuertoFormDialogFragment extends DialogFragment {

    // Vistas del Layout
    private TextInputEditText etIdAeropuerto, etNombreAeropuerto;
    private AutoCompleteTextView spinnerPais;
    private TextView tvDialogTitle;
    private Button btnGuardar, btnCancelar;
    private View tilIdAeropuerto;

    // Datos y Lógica
    private Aeropuerto aeropuertoActual;
    private Map<String, Integer> paisMap;


    @Inject PaisUseCase paisUseCase;

    // ──────────────────────────────────────────────────────────
    // ⚠️ INTERFAZ DE CALLBACK Y SETTER (MOVIMIENTOS CLAVE AQUÍ)
    // ──────────────────────────────────────────────────────────

    private OnAeropuertoSavedListener listener; // Declaración de campo

    /**
     * Interfaz para notificar al ViewModel cuando se guarda un Aeropuerto.
     */
    public interface OnAeropuertoSavedListener {
        void onAeropuertoSaved(Aeropuerto aeropuerto);
    }

    /**
     * Método Setter público. Lo llama el fragmento padre.
     */
    public void setListener(OnAeropuertoSavedListener listener) {
        this.listener = listener;
    }

    // ──────────────────────────────────────────────────────────

    public AeropuertoFormDialogFragment() {} // Constructor vacío

    public static AeropuertoFormDialogFragment newInstance(Aeropuerto aeropuerto) {
        AeropuertoFormDialogFragment fragment = new AeropuertoFormDialogFragment();
        if (aeropuerto != null) fragment.aeropuertoActual = aeropuerto;
        return fragment;
    }

    // ... (Métodos onStart, onCreateView, etc. se mantienen) ...

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
        return inflater.inflate(R.layout.fragment_aeropuerto_form_dialog, container, false); // Asumimos que el layout es dialog_aeropuerto_form
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        loadSpinnerData();

        if (aeropuertoActual != null && aeropuertoActual.getIdAeropuerto() > 0) {
            populateFields();
            tvDialogTitle.setText("Editar Aeropuerto #" + aeropuertoActual.getIdAeropuerto());
            tilIdAeropuerto.setVisibility(View.VISIBLE);
        } else {
            tvDialogTitle.setText("Nuevo Aeropuerto");
            tilIdAeropuerto.setVisibility(View.GONE);
        }

        btnCancelar.setOnClickListener(v -> dismiss());
        btnGuardar.setOnClickListener(v -> guardarAeropuerto());
    }

    private void initViews(View view) {
        tvDialogTitle = view.findViewById(R.id.tvDialogTitleAeropuerto);

        tilIdAeropuerto = view.findViewById(R.id.tilIdAeropuerto);
        etIdAeropuerto = view.findViewById(R.id.etIdAeropuerto);

        spinnerPais = view.findViewById(R.id.spinnerPais);
        etNombreAeropuerto = view.findViewById(R.id.etNombreAeropuerto);

        btnGuardar = view.findViewById(R.id.btnGuardarAeropuerto);
        btnCancelar = view.findViewById(R.id.btnCancelarAeropuerto);
    }

    private void loadSpinnerData() {
        new Thread(() -> {
            try {
                List<Pais> paises = paisUseCase.getAllPaises();

                paisMap = paises.stream()
                        .collect(Collectors.toMap(Pais::getNombre, Pais::getIdPais, (a, b) -> a, HashMap::new));

                List<String> paisNombres = paises.stream()
                        .map(Pais::getNombre)
                        .collect(Collectors.toList());

                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                                android.R.layout.simple_dropdown_item_1line, paisNombres);
                        spinnerPais.setAdapter(adapter);

                        if (aeropuertoActual != null && aeropuertoActual.getIdAeropuerto() > 0) {
                            populateFields();
                        }
                    });
                }
            } catch (Exception e) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Error al cargar países: " + e.getMessage(), Toast.LENGTH_LONG).show());
                }
            }
        }).start();
    }

    private void populateFields() {
        if (aeropuertoActual.getIdAeropuerto() > 0) {
            etIdAeropuerto.setText(String.valueOf(aeropuertoActual.getIdAeropuerto()));
        }

        etNombreAeropuerto.setText(aeropuertoActual.getNombre());

        String nombrePaisActual = paisMap.entrySet().stream()
                .filter(entry -> entry.getValue().equals(aeropuertoActual.getIdPais()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse("");

        spinnerPais.setText(nombrePaisActual, false);
    }


    private void guardarAeropuerto() {
        try {
            // 1. Obtener y validar datos
            String nombrePaisSeleccionado = spinnerPais.getText().toString();
            String nombreAeropuerto = etNombreAeropuerto.getText().toString().trim();

            if (nombreAeropuerto.isEmpty() || nombrePaisSeleccionado.isEmpty()) {
                Toast.makeText(getContext(), "Complete el nombre y seleccione un país.", Toast.LENGTH_SHORT).show();
                return;
            }

            // 2. Traducir el nombre del País a ID
            Integer idPais = paisMap.get(nombrePaisSeleccionado);
            if (idPais == null) {
                Toast.makeText(getContext(), "Seleccione un país válido de la lista.", Toast.LENGTH_SHORT).show();
                return;
            }

            // 3. Crear o actualizar objeto Aeropuerto
            if (aeropuertoActual == null) {
                aeropuertoActual = new Aeropuerto();
            }

            aeropuertoActual.setNombre(nombreAeropuerto);
            aeropuertoActual.setIdPais(idPais);

            // 4. Notificar al listener y cerrar
            if (listener != null) listener.onAeropuertoSaved(aeropuertoActual);

            dismiss();

        } catch (Exception e) {
            Toast.makeText(getContext(), "Error al guardar: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}