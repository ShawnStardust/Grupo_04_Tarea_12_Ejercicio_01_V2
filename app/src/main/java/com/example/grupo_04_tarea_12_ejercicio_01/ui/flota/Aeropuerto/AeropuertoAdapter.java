package com.example.grupo_04_tarea_12_ejercicio_01.ui.flota.Aeropuerto;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grupo_04_tarea_12_ejercicio_01.R;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Aeropuerto;

import java.util.List;
import java.util.Locale;


public class AeropuertoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private List<Aeropuerto> aeropuertosList;
    private OnAeropuertoClickListener editListener;
    private OnAeropuertoClickListener deleteListener;


    public interface OnAeropuertoClickListener {
        void onClick(Aeropuerto aeropuerto);
    }

    // 🛠️ Constructor SIMPLE de 3 argumentos
    public AeropuertoAdapter(List<Aeropuerto> aeropuertosList,
                             OnAeropuertoClickListener editListener,
                             OnAeropuertoClickListener deleteListener) {
        this.aeropuertosList = aeropuertosList;
        this.editListener = editListener;
        this.deleteListener = deleteListener;
    }

    public void updateList(List<Aeropuerto> newAeropuertosList) {
        this.aeropuertosList = newAeropuertosList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == aeropuertosList.size()) {
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
                .inflate(R.layout.item_aeropuerto, parent, false);
        return new AeropuertoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == TYPE_FOOTER) {
            return;
        }

        Aeropuerto aeropuerto = aeropuertosList.get(position);
        AeropuertoViewHolder vh = (AeropuertoViewHolder) holder;


        vh.tvAeropuertoId.setText("Aeropuerto ID: " + aeropuerto.getIdAeropuerto());
        vh.tvAeropuertoNombre.setText(aeropuerto.getNombre());

        vh.tvAeropuertoPaisId.setText(String.format(Locale.getDefault(),
                "País ID: %d", aeropuerto.getIdPais())); // Muestra solo el ID




        vh.btnEditAeropuerto.setOnClickListener(v -> {
            if (editListener != null) editListener.onClick(aeropuerto);
        });

        vh.btnDeleteAeropuerto.setOnClickListener(v -> {
            if (deleteListener != null) deleteListener.onClick(aeropuerto);
        });
    }

    @Override
    public int getItemCount() {
        return aeropuertosList.size() + 1;
    }

    // ------------------------------------
    // VIEWHOLDERS (Se mantienen iguales)
    // ------------------------------------

    static class AeropuertoViewHolder extends RecyclerView.ViewHolder {
        TextView tvAeropuertoId;
        TextView tvAeropuertoPaisId;
        TextView tvAeropuertoNombre;
        TextView tvPlaceholder1;
        TextView tvPlaceholder2;
        Button btnEditAeropuerto;
        Button btnDeleteAeropuerto;

        public AeropuertoViewHolder(@NonNull View itemView) {
            super(itemView);
            // Enlazamos los IDs de item_aeropuerto.xml
            tvAeropuertoId = itemView.findViewById(R.id.tvAeropuertoId);
            tvAeropuertoPaisId = itemView.findViewById(R.id.tvAeropuertoPaisId);
            tvAeropuertoNombre = itemView.findViewById(R.id.tvAeropuertoNombre);
            tvPlaceholder1 = itemView.findViewById(R.id.tvPlaceholder1);
            tvPlaceholder2 = itemView.findViewById(R.id.tvPlaceholder2);
            btnEditAeropuerto = itemView.findViewById(R.id.btnEditAeropuerto);
            btnDeleteAeropuerto = itemView.findViewById(R.id.btnDeleteAeropuerto);
        }
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}