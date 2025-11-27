package com.example.proyectomoviles;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.example.proyectomoviles.modelos.Plaga;
import java.util.List;

public class AdaptadorPlagas extends RecyclerView.Adapter<AdaptadorPlagas.PlagaViewHolder> {

    private List<Plaga> listaPlagas;
    private OnPlagaClickListener listener;


    public AdaptadorPlagas(List<Plaga> listaPlagas, OnPlagaClickListener listener) {
        this.listaPlagas = listaPlagas;
        this.listener = listener;
    }
    public interface OnPlagaClickListener {
        void onPlagaClick(Plaga plaga);
    }

    @NonNull
    @Override
    public PlagaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_plaga, parent, false);
        return new PlagaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlagaViewHolder holder, int position) {
        Plaga plaga = listaPlagas.get(position);
        holder.bind(plaga);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    listener.onPlagaClick(listaPlagas.get(pos));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaPlagas != null ? listaPlagas.size() : 0;
    }

    public void actualizarLista(List<Plaga> nuevasPlagas) {
        this.listaPlagas = nuevasPlagas;
        notifyDataSetChanged();
    }

    static class PlagaViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagenPlaga;
        private TextView textTituloPlaga, textNombreCientifico, textDescripcionPlaga, textRemediosDisponibles;

        public PlagaViewHolder(@NonNull View itemView) {
            super(itemView);
            imagenPlaga = itemView.findViewById(R.id.imagePlaga);
            textTituloPlaga = itemView.findViewById(R.id.textTituloPlaga);
            textNombreCientifico = itemView.findViewById(R.id.textNombreCientifico);
            textDescripcionPlaga = itemView.findViewById(R.id.textDescripcionPlaga);
            textRemediosDisponibles = itemView.findViewById(R.id.textRemediosDisponibles);
        }

        public void bind(Plaga plaga) {
            // CARGAR IMAGEN CON PICASSO - SUPER SIMPLE
            if (plaga.getImg() != null && !plaga.getImg().isEmpty()) {
                Picasso.get()
                        .load(plaga.getImg())
                        .fit()
                        .centerCrop()
                        .into(imagenPlaga);
            }

            // Textos
            textTituloPlaga.setText(plaga.getNombreP());
            textNombreCientifico.setText(plaga.getNombreCien());

            // Descripción corta
            String descripcion = plaga.getDefinicion();
            if (descripcion != null && descripcion.length() > 100) {
                descripcion = descripcion.substring(0, 100) + "...";
            }

            textDescripcionPlaga.setText(descripcion);
            int numRemedios = plaga.getRemedio() != null ? plaga.getRemedio().size() : 0;
            String textoRemedios = numRemedios + " remedio" + (numRemedios != 1 ? "s" : "") + " natural" + (numRemedios != 1 ? "es" : "") + " disponible" + (numRemedios != 1 ? "s" : "") + "para esta plaga.";
            textRemediosDisponibles.setText(textoRemedios);
        }


    }
}




