package com.example.grupo_04_tarea_12_ejercicio_01.ui.vuelos.tarifas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grupo_04_tarea_12_ejercicio_01.R;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Tarifa;

import java.util.List;
import java.util.Locale;

public class TarifasAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private List<Tarifa> tarifasList;
    private OnTarifaClickListener editListener;
    private OnTarifaClickListener deleteListener;

    public interface OnTarifaClickListener {
        void onClick(Tarifa tarifa);
    }

    public TarifasAdapter(List<Tarifa> tarifasList,
                          OnTarifaClickListener editListener,
                          OnTarifaClickListener deleteListener) {
        this.tarifasList = tarifasList;
        this.editListener = editListener;
        this.deleteListener = deleteListener;
    }

    public void updateList(List<Tarifa> newTarifasList) {
        this.tarifasList = newTarifasList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == tarifasList.size()) {
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
                .inflate(R.layout.item_tarifa, parent, false);
        return new TarifaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == TYPE_FOOTER) {
            return;
        }

        Tarifa tarifa = tarifasList.get(position);
        TarifaViewHolder vh = (TarifaViewHolder) holder;

        vh.tvTarifaId.setText("Tarifa ID: " + tarifa.getIdTarifa());
        vh.tvTarifaClase.setText("Clase: " + tarifa.getClase());

        vh.tvTarifaPrecio.setText(String.format(
                Locale.getDefault(),
                "Precio base: $%.2f",
                tarifa.getPrecio()
        ));

        vh.tvTarifaImpuesto.setText(String.format(
                Locale.getDefault(),
                "Impuesto: $%.2f",
                tarifa.getImpuesto()
        ));

        double total = tarifa.getPrecio() + tarifa.getImpuesto();
        vh.tvTarifaTotal.setText(String.format(
                Locale.getDefault(),
                "Total: $%.2f",
                total
        ));

        vh.btnEditTarifa.setOnClickListener(v -> {
            if (editListener != null) editListener.onClick(tarifa);
        });

        vh.btnDeleteTarifa.setOnClickListener(v -> {
            if (deleteListener != null) deleteListener.onClick(tarifa);
        });
    }

    @Override
    public int getItemCount() {
        return tarifasList.size() + 1;
    }

    static class TarifaViewHolder extends RecyclerView.ViewHolder {
        TextView tvTarifaId;
        TextView tvTarifaClase;
        TextView tvTarifaPrecio;
        TextView tvTarifaImpuesto;
        TextView tvTarifaTotal;
        Button btnEditTarifa;
        Button btnDeleteTarifa;

        public TarifaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTarifaId = itemView.findViewById(R.id.tvTarifaId);
            tvTarifaClase = itemView.findViewById(R.id.tvTarifaClase);
            tvTarifaPrecio = itemView.findViewById(R.id.tvTarifaPrecio);
            tvTarifaImpuesto = itemView.findViewById(R.id.tvTarifaImpuesto);
            tvTarifaTotal = itemView.findViewById(R.id.tvTarifaTotal);
            btnEditTarifa = itemView.findViewById(R.id.btnEditTarifa);
            btnDeleteTarifa = itemView.findViewById(R.id.btnDeleteTarifa);
        }
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}