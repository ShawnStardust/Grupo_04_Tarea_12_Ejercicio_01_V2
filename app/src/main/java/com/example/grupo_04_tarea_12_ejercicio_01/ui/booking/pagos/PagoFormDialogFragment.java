package com.example.grupo_04_tarea_12_ejercicio_01.ui.booking.pagos;

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
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Pago;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PagoFormDialogFragment extends DialogFragment {

    private TextInputEditText etIdReserva, etFecha, etNumero, etMonto, etImpuesto;
    private AutoCompleteTextView spinnerTipoComprobante;
    private TextView tvDialogTitle;
    private Button btnGuardar, btnCancelar;

    private Pago pagoActual;
    private OnPagoSavedListener listener;

    private final Calendar calendar = Calendar.getInstance();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    // Tipos de comprobante disponibles
    private static final String[] TIPOS_COMPROBANTE = {"Boleta", "Factura"};

    public interface OnPagoSavedListener {
        void onPagoSaved(Pago pago);
    }

    public void setListener(OnPagoSavedListener listener) {
        this.listener = listener;
    }

    public static PagoFormDialogFragment newInstance(Pago pago) {
        PagoFormDialogFragment fragment = new PagoFormDialogFragment();
        if (pago != null) {
            fragment.pagoActual = pago;
        }
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
        return inflater.inflate(R.layout.dialog_pago_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        setupTipoComprobanteSpinner();
        setupDatePicker();

        if (pagoActual != null) {
            populateFields();
            tvDialogTitle.setText("Editar Pago");
        }

        btnCancelar.setOnClickListener(v -> dismiss());
        btnGuardar.setOnClickListener(v -> guardarPago());
    }

    private void initViews(View view) {
        tvDialogTitle = view.findViewById(R.id.tvDialogTitlePago);
        etIdReserva = view.findViewById(R.id.etIdReservaPago);
        etFecha = view.findViewById(R.id.etFechaPago);
        spinnerTipoComprobante = view.findViewById(R.id.spinnerTipoComprobante);
        etNumero = view.findViewById(R.id.etNumeroComprobantePago);
        etMonto = view.findViewById(R.id.etMontoPago);
        etImpuesto = view.findViewById(R.id.etImpuestoPago);
        btnGuardar = view.findViewById(R.id.btnGuardarPago);
        btnCancelar = view.findViewById(R.id.btnCancelarPago);
    }

    /**
     * Configura el spinner de tipo de comprobante
     */
    private void setupTipoComprobanteSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                TIPOS_COMPROBANTE
        );
        spinnerTipoComprobante.setAdapter(adapter);

        // Seleccionar "Boleta" por defecto si no hay pago actual
        if (pagoActual == null) {
            spinnerTipoComprobante.setText(TIPOS_COMPROBANTE[0], false);
        }
    }

    private void setupDatePicker() {
        etFecha.setOnClickListener(v -> {
            new DatePickerDialog(getContext(), (picker, year, month, day) -> {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                etFecha.setText(dateFormat.format(calendar.getTime()));
            }, calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show();
        });
    }

    private void populateFields() {
        etIdReserva.setText(String.valueOf(pagoActual.getIdReserva()));
        etNumero.setText(pagoActual.getNumComprobante());
        etMonto.setText(String.valueOf(pagoActual.getMonto()));
        etImpuesto.setText(String.valueOf(pagoActual.getImpuesto()));

        // Establecer tipo de comprobante
        if (pagoActual.getTipoComprobante() != null) {
            spinnerTipoComprobante.setText(pagoActual.getTipoComprobante(), false);
        }

        // Establecer fecha
        if (pagoActual.getFecha() != null) {
            etFecha.setText(dateFormat.format(pagoActual.getFecha()));
            calendar.setTime(pagoActual.getFecha());
        }
    }

    private void guardarPago() {
        try {
            // Validar ID de Reserva
            String idReservaText = etIdReserva.getText().toString().trim();
            if (idReservaText.isEmpty()) {
                Toast.makeText(getContext(), "Ingrese el ID de Reserva", Toast.LENGTH_SHORT).show();
                return;
            }
            int idReserva = Integer.parseInt(idReservaText);

            // Validar Fecha
            String fechaText = etFecha.getText().toString().trim();
            if (fechaText.isEmpty()) {
                Toast.makeText(getContext(), "Seleccione una fecha", Toast.LENGTH_SHORT).show();
                return;
            }
            Date fecha = dateFormat.parse(fechaText);

            // Validar Tipo de Comprobante
            String tipoComprobante = spinnerTipoComprobante.getText().toString().trim();
            if (tipoComprobante.isEmpty()) {
                Toast.makeText(getContext(), "Seleccione un tipo de comprobante", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validar Número de Comprobante
            String numeroComprobante = etNumero.getText().toString().trim();
            if (numeroComprobante.isEmpty()) {
                Toast.makeText(getContext(), "Ingrese el número de comprobante", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validar Monto
            String montoText = etMonto.getText().toString().trim();
            if (montoText.isEmpty()) {
                Toast.makeText(getContext(), "Ingrese el monto", Toast.LENGTH_SHORT).show();
                return;
            }
            double monto = Double.parseDouble(montoText);

            // Validar Impuesto
            String impuestoText = etImpuesto.getText().toString().trim();
            if (impuestoText.isEmpty()) {
                Toast.makeText(getContext(), "Ingrese el impuesto", Toast.LENGTH_SHORT).show();
                return;
            }
            double impuesto = Double.parseDouble(impuestoText);

            // Crear o actualizar el pago
            if (pagoActual == null) {
                pagoActual = new Pago();
            }

            pagoActual.setIdReserva(idReserva);
            pagoActual.setFecha(fecha);
            pagoActual.setTipoComprobante(tipoComprobante);
            pagoActual.setNumComprobante(numeroComprobante);
            pagoActual.setMonto(monto);
            pagoActual.setImpuesto(impuesto);

            if (listener != null) {
                listener.onPagoSaved(pagoActual);
            }

            dismiss();

        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Revise los valores numéricos ingresados",
                    Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            Toast.makeText(getContext(), "Formato de fecha incorrecto (use dd/MM/yyyy)",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), "Error: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }
}