package com.example.grupo_04_tarea_12_ejercicio_01.ui.vuelos.tarifas;

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
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Tarifa;
import com.google.android.material.textfield.TextInputEditText;

public class TarifaFormDialogFragment extends DialogFragment {

    private TextInputEditText etClase, etPrecio, etImpuesto;
    private TextView tvDialogTitle;
    private Button btnGuardar, btnCancelar;

    private Tarifa tarifaActual;
    private OnTarifaSavedListener listener;

    public interface OnTarifaSavedListener {
        void onTarifaSaved(Tarifa tarifa);
    }

    public void setListener(OnTarifaSavedListener listener) {
        this.listener = listener;
    }

    public static TarifaFormDialogFragment newInstance(Tarifa tarifa) {
        TarifaFormDialogFragment fragment = new TarifaFormDialogFragment();
        if (tarifa != null) fragment.tarifaActual = tarifa;
        return fragment;
    }

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
        return inflater.inflate(R.layout.dialog_tarifa_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

        if (tarifaActual != null) {
            populateFields();
            tvDialogTitle.setText("Editar Tarifa #" + tarifaActual.getIdTarifa());
        } else {
            tvDialogTitle.setText("Nueva Tarifa");
        }

        btnCancelar.setOnClickListener(v -> dismiss());
        btnGuardar.setOnClickListener(v -> guardarTarifa());
    }

    private void initViews(View view) {
        tvDialogTitle = view.findViewById(R.id.tvDialogTitleTarifa);

        etClase     = view.findViewById(R.id.etClaseTarifa);
        etPrecio    = view.findViewById(R.id.etPrecioTarifa);
        etImpuesto  = view.findViewById(R.id.etImpuestoTarifa);

        btnGuardar = view.findViewById(R.id.btnGuardarTarifa);
        btnCancelar = view.findViewById(R.id.btnCancelarTarifa);
    }

    private void populateFields() {
        etClase.setText(tarifaActual.getClase());
        etPrecio.setText(String.valueOf(tarifaActual.getPrecio()));
        etImpuesto.setText(String.valueOf(tarifaActual.getImpuesto()));
    }

    private void guardarTarifa() {
        try {
            String clase = etClase.getText().toString().trim();
            double precio = Double.parseDouble(etPrecio.getText().toString());
            double impuesto = Double.parseDouble(etImpuesto.getText().toString());

            if (clase.isEmpty()) {
                Toast.makeText(getContext(), "La clase de tarifa es obligatoria", Toast.LENGTH_SHORT).show();
                return;
            }
            if (precio < 0 || impuesto < 0) {
                Toast.makeText(getContext(), "El precio y el impuesto no pueden ser negativos", Toast.LENGTH_SHORT).show();
                return;
            }

            if (tarifaActual == null) {
                tarifaActual = new Tarifa();
            }

            tarifaActual.setClase(clase);
            tarifaActual.setPrecio(precio);
            tarifaActual.setImpuesto(impuesto);

            if (listener != null) listener.onTarifaSaved(tarifaActual);

            dismiss();

        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Revise los valores numéricos de precio/impuesto", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), "Complete todos los campos requeridos", Toast.LENGTH_SHORT).show();
        }
    }
}