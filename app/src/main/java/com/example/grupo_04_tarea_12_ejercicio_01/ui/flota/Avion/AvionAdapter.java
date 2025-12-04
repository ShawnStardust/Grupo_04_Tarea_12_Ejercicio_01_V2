package com.example.grupo_04_tarea_12_ejercicio_01.ui.flota.Avion; // Asumiendo carpeta Avion con mayúscula

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grupo_04_tarea_12_ejercicio_01.R;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Avion;

import java.util.List;
import java.util.Locale;

public class AvionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private List<Avion> avionesList;
    private OnAvionClickListener editListener;
    private OnAvionClickListener deleteListener;

    public interface OnAvionClickListener {
        void onClick(Avion avion);
    }

    // Constructor simple (sin inyección)
    public AvionAdapter(List<Avion> avionesList,
                        OnAvionClickListener editListener,
                        OnAvionClickListener deleteListener) {
        this.avionesList = avionesList;
        this.editListener = editListener;
        this.deleteListener = deleteListener;
    }

    public void updateList(List<Avion> newAvionesList) {
        this.avionesList = newAvionesList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == avionesList.size()) {
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
                    600 // Espacio para el footer
            ));
            return new FooterViewHolder(view);
        }

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_avion, parent, false);
        return new AvionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_FOOTER) {
            return;
        }

        Avion avion = avionesList.get(position);
        AvionViewHolder vh = (AvionViewHolder) holder;

        // Asignación de datos a las vistas
        vh.tvAvionId.setText("Avión ID: " + avion.getIdAvion());

        // Mostramos el ID de la aerolínea directamente
        vh.tvAvionAerolineaId.setText("Aerolínea ID: " + avion.getIdAerolinea());

        vh.tvAvionTipo.setText(avion.getTipoAvion());

        vh.tvAvionCapacidad.setText(String.format(Locale.getDefault(),
                "Capacidad: %d pasajeros", avion.getCapacidad()));

        // Listeners
        vh.btnEditAvion.setOnClickListener(v -> {
            if (editListener != null) editListener.onClick(avion);
        });

        vh.btnDeleteAvion.setOnClickListener(v -> {
            if (deleteListener != null) deleteListener.onClick(avion);
        });
    }

    @Override
    public int getItemCount() {
        return avionesList.size() + 1;
    }

    // ViewHolders
    static class AvionViewHolder extends RecyclerView.ViewHolder {
        TextView tvAvionId;
        TextView tvAvionAerolineaId;
        TextView tvAvionTipo;
        TextView tvAvionCapacidad;
        Button btnEditAvion;
        Button btnDeleteAvion;

        public AvionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAvionId = itemView.findViewById(R.id.tvAvionId);
            tvAvionAerolineaId = itemView.findViewById(R.id.tvAvionAerolineaId);
            tvAvionTipo = itemView.findViewById(R.id.tvAvionTipo);
            tvAvionCapacidad = itemView.findViewById(R.id.tvAvionCapacidad);
            btnEditAvion = itemView.findViewById(R.id.btnEditAvion);
            btnDeleteAvion = itemView.findViewById(R.id.btnDeleteAvion);
        }
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}