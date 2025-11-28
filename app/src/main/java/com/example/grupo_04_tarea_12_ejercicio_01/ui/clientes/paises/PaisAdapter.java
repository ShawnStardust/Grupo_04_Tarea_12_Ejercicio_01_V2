package com.example.grupo_04_tarea_12_ejercicio_01.ui.clientes.paises;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.grupo_04_tarea_12_ejercicio_01.R;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Pais;
import java.util.ArrayList;
import java.util.List;

public class PaisAdapter extends RecyclerView.Adapter<PaisAdapter.PaisViewHolder> {

    private List<Pais> listaPaises = new ArrayList<>();
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onDeleteClick(Pais pais);
        void onEditClick(Pais pais);
    }

    public PaisAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setPaises(List<Pais> paises) {
        this.listaPaises = paises;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PaisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pais, parent, false);
        return new PaisViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaisViewHolder holder, int position) {
        Pais pais = listaPaises.get(position);

        holder.tvNombre.setText(pais.getNombre());
        holder.tvId.setText("ID: " + pais.getIdPais());

        holder.btnEliminar.setOnClickListener(v -> listener.onDeleteClick(pais));
        holder.btnEditar.setOnClickListener(v -> listener.onEditClick(pais)); // <--- Nuevo
    }

    @Override
    public int getItemCount() { return listaPaises.size(); }

    static class PaisViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvId; // Nuevos TextViews
        ImageButton btnEliminar, btnEditar;

        public PaisViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombrePais);
            tvId = itemView.findViewById(R.id.tvIdPais);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
            btnEditar = itemView.findViewById(R.id.btnEditar);
        }
    }
}