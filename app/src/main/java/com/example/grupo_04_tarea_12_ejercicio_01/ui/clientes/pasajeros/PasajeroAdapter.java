package com.example.grupo_04_tarea_12_ejercicio_01.ui.clientes.pasajeros;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grupo_04_tarea_12_ejercicio_01.R;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Pasajero;

import java.util.ArrayList;
import java.util.List;

public class PasajeroAdapter extends RecyclerView.Adapter<PasajeroAdapter.PasajeroViewHolder> {

    private List<Pasajero> listaPasajeros = new ArrayList<>();
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onDeleteClick(Pasajero pasajero);
        void onEditClick(Pasajero pasajero);
    }

    public PasajeroAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setPasajeros(List<Pasajero> pasajeros) {
        this.listaPasajeros = pasajeros;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PasajeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pasajero, parent, false);
        return new PasajeroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PasajeroViewHolder holder, int position) {
        Pasajero pasajero = listaPasajeros.get(position);
        holder.tvNombre.setText(pasajero.getNombre() + " " + pasajero.getApaterno());
        holder.tvId.setText("ID: " + pasajero.getIdPasajero());
        holder.tvDocumento.setText(pasajero.getTipoDocumento() + ": " + pasajero.getNumDocumento());
        holder.tvEmail.setText(pasajero.getEmail());
        holder.tvTelefono.setText("Tel: " + pasajero.getTelefono());

        holder.btnEliminar.setOnClickListener(v -> listener.onDeleteClick(pasajero));
        holder.btnEditar.setOnClickListener(v -> listener.onEditClick(pasajero)); // <--- Nuevo
    }

    @Override
    public int getItemCount() {
        return listaPasajeros.size();
    }

    static class PasajeroViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvDocumento, tvId, tvEmail, tvTelefono; // Nuevos TextViews
        ImageButton btnEliminar, btnEditar;

        public PasajeroViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombrePasajero);
            tvDocumento = itemView.findViewById(R.id.tvDocumentoPasajero);
            tvId = itemView.findViewById(R.id.tvIdPasajero); // <--- Nuevo ID en XML
            tvEmail = itemView.findViewById(R.id.tvEmailPasajero); // <--- Nuevo ID en XML
            tvTelefono = itemView.findViewById(R.id.tvTelefonoPasajero); // <--- Nuevo ID en XML
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
            btnEditar = itemView.findViewById(R.id.btnEditar); // <--- Nuevo ID en XML
        }
    }
}