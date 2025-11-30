package com.example.grupo_04_tarea_12_ejercicio_01.ui.booking.reservas;

import android.app.DatePickerDialog;
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
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Pasajero;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Reserva;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Vuelo;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReservaFormDialogFragment extends DialogFragment {

    private AutoCompleteTextView spinnerPasajero, spinnerVuelo;
    private TextInputEditText etFecha, etCosto, etObservacion;
    private TextView tvDialogTitle;
    private Button btnGuardar, btnCancelar;

    private Reserva reservaActual;
    private OnReservaSavedListener listener;

    private List<PasajeroDisplayItem> pasajerosDisplayList = new ArrayList<>();
    private List<VueloDisplayItem> vuelosDisplayList = new ArrayList<>();
    private ArrayAdapter<String> pasajerosAdapter;
    private ArrayAdapter<String> vuelosAdapter;

    private final Calendar calendar = Calendar.getInstance();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    public interface OnReservaSavedListener {
        void onReservaSaved(Reserva reserva);
    }

    public void setListener(OnReservaSavedListener listener) {
        this.listener = listener;
    }

    public void setPasajerosYVuelos(List<Pasajero> pasajeros, List<Vuelo> vuelos, List<Aeropuerto> aeropuertos) {
        // Construir DisplayItems para pasajeros
        this.pasajerosDisplayList.clear();
        for (Pasajero p : pasajeros) {
            this.pasajerosDisplayList.add(new PasajeroDisplayItem(p));
        }

        // Construir DisplayItems para vuelos
        this.vuelosDisplayList.clear();
        for (Vuelo v : vuelos) {
            Aeropuerto origen = findAeropuertoById(v.getIdAeropuertoOrigen(), aeropuertos);
            Aeropuerto destino = findAeropuertoById(v.getIdAeropuertoDestino(), aeropuertos);
            this.vuelosDisplayList.add(new VueloDisplayItem(v, origen, destino));
        }
    }

    private Aeropuerto findAeropuertoById(int id, List<Aeropuerto> aeropuertos) {
        for (Aeropuerto a : aeropuertos) {
            if (a.getIdAeropuerto() == id) {
                return a;
            }
        }
        return null;
    }

    public static ReservaFormDialogFragment newInstance(Reserva reserva,
                                                        List<Pasajero> pasajeros,
                                                        List<Vuelo> vuelos,
                                                        List<Aeropuerto> aeropuertos) {
        ReservaFormDialogFragment fragment = new ReservaFormDialogFragment();
        fragment.reservaActual = reserva;
        fragment.setPasajerosYVuelos(pasajeros, vuelos, aeropuertos);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_reserva_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        setupSpinners();
        setupDatePicker();

        if (reservaActual != null) {
            populateFields();
            tvDialogTitle.setText("Editar Reserva");
        }

        btnCancelar.setOnClickListener(v -> dismiss());
        btnGuardar.setOnClickListener(v -> guardarReserva());
    }

    private void initViews(View view) {
        tvDialogTitle = view.findViewById(R.id.tvDialogTitle);
        spinnerPasajero = view.findViewById(R.id.spinnerPasajero);
        spinnerVuelo = view.findViewById(R.id.spinnerVuelo);
        etFecha = view.findViewById(R.id.etFecha);
        etCosto = view.findViewById(R.id.etCosto);
        etObservacion = view.findViewById(R.id.etObservacion);
        btnGuardar = view.findViewById(R.id.btnGuardar);
        btnCancelar = view.findViewById(R.id.btnCancelar);
    }

    private void setupSpinners() {
        // Configurar Spinner de Pasajeros
        List<String> pasajerosNames = new ArrayList<>();
        for (PasajeroDisplayItem item : pasajerosDisplayList) {
            pasajerosNames.add(item.getDisplayText());
        }

        pasajerosAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                pasajerosNames
        );
        spinnerPasajero.setAdapter(pasajerosAdapter);

        // Configurar Spinner de Vuelos
        List<String> vuelosDisplay = new ArrayList<>();
        for (VueloDisplayItem item : vuelosDisplayList) {
            vuelosDisplay.add(item.getDisplayText());
        }

        vuelosAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                vuelosDisplay
        );
        spinnerVuelo.setAdapter(vuelosAdapter);
    }

    private void setupDatePicker() {
        etFecha.setOnClickListener(v -> {
            new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                etFecha.setText(dateFormat.format(calendar.getTime()));
            }, calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show();
        });
    }

    private void populateFields() {
        // Buscar y seleccionar el pasajero
        for (PasajeroDisplayItem item : pasajerosDisplayList) {
            if (item.getIdPasajero() == reservaActual.getIdPasajero()) {
                spinnerPasajero.setText(item.getDisplayText(), false);
                break;
            }
        }

        // Buscar y seleccionar el vuelo
        for (VueloDisplayItem item : vuelosDisplayList) {
            if (item.getIdVuelo() == reservaActual.getIdVuelo()) {
                spinnerVuelo.setText(item.getDisplayText(), false);
                break;
            }
        }

        etCosto.setText(String.valueOf(reservaActual.getCosto()));
        etObservacion.setText(reservaActual.getObservacion());

        if (reservaActual.getFecha() != null) {
            etFecha.setText(dateFormat.format(reservaActual.getFecha()));
            calendar.setTime(reservaActual.getFecha());
        }
    }

    private void guardarReserva() {
        try {
            // Validar selección de pasajero
            String pasajeroText = spinnerPasajero.getText().toString();
            if (pasajeroText.isEmpty()) {
                Toast.makeText(getContext(), "Seleccione un pasajero", Toast.LENGTH_SHORT).show();
                return;
            }

            // Obtener ID del pasajero seleccionado
            int idPasajero = -1;
            for (PasajeroDisplayItem item : pasajerosDisplayList) {
                if (item.getDisplayText().equals(pasajeroText)) {
                    idPasajero = item.getIdPasajero();
                    break;
                }
            }

            if (idPasajero == -1) {
                Toast.makeText(getContext(), "Pasajero no válido", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validar selección de vuelo
            String vueloText = spinnerVuelo.getText().toString();
            if (vueloText.isEmpty()) {
                Toast.makeText(getContext(), "Seleccione un vuelo", Toast.LENGTH_SHORT).show();
                return;
            }

            // Obtener ID del vuelo seleccionado
            int idVuelo = -1;
            for (VueloDisplayItem item : vuelosDisplayList) {
                if (item.getDisplayText().equals(vueloText)) {
                    idVuelo = item.getIdVuelo();
                    break;
                }
            }

            if (idVuelo == -1) {
                Toast.makeText(getContext(), "Vuelo no válido", Toast.LENGTH_SHORT).show();
                return;
            }

            double costo = Double.parseDouble(etCosto.getText().toString());
            Date fecha = dateFormat.parse(etFecha.getText().toString());
            String observacion = etObservacion.getText().toString();

            if (reservaActual == null) {
                reservaActual = new Reserva();
            }

            reservaActual.setIdPasajero(idPasajero);
            reservaActual.setIdVuelo(idVuelo);
            reservaActual.setCosto(costo);
            reservaActual.setFecha(fecha);
            reservaActual.setObservacion(observacion);

            if (listener != null) {
                listener.onReservaSaved(reservaActual);
            }
            dismiss();

        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Por favor revise los números ingresados",
                    Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            Toast.makeText(getContext(), "Formato de fecha inválido",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), "Complete todos los campos requeridos",
                    Toast.LENGTH_SHORT).show();
        }
    }
}