package com.example.grupo_04_tarea_12_ejercicio_01.ui.clientes.aerolineas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.grupo_04_tarea_12_ejercicio_01.R;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Aerolinea;
import java.util.ArrayList;
import java.util.List;

public class AerolineaAdapter extends RecyclerView.Adapter<AerolineaAdapter.AerolineaViewHolder> {

    private List<Aerolinea> lista = new ArrayList<>();
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onDeleteClick(Aerolinea aerolinea);
        void onEditClick(Aerolinea aerolinea);
    }

    public AerolineaAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setAerolineas(List<Aerolinea> lista) {
        this.lista = lista;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AerolineaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_aerolinea, parent, false);
        return new AerolineaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AerolineaViewHolder holder, int position) {
        Aerolinea item = lista.get(position);
        holder.tvNombre.setText(item.getNombre());
        holder.tvRuc.setText("RUC: " + item.getRuc());
        holder.tvId.setText("ID: " + item.getIdAerolinea());

        holder.btnEliminar.setOnClickListener(v -> listener.onDeleteClick(item));
        holder.btnEditar.setOnClickListener(v -> listener.onEditClick(item));
    }

    @Override
    public int getItemCount() { return lista.size(); }

    static class AerolineaViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvRuc, tvId;
        ImageButton btnEliminar, btnEditar;

        public AerolineaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombreAerolinea);
            tvRuc = itemView.findViewById(R.id.tvRucAerolinea);
            tvId = itemView.findViewById(R.id.tvIdAerolinea);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
            btnEditar = itemView.findViewById(R.id.btnEditar);
        }
    }
}