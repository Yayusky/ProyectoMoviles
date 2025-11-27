package com.example.proyectomoviles;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.proyectomoviles.modelos.Planta;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class AdaptadorPlantas extends RecyclerView.Adapter<AdaptadorPlantas.PlantaViewHolder> {
    private List<Planta> plantas = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Planta planta, View view);
    }

    public void setPlantas(List<Planta> plantas) {
        this.plantas = plantas != null ? plantas : new ArrayList<>();
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public PlantaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_planta, parent, false);
        return new PlantaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlantaViewHolder holder, int position) {
        Planta planta = plantas.get(position);
        holder.nombre.setText(planta.getNombre());
        holder.descripcion.setText(planta.getDescripcion());
        Picasso.get()
                .load(planta.getUrlImagen())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(holder.imagen);
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(planta, v);
        });
    }

    @Override
    public int getItemCount() {
        return plantas.size();
    }

    static class PlantaViewHolder extends RecyclerView.ViewHolder {
        ImageView imagen;
        TextView nombre, descripcion;
        PlantaViewHolder(View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.imagenPlanta);
            nombre = itemView.findViewById(R.id.nombrePlanta);
            descripcion = itemView.findViewById(R.id.descPlanta);
        }
    }
}







