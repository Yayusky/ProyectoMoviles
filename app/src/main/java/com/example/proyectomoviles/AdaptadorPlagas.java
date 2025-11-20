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

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.onPlagaClick(plaga);
//            }
//        });


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
        private ImageView imagePlaga;
        private TextView textTituloPlaga, textNombreCientifico, textDescripcionPlaga, textRemediosDisponibles;

        public PlagaViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePlaga = itemView.findViewById(R.id.imagePlaga);
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
                        .into(imagePlaga);
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


            // Contador de remedios
            int numRemedios = plaga.getRemedio() != null ? plaga.getRemedio().size() : 0;
            String textoRemedios = "💡 " + numRemedios + " remedio" + (numRemedios != 1 ? "s" : "") + " natural" + (numRemedios != 1 ? "es" : "") + " disponible" + (numRemedios != 1 ? "s" : "");
            textRemediosDisponibles.setText(textoRemedios);
        }


    }
}





//package com.example.proyectomoviles;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
//import com.example.proyectomoviles.modelos.Plaga;
//import java.util.List;
//
//public class AdaptadorPlagas extends RecyclerView.Adapter<AdaptadorPlagas.PlagaViewHolder> {
//
//    private List<Plaga> listaPlagas;
//    private OnPlagaClickListener listener;
//
//    public interface OnPlagaClickListener {
//        void onPlagaClick(Plaga plaga);
//    }
//
//    public AdaptadorPlagas(List<Plaga> listaPlagas, OnPlagaClickListener listener) {
//        this.listaPlagas = listaPlagas;
//        this.listener = listener;
//    }
//
//    @NonNull
//    @Override
//    public PlagaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_plaga, parent, false);
//        return new PlagaViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull PlagaViewHolder holder, int position) {
//        Plaga plaga = listaPlagas.get(position);
//        holder.bind(plaga);
//
//        holder.itemView.setOnClickListener(v -> {
//            if (listener != null) {
//                listener.onPlagaClick(plaga);
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return listaPlagas != null ? listaPlagas.size() : 0;
//    }
//
//    public void actualizarLista(List<Plaga> nuevasPlagas) {
//        this.listaPlagas = nuevasPlagas;
//        notifyDataSetChanged();
//    }
//
//    static class PlagaViewHolder extends RecyclerView.ViewHolder {
//        private TextView textTituloPlaga, textNombreCientifico, textDescripcionPlaga, textRemediosDisponibles;
//
//        public PlagaViewHolder(@NonNull View itemView) {
//            super(itemView);
//            imagePlaga = itemView.findViewById(R.id.imagePlaga);
//            textTituloPlaga = itemView.findViewById(R.id.textTituloPlaga);
//            textNombreCientifico = itemView.findViewById(R.id.textNombreCientifico);
//            textDescripcionPlaga = itemView.findViewById(R.id.textDescripcionPlaga);
//            textRemediosDisponibles = itemView.findViewById(R.id.textRemediosDisponibles);
//        }
//
//        public void bind(Plaga plaga) {
//
//            // Cargar imagen con Glide
//            if (plaga.getImg() != null && !plaga.getImg().isEmpty()) {
//                Glide.with(itemView.getContext())
//                        .load(plaga.getImg())
//                        .transition(DrawableTransitionOptions.withCrossFade())
//                        .error(R.drawable.placeholder_plaga) // Imagen de fallback
//                        .into(imagePlaga);
//            } else {
//                imagePlaga.setImageResource(R.drawable.placeholder_plaga);
//            }
//
//            textTituloPlaga.setText(plaga.getNombreP());
//            textNombreCientifico.setText(plaga.getNombreCien());
//
//            textTituloPlaga.setText(plaga.getNombreP());
//            textNombreCientifico.setText(plaga.getNombreCien());
//
//            String descripcion = plaga.getDefinicion();
//            if (descripcion != null && descripcion.length() > 150) {
//                descripcion = descripcion.substring(0, 150) + "...";
//            }
//            textDescripcionPlaga.setText(descripcion);
//
//            int numRemedios = plaga.getRemedios() != null ? plaga.getRemedios().size() : 0;
//            textRemediosDisponibles.setText("Remedios disponibles: " + numRemedios);
//        }
//    }
//}
//
//




























//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import com.example.proyectomoviles.modelos.Plaga;
//
//import java.util.List;
//
//public class AdaptadorPlagas extends BaseAdapter {
//    private Context contexto;
//    private List<Plaga> lista;
//
//    private LayoutInflater inflater;
//
//    public AdaptadorPlagas(Context c, List<Plaga> l) {
//        this.contexto = c;
//        this.lista = l;
//        this.inflater = LayoutInflater.from(c);
//    }
//
//    @Override
//    public int getCount() {
//        return lista.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return lista.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        Vista vh;
//        if (convertView == null) {
//            convertView = inflater.inflate(R.layout.item_plaga, parent, false);
//            vh = new Vista();
//            vh.nombre = convertView.findViewById(R.id.nombrePlaga);
//            vh.nombreCientifico = convertView.findViewById(R.id.nombreCientificoPlaga);
//            vh.definicion = convertView.findViewById(R.id.definicionPlaga);
//            convertView.setTag(vh);
//        } else {
//            vh = (Vista) convertView.getTag();
//        }
//        Plaga plaga = lista.get(position);
//        vh.nombre.setText(plaga.getNombreP());
//        vh.nombreCientifico.setText(plaga.getNombreCien());
//
//        // Mostrar solo los primeros 100 caracteres de la definición
//        String definicionCorta = plaga.getDefinicion();
//        if (definicionCorta != null && definicionCorta.length() > 100) {
//            definicionCorta = definicionCorta.substring(0, 100) + "...";
//        }
//        vh.definicion.setText(definicionCorta);
//
//
//
//        return convertView;
//    }
//
//    public static class Vista {
//        TextView nombre;
//        TextView nombreCientifico;
//        TextView definicion;
//    }
//}


