package com.example.grupo_04_tarea_12_ejercicio_01.ui.clientes.aerolineas;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.grupo_04_tarea_12_ejercicio_01.R;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Aerolinea;
import com.example.grupo_04_tarea_12_ejercicio_01.ui.clientes.ClientesViewModel;

public class AgregarAerolineaDialog extends DialogFragment {

    private Aerolinea aerolineaEditar = null;

    public void setAerolineaEditar(Aerolinea aerolinea) {
        this.aerolineaEditar = aerolinea;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_agregar_aerolinea, null);

        EditText etNombre = view.findViewById(R.id.etNombre);
        EditText etRuc = view.findViewById(R.id.etRuc);
        Button btnGuardar = view.findViewById(R.id.btnGuardar);
        Button btnCancelar = view.findViewById(R.id.btnCancelar);

        ClientesViewModel viewModel = new ViewModelProvider(requireActivity()).get(ClientesViewModel.class);

        if (aerolineaEditar != null) {
            etNombre.setText(aerolineaEditar.getNombre());
            etRuc.setText(aerolineaEditar.getRuc());
            btnGuardar.setText("Actualizar");
        }

        btnGuardar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString();
            String ruc = etRuc.getText().toString();

            if (nombre.isEmpty() || ruc.isEmpty()) {
                Toast.makeText(getContext(), "Complete los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            if (ruc.length() != 11) {
                etRuc.setError("El RUC debe tener 11 dígitos");
                return;
            }

            Aerolinea nueva = new Aerolinea(ruc, nombre);

            if (aerolineaEditar == null) {
                viewModel.registrarAerolinea(nueva);
                Toast.makeText(getContext(), "Aerolínea Registrada", Toast.LENGTH_SHORT).show();
            } else {
                nueva.setIdAerolinea(aerolineaEditar.getIdAerolinea());
                viewModel.actualizarAerolinea(nueva);
                Toast.makeText(getContext(), "Aerolínea Actualizada", Toast.LENGTH_SHORT).show();
            }
            dismiss();
        });

        btnCancelar.setOnClickListener(v -> dismiss());

        builder.setView(view);
        return builder.create();
    }
}