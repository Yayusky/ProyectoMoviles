package com.example.proyectomoviles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.proyectomoviles.modelos.Planta;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetallePlantaFragment extends Fragment {
    private Planta planta;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalle_planta, container, false);
        planta = (Planta) getArguments().getSerializable("planta");

        ImageView img = view.findViewById(R.id.imagenDetalle);
        TextView nombre = view.findViewById(R.id.nombreDetalle);
        TextView nombreC = view.findViewById(R.id.nombreCientificoDetalle);
        TextView descripcion = view.findViewById(R.id.descripcionDetalle);
        TextView tipo = view.findViewById(R.id.tipoPlantaDetalle);
        TextView clima = view.findViewById(R.id.climaDetalle);
        TextView suelo = view.findViewById(R.id.sueloDetalle);
        TextView tiempoVida = view.findViewById(R.id.tiempoVidaDetalle);
        TextView mesesSiembra = view.findViewById(R.id.mesesSiembraDetalle);
        TextView riego = view.findViewById(R.id.riegoDetalle);
        Button btnSembrar = view.findViewById(R.id.btnSembrar);

        Picasso.get().load(planta.getUrlImagen()).placeholder(R.drawable.ic_launcher_background).into(img);
        nombre.setText(planta.getNombre());
        nombreC.setText(planta.getNombreC());
        descripcion.setText(planta.getDescripcion());
        tipo.setText("Tipo de Planta: " + planta.getTipo());
        clima.setText("Clima: " + planta.getClima());
        suelo.setText("Tipo de Suelo: " + planta.getSuelo());
        tiempoVida.setText("Tiempo aprox. de vida: " +
                (planta.getFaseInicial() + planta.getFaseVegetativa() + planta.getFaseReproductiva()) + " días");
        mesesSiembra.setText("Meses ideales para sembrar: " + meses(planta.getMesesSiembra()));
        riego.setText("Riego: " + planta.getNumRiegoXSemana() + " veces por semana");

        btnSembrar.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putSerializable("planta", planta);
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_detallePlantaFragment_to_registroHuertoFragment, args);
        });


        return view;
    }

    private String meses(List<Integer> meses) {
        String[] nombresMes = {
                "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        };
        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < meses.size(); i++) {
            int numMes = meses.get(i);
            if (numMes >= 1 && numMes <= 12) {
                resultado.append(nombresMes[numMes - 1]);
                if (i < meses.size() - 1) {
                    resultado.append(", ");
                }
            }
        }
        return resultado.toString();
    }



}
