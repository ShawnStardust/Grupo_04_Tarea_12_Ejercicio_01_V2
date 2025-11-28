package com.example.grupo_04_tarea_12_ejercicio_01.ui.clientes.paises;

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
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Pais;
import com.example.grupo_04_tarea_12_ejercicio_01.ui.clientes.ClientesViewModel;

public class AgregarPaisDialog extends DialogFragment {

    private ClientesViewModel viewModel;
    private Pais paisAEditar = null;

    public void setpaisAEditar(Pais pasajero) {
        this.paisAEditar = pasajero;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_agregar_pais, null);

        EditText etNombre = view.findViewById(R.id.etNombrePais);
        Button btnGuardar = view.findViewById(R.id.btnGuardar);
        Button btnCancelar = view.findViewById(R.id.btnCancelar);

        //Aquí se conecta el ViewModel:
        viewModel = new ViewModelProvider(requireActivity()).get(ClientesViewModel.class);

        if (paisAEditar != null) {
            etNombre.setText(paisAEditar.getNombre());
            btnGuardar.setText("Actualizar");
        }

        btnGuardar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            if (nombre.isEmpty()) {
                etNombre.setError("Escriba un nombre");
                return;
            }

            Pais nuevoPais = new Pais(
                    etNombre.getText().toString()
            );

            if (paisAEditar == null) {
                // MODO CREAR
                viewModel.registrarPais(nuevoPais);
                Toast.makeText(getContext(), "País Registrado", Toast.LENGTH_SHORT).show();
            } else {
                // MODO EDITAR
                nuevoPais.setIdPais(paisAEditar.getIdPais()); // ¡IMPORTANTE! MANTENER EL ID
                viewModel.actualizarPais(nuevoPais); // Usar método actualizar
                Toast.makeText(getContext(), "País actualizado", Toast.LENGTH_SHORT).show();
            }
            dismiss();
        });

        btnCancelar.setOnClickListener(v -> dismiss());

        builder.setView(view);
        return builder.create();
    }
}