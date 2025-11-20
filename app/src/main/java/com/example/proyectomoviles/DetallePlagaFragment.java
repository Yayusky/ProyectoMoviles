package com.example.proyectomoviles;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.proyectomoviles.modelos.Plaga;
import com.example.proyectomoviles.modelos.RemedioPlaga;
import com.squareup.picasso.Picasso;

public class DetallePlagaFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalle_plaga, container, false);

        Plaga plaga = (Plaga) getArguments().getSerializable("plaga");

        ImageView imagePlaga = view.findViewById(R.id.imagePlaga);
        TextView tituloPlaga = view.findViewById(R.id.textTituloPlaga);
        TextView nombreCientifico = view.findViewById(R.id.textNombreCientifico);
        TextView descripcionCorta = view.findViewById(R.id.textDescripcionCorta);
        TextView descripcionLarga = view.findViewById(R.id.textDescripcionLarga);
        LinearLayout layoutRemedios = view.findViewById(R.id.layoutRemedios);

        // Imagen principal
        Picasso.get().load(plaga.getImg()).into(imagePlaga);

        // Titulos y descripciones
        tituloPlaga.setText(plaga.getNombreP());
        nombreCientifico.setText(plaga.getNombreCien());
        descripcionCorta.setText(plaga.getDefinicion());
        descripcionLarga.setText(""); // Completa si tienes una descripción larga adicional

        // Remedios
        if(plaga.getRemedio() != null && !plaga.getRemedio().isEmpty()){
            for(RemedioPlaga rp : plaga.getRemedio()){
                View remedioView = inflater.inflate(R.layout.item_remedio, layoutRemedios, false);
                ImageView imgRemedio = remedioView.findViewById(R.id.imgRemedio);
                TextView nombreRemedio = remedioView.findViewById(R.id.nombreRemedio);
                TextView descripcionRemedio = remedioView.findViewById(R.id.descripcionRemedio);
                TextView ingredientesRemedio = remedioView.findViewById(R.id.ingredientesRemedio);
                TextView aplicacionRemedio = remedioView.findViewById(R.id.aplicacionRemedio);
                TextView preparativo = remedioView.findViewById(R.id.preparativo);


                Picasso.get().load(rp.getImgRemedio()).into(imgRemedio);
                nombreRemedio.setText(rp.getNombre());
                descripcionRemedio.setText(rp.getDescripcion());
                ingredientesRemedio.setText("Ingredientes: " + rp.getIngredientes());
                preparativo.setText("Preparativo: " + rp.getPreparativo());
                aplicacionRemedio.setText("Aplicación: " + rp.getAplicacion());

                layoutRemedios.addView(remedioView);
            }
        }

        return view;
    }
}

