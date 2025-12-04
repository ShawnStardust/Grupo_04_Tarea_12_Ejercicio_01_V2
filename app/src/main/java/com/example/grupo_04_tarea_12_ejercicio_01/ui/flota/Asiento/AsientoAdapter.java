package com.example.grupo_04_tarea_12_ejercicio_01.ui.flota.Asiento;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grupo_04_tarea_12_ejercicio_01.R;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Asiento;

import java.util.List;

public class AsientoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private List<Asiento> asientosList;
    private OnAsientoClickListener editListener;
    private OnAsientoClickListener deleteListener;

    public interface OnAsientoClickListener {
        void onClick(Asiento asiento);
    }

    public AsientoAdapter(List<Asiento> asientosList,
                          OnAsientoClickListener editListener,
                          OnAsientoClickListener deleteListener) {
        this.asientosList = asientosList;
        this.editListener = editListener;
        this.deleteListener = deleteListener;
    }

    public void updateList(List<Asiento> newAsientosList) {
        this.asientosList = newAsientosList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == asientosList.size()) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            View view = new View(parent.getContext());
            view.setLayoutParams(new RecyclerView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    200 // Espacio para el footer
            ));
            return new FooterViewHolder(view);
        }

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_asiento, parent, false);
        return new AsientoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_FOOTER) return;

        Asiento asiento = asientosList.get(position);
        AsientoViewHolder vh = (AsientoViewHolder) holder;

        vh.tvAsientoId.setText("Asiento ID: " + asiento.getIdAsiento());
        vh.tvAsientoVuelo.setText("Vuelo ID: " + asiento.getIdVuelo());
        vh.tvAsientoFila.setText("Fila: " + asiento.getFila());
        vh.tvAsientoEstado.setText(asiento.getEstado());

        // Manejo visual del estado
        if ("Ocupado".equalsIgnoreCase(asiento.getEstado())) {
            vh.tvAsientoEstado.setTextColor(0xFFFF5722); // Naranja/Rojo para ocupado
        } else {
            vh.tvAsientoEstado.setTextColor(0xFF4CAF50); // Verde para disponible
        }

        // Mostrar Reserva solo si existe (> 0 o diferente de null/0 según tu lógica de BD)
        if (asiento.getIdReserva() > 0) {
            vh.tvAsientoReserva.setText("Reserva ID: " + asiento.getIdReserva());
            vh.tvAsientoReserva.setVisibility(View.VISIBLE);
        } else {
            vh.tvAsientoReserva.setVisibility(View.GONE);
        }

        vh.btnEditAsiento.setOnClickListener(v -> {
            if (editListener != null) editListener.onClick(asiento);
        });

        vh.btnDeleteAsiento.setOnClickListener(v -> {
            if (deleteListener != null) deleteListener.onClick(asiento);
        });
    }

    @Override
    public int getItemCount() {
        return asientosList.size() + 1;
    }

    static class AsientoViewHolder extends RecyclerView.ViewHolder {
        TextView tvAsientoId, tvAsientoVuelo, tvAsientoFila, tvAsientoEstado, tvAsientoReserva;
        Button btnEditAsiento, btnDeleteAsiento;

        public AsientoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAsientoId = itemView.findViewById(R.id.tvAsientoId);
            tvAsientoVuelo = itemView.findViewById(R.id.tvAsientoVuelo);
            tvAsientoFila = itemView.findViewById(R.id.tvAsientoFila);
            tvAsientoEstado = itemView.findViewById(R.id.tvAsientoEstado);
            tvAsientoReserva = itemView.findViewById(R.id.tvAsientoReserva);
            btnEditAsiento = itemView.findViewById(R.id.btnEditAsiento);
            btnDeleteAsiento = itemView.findViewById(R.id.btnDeleteAsiento);
        }
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}