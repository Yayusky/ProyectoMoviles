package com.example.proyectomoviles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectomoviles.modelos.Huerto;
import com.example.proyectomoviles.modelos.Planta;
import com.google.android.material.imageview.ShapeableImageView;
import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;


public class AdaptadorHuerto extends RecyclerView.Adapter<AdaptadorHuerto.HuertoViewHolder> {
    private final List<Huerto> listaHuertos;
    private final List<Planta> listaPlantas;
    private final OnClickHuertoListener listener;

    public interface OnClickHuertoListener {
        void onHuertoClick(Huerto huerto);
    }

    public AdaptadorHuerto(List<Huerto> huertos, List<Planta> plantas, OnClickHuertoListener listener) {
        this.listaHuertos = huertos;
        this.listaPlantas = plantas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HuertoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_huerto, parent, false);
        return new HuertoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HuertoViewHolder holder, int position) {
        holder.bind(listaHuertos.get(position), listener, listaPlantas);
    }

    @Override
    public int getItemCount() {
        return listaHuertos.size();
    }

    static class HuertoViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView imgMiniPlanta;
        TextView tvNombreHuerto, tvNombrePlanta;
        TextView tvDescripcionPlanta;   // nuevo

        HuertoViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMiniPlanta = itemView.findViewById(R.id.imgMiniPlanta);
            tvNombreHuerto = itemView.findViewById(R.id.tvNombreHuerto);
            tvNombrePlanta = itemView.findViewById(R.id.tvNombrePlanta);
            tvDescripcionPlanta = itemView.findViewById(R.id.tvDescripcionPlanta);
        }

        void bind(Huerto huerto, OnClickHuertoListener listener, List<Planta> listaPlantas) {
            tvNombreHuerto.setText(huerto.getNombre());

            String nombrePlanta = "";
            String urlImagenPlanta = "";
            String descripcionPlanta = "";

            Huerto.PlantaSembrada primeraSembrada = null;
            if (huerto.getPlantasSembradas() != null && !huerto.getPlantasSembradas().isEmpty()) {
                primeraSembrada = huerto.getPlantasSembradas().get(0);
                nombrePlanta = primeraSembrada.getNombrePlanta();
                String plantaId = primeraSembrada.getPlantaId();

                // Buscar la planta en la lista por su id y sacar urlImagen y descripción
                for (Planta planta : listaPlantas) {
                    if (planta.getId().equals(plantaId)) {
                        urlImagenPlanta = planta.getUrlImagen();
                        descripcionPlanta = planta.getDescripcion();
                        break;
                    }
                }
            }

            tvNombrePlanta.setText(nombrePlanta);
            tvDescripcionPlanta.setText(
                    (descripcionPlanta != null && !descripcionPlanta.isEmpty())
                            ? descripcionPlanta
                            : "Sin descripción disponible"
            );

            if (urlImagenPlanta != null && !urlImagenPlanta.isEmpty()) {
                Picasso.get().load(urlImagenPlanta).into(imgMiniPlanta);
            } else {
                imgMiniPlanta.setImageResource(R.drawable.ic_launcher_background);
            }

            itemView.setOnClickListener(v -> {
                if (huerto.getPlantasSembradas() == null
                        || huerto.getPlantasSembradas().isEmpty()) {
                    Toast.makeText(itemView.getContext(),
                            "No hay plantas asociadas a este huerto",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Buscar la Planta correspondiente usando plantaId
                Huerto.PlantaSembrada ps = huerto.getPlantasSembradas().get(0);
                String plantaId = ps.getPlantaId();

                Planta plantaSeleccionada = null;
                for (Planta p : listaPlantas) {
                    if (p.getId().equals(plantaId)) {
                        plantaSeleccionada = p;
                        break;
                    }
                }

                if (plantaSeleccionada == null) {
                    Toast.makeText(itemView.getContext(),
                            "No se encontró la planta asociada",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                Bundle bundle = new Bundle();
                bundle.putSerializable("huerto", huerto);
                bundle.putSerializable("planta", plantaSeleccionada);

                NavController navController = Navigation.findNavController(itemView);
                navController.navigate(R.id.detalleHuertoFragment, bundle);
            });
        }
    }
}

