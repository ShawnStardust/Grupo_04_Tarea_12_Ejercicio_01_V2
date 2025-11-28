package com.example.grupo_04_tarea_12_ejercicio_01.ui.vuelos.vuelo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grupo_04_tarea_12_ejercicio_01.R;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Vuelo;

import java.util.List;

public class VuelosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private List<Vuelo> vuelosList;
    private OnVueloClickListener editListener;
    private OnVueloClickListener deleteListener;

    public interface OnVueloClickListener {
        void onClick(Vuelo vuelo);
    }

    public VuelosAdapter(List<Vuelo> vuelosList,
                         OnVueloClickListener editListener,
                         OnVueloClickListener deleteListener) {
        this.vuelosList = vuelosList;
        this.editListener = editListener;
        this.deleteListener = deleteListener;
    }

    public void updateList(List<Vuelo> newVuelosList) {
        this.vuelosList = newVuelosList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == vuelosList.size()) {
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
                    600
            ));
            return new FooterViewHolder(view);
        }

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vuelo, parent, false);
        return new VueloViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == TYPE_FOOTER) {
            return;
        }

        Vuelo vuelo = vuelosList.get(position);
        VueloViewHolder vh = (VueloViewHolder) holder;

        vh.tvVueloId.setText("Vuelo ID: " + vuelo.getIdVuelo());

        vh.tvVueloRuta.setText(String.format("Ruta: %d → %d",
                vuelo.getIdAeropuertoOrigen(),
                vuelo.getIdAeropuertoDestino()));

        vh.tvVueloDetalles.setText(String.format("Avión ID: %d | Filas: %d",
                vuelo.getIdAvion(),
                vuelo.getIdFila()));

        vh.btnEditVuelo.setOnClickListener(v -> {
            if (editListener != null) editListener.onClick(vuelo);
        });

        vh.btnDeleteVuelo.setOnClickListener(v -> {
            if (deleteListener != null) deleteListener.onClick(vuelo);
        });
    }

    @Override
    public int getItemCount() {
        // +1 para incluir el footer
        return vuelosList.size() + 1;
    }

    static class VueloViewHolder extends RecyclerView.ViewHolder {
        TextView tvVueloId;
        TextView tvVueloRuta;
        TextView tvVueloDetalles;
        TextView tvVueloFecha;
        TextView tvVueloHora;
        Button btnEditVuelo;
        Button btnDeleteVuelo;

        public VueloViewHolder(@NonNull View itemView) {
            super(itemView);
            tvVueloId = itemView.findViewById(R.id.tvVueloId);
            tvVueloRuta = itemView.findViewById(R.id.tvVueloRuta);
            tvVueloDetalles = itemView.findViewById(R.id.tvVueloDetalles);
            tvVueloFecha = itemView.findViewById(R.id.tvVueloFecha);
            tvVueloHora = itemView.findViewById(R.id.tvVueloHora);
            btnEditVuelo = itemView.findViewById(R.id.btnEditVuelo);
            btnDeleteVuelo = itemView.findViewById(R.id.btnDeleteVuelo);
        }
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}