package com.example.grupo_04_tarea_12_ejercicio_01.ui.flota.Asiento;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.grupo_04_tarea_12_ejercicio_01.R;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Asiento;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Vuelo;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AsientoFormDialogFragment extends DialogFragment {

    private Spinner spinnerVuelos, spinnerEstado;
    private TextInputEditText etFila, etIdReserva;
    private TextView tvDialogTitle;
    private Button btnGuardar, btnCancelar;

    private Asiento asientoActual;
    private OnAsientoSavedListener listener;
    private AsientoFormViewModel viewModel;

    private List<Vuelo> vuelosList = new ArrayList<>();
    private List<String> estadosList = new ArrayList<>();

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

        viewModel = new ViewModelProvider(this).get(AsientoFormViewModel.class);

        initViews(view);
        setupEstadosSpinner();
        observeViewModel();

        if (asientoActual != null && asientoActual.getIdAsiento() > 0) {
            tvDialogTitle.setText("Editar Asiento #" + asientoActual.getIdAsiento());
        } else {
            tvDialogTitle.setText("Nuevo Asiento");
        }

        btnCancelar.setOnClickListener(v -> dismiss());
        btnGuardar.setOnClickListener(v -> guardarAsiento());

        // Cargar los vuelos
        viewModel.loadVuelos();
    }

    private void initViews(View view) {
        tvDialogTitle = view.findViewById(R.id.tvDialogTitleAsiento);
        spinnerVuelos = view.findViewById(R.id.spinnerVuelosAsiento);
        spinnerEstado = view.findViewById(R.id.spinnerEstadoAsiento);
        etFila = view.findViewById(R.id.etFilaAsiento);
        etIdReserva = view.findViewById(R.id.etIdReservaAsiento);
        btnGuardar = view.findViewById(R.id.btnGuardarAsiento);
        btnCancelar = view.findViewById(R.id.btnCancelarAsiento);
    }

    private void setupEstadosSpinner() {
        estadosList.add("Disponible");
        estadosList.add("Ocupado");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                estadosList
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstado.setAdapter(adapter);
    }

    private void observeViewModel() {
        viewModel.vuelosLiveData.observe(getViewLifecycleOwner(), vuelos -> {
            if (vuelos != null && !vuelos.isEmpty()) {
                vuelosList.clear();
                vuelosList.addAll(vuelos);
                setupVuelosSpinner();

                // Si estamos editando, seleccionar el vuelo correcto
                if (asientoActual != null && asientoActual.getIdAsiento() > 0) {
                    populateFields();
                }
            }
        });
    }

    private void setupVuelosSpinner() {
        List<String> vuelosDisplay = new ArrayList<>();
        for (Vuelo vuelo : vuelosList) {
            vuelosDisplay.add("Vuelo #" + vuelo.getIdVuelo() +
                    " (Origen: " + vuelo.getIdAeropuertoOrigen() +
                    " → Destino: " + vuelo.getIdAeropuertoDestino() + ")");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                vuelosDisplay
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVuelos.setAdapter(adapter);
    }

    private void populateFields() {
        // Seleccionar el vuelo correcto en el spinner
        for (int i = 0; i < vuelosList.size(); i++) {
            if (vuelosList.get(i).getIdVuelo() == asientoActual.getIdVuelo()) {
                spinnerVuelos.setSelection(i);
                break;
            }
        }

        // Seleccionar el estado correcto
        int estadoPosition = estadosList.indexOf(asientoActual.getEstado());
        if (estadoPosition >= 0) {
            spinnerEstado.setSelection(estadoPosition);
        }

        // Llenar campos de texto
        etFila.setText(String.valueOf(asientoActual.getFila()));

        // ID Reserva solo si existe
        if (asientoActual.getIdReserva() > 0) {
            etIdReserva.setText(String.valueOf(asientoActual.getIdReserva()));
        }
    }

    private void guardarAsiento() {
        try {
            // Validar que haya vuelos disponibles
            if (vuelosList.isEmpty()) {
                Toast.makeText(getContext(), "No hay vuelos disponibles. Debe crear vuelos primero.", Toast.LENGTH_LONG).show();
                return;
            }

            // Obtener el vuelo seleccionado
            int vueloPosition = spinnerVuelos.getSelectedItemPosition();
            if (vueloPosition < 0 || vueloPosition >= vuelosList.size()) {
                Toast.makeText(getContext(), "Seleccione un vuelo válido", Toast.LENGTH_SHORT).show();
                return;
            }
            int idVuelo = vuelosList.get(vueloPosition).getIdVuelo();

            // Obtener el estado seleccionado
            if (spinnerEstado.getSelectedItem() == null) {
                Toast.makeText(getContext(), "Seleccione un estado", Toast.LENGTH_SHORT).show();
                return;
            }
            String estado = spinnerEstado.getSelectedItem().toString();

            // Validar y obtener fila
            String filaStr = etFila.getText() != null ? etFila.getText().toString().trim() : "";
            if (filaStr.isEmpty()) {
                Toast.makeText(getContext(), "Debe ingresar el número de fila", Toast.LENGTH_SHORT).show();
                etFila.requestFocus();
                return;
            }

            int fila;
            try {
                fila = Integer.parseInt(filaStr);
                if (fila <= 0) {
                    Toast.makeText(getContext(), "El número de fila debe ser mayor a 0", Toast.LENGTH_SHORT).show();
                    etFila.requestFocus();
                    return;
                }
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Ingrese un número válido para la fila", Toast.LENGTH_SHORT).show();
                etFila.requestFocus();
                return;
            }

            // ID Reserva es OPCIONAL - manejar correctamente el caso vacío
            Integer idReserva = null;
            String idReservaStr = etIdReserva.getText() != null ? etIdReserva.getText().toString().trim() : "";

            if (!idReservaStr.isEmpty()) {
                try {
                    int tempReserva = Integer.parseInt(idReservaStr);
                    if (tempReserva > 0) {
                        idReserva = tempReserva;
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "ID Reserva debe ser un número válido o dejar vacío", Toast.LENGTH_SHORT).show();
                    etIdReserva.requestFocus();
                    return;
                }
            }

            // Crear o actualizar el asiento
            if (asientoActual == null) {
                asientoActual = new Asiento();
            }

            asientoActual.setIdVuelo(idVuelo);
            asientoActual.setFila(fila);
            asientoActual.setEstado(estado);
            asientoActual.setIdReserva(idReserva != null ? idReserva : 0);

            if (listener != null) {
                listener.onAsientoSaved(asientoActual);
            }

            Toast.makeText(getContext(), "Asiento guardado correctamente", Toast.LENGTH_SHORT).show();
            dismiss();

        } catch (Exception e) {
            Toast.makeText(getContext(), "Error al guardar: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}