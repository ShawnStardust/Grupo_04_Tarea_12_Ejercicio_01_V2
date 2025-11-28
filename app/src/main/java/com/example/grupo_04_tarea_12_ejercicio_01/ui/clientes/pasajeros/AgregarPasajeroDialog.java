package com.example.grupo_04_tarea_12_ejercicio_01.ui.clientes.pasajeros;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView; // Agregado para el título ID
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.grupo_04_tarea_12_ejercicio_01.R;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Pais;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Pasajero;
import com.example.grupo_04_tarea_12_ejercicio_01.ui.clientes.ClientesViewModel;

import java.util.ArrayList;
import java.util.List;

public class AgregarPasajeroDialog extends DialogFragment {

    private ClientesViewModel viewModel;
    private Pasajero pasajeroAEditar = null;
    private List<Pais> listaPaisesCargados = new ArrayList<>();

    private Spinner spinnerTipoDoc;
    private Spinner spinnerPais;
    private ArrayAdapter<String> adapterTipos;
    private ArrayAdapter<String> adapterPaises;
    private List<String> nombresPaises = new ArrayList<>();

    public void setPasajeroAEditar(Pasajero pasajero) {
        this.pasajeroAEditar = pasajero;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_agregar_pasajero, null);

        TextView tvTituloId = view.findViewById(R.id.tvTituloId);
        EditText etNombre = view.findViewById(R.id.etNombre);
        EditText etAPaterno = view.findViewById(R.id.etAPaterno);
        EditText etAMaterno = view.findViewById(R.id.etAMaterno);
        spinnerTipoDoc = view.findViewById(R.id.spinnerTipoDoc);
        EditText etNumDoc = view.findViewById(R.id.etNumDoc);
        EditText etFechaNac = view.findViewById(R.id.etFechaNac);
        spinnerPais = view.findViewById(R.id.spinnerPais);
        EditText etTelefono = view.findViewById(R.id.etTelefono);
        EditText etEmail = view.findViewById(R.id.etEmail);
        EditText etClave = view.findViewById(R.id.etClave);
        Button btnGuardar = view.findViewById(R.id.btnGuardar);
        Button btnCancelar = view.findViewById(R.id.btnCancelar);

        viewModel = new ViewModelProvider(requireActivity()).get(ClientesViewModel.class);

        String[] tipos = {"DNI", "Pasaporte", "Carnet Extranjería"};
        adapterTipos = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, tipos);
        adapterTipos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoDoc.setAdapter(adapterTipos);

        viewModel.getListaPaises().observe(this, paises -> {
            listaPaisesCargados = paises;
            nombresPaises.clear();
            for (Pais p : paises) {
                nombresPaises.add(p.getNombre());
            }
            adapterPaises = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, nombresPaises);
            adapterPaises.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerPais.setAdapter(adapterPaises);

            if (pasajeroAEditar != null) {
                setSpinnerPais(pasajeroAEditar.getIdPais());
            }
        });

        if (pasajeroAEditar != null) {
            btnGuardar.setText("Actualizar");
            etNombre.setText(pasajeroAEditar.getNombre());
            etAPaterno.setText(pasajeroAEditar.getApaterno());
            etAMaterno.setText(pasajeroAEditar.getAmaterno());
            etNumDoc.setText(pasajeroAEditar.getNumDocumento());
            etFechaNac.setText(pasajeroAEditar.getFechaNacimiento());
            etTelefono.setText(pasajeroAEditar.getTelefono());
            etEmail.setText(pasajeroAEditar.getEmail());
            etClave.setText(pasajeroAEditar.getClave());

            setSpinnerString(spinnerTipoDoc, adapterTipos, pasajeroAEditar.getTipoDocumento());
        }

        btnGuardar.setOnClickListener(v -> {
            if (etNombre.getText().toString().isEmpty() || etNumDoc.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Complete nombre y documento", Toast.LENGTH_SHORT).show();
                return;
            }

            int idPaisSeleccionado = 0;
            if (!listaPaisesCargados.isEmpty() && spinnerPais.getSelectedItemPosition() >= 0) {
                idPaisSeleccionado = listaPaisesCargados.get(spinnerPais.getSelectedItemPosition()).getIdPais();
            }

            Pasajero nuevoPasajero = new Pasajero(
                    etNombre.getText().toString(),
                    etAPaterno.getText().toString(),
                    etAMaterno.getText().toString(),
                    spinnerTipoDoc.getSelectedItem().toString(),
                    etNumDoc.getText().toString(),
                    etFechaNac.getText().toString(),
                    idPaisSeleccionado,
                    etTelefono.getText().toString(),
                    etEmail.getText().toString(),
                    etClave.getText().toString()
            );

            if (pasajeroAEditar == null) {
                viewModel.registrarPasajero(nuevoPasajero);
                Toast.makeText(getContext(), "Registrado", Toast.LENGTH_SHORT).show();
            } else {
                nuevoPasajero.setIdPasajero(pasajeroAEditar.getIdPasajero()); // Mantener ID
                viewModel.actualizarPasajero(nuevoPasajero);
                Toast.makeText(getContext(), "Actualizado", Toast.LENGTH_SHORT).show();
            }
            dismiss();
        });

        btnCancelar.setOnClickListener(v -> dismiss());

        builder.setView(view);
        return builder.create();
    }

    // Este método busca un texto en el spinner y lo selecciona
    private void setSpinnerString(Spinner spinner, ArrayAdapter<String> adapter, String valor) {
        if (valor == null) return;
        int position = adapter.getPosition(valor);
        if (position >= 0) {
            spinner.setSelection(position);
        }
    }

    // Este método busca el ID del país en la lista cargada y lo selecciona en el spinner
    private void setSpinnerPais(int idPais) {
        if (listaPaisesCargados == null || listaPaisesCargados.isEmpty()) return;

        for (int i = 0; i < listaPaisesCargados.size(); i++) {
            if (listaPaisesCargados.get(i).getIdPais() == idPais) {
                spinnerPais.setSelection(i);
                break;
            }
        }
    }
}