package com.example.proyectomoviles;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.proyectomoviles.modelos.Huerto;
import com.example.proyectomoviles.modelos.Planta;
import com.google.android.material.appbar.MaterialToolbar;
import com.squareup.picasso.Picasso;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

// Suponiendo que tienes estas clases
// import com.example.proyectomoviles.modelos.Huerto;
// import com.example.proyectomoviles.modelos.Planta;

public class HuertaFragment extends Fragment {

    private TextView tvNombrePlanta, tvNombreCientifico, tvFechaSiembra, tvProximoRiego;
    private ImageView imgPlanta;
    private LinearLayout layoutEtapas;
    private MaterialToolbar topAppBar;
    private Button btnRegar, btnEliminar;

    private Huerto huerto;
    private Planta planta;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_huerta, container, false);

        // Vincula UI
        tvNombrePlanta = view.findViewById(R.id.tvNombrePlanta);
        tvNombreCientifico = view.findViewById(R.id.tvNombreCientifico);
        imgPlanta = view.findViewById(R.id.imgPlanta);
        tvFechaSiembra = view.findViewById(R.id.tvFechaSiembra);
        tvProximoRiego = view.findViewById(R.id.tvProximoRiego);
        layoutEtapas = view.findViewById(R.id.layoutEtapas);
        topAppBar = view.findViewById(R.id.topAppBar);
        btnRegar = view.findViewById(R.id.btnRegar);
        btnEliminar = view.findViewById(R.id.btnEliminar);

        // Recibe huerto y planta por argumentos (Serializable)
        Bundle args = getArguments();
        if (args != null) {
            huerto = (Huerto) args.getSerializable("huerto");
            planta = (Planta) args.getSerializable("planta");
        }

        if (planta != null && huerto != null) {
            mostrarDatos(inflater);
        }


        topAppBar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());

        btnRegar.setOnClickListener(v -> {

        });

        btnEliminar.setOnClickListener(v -> {
        });

        return view;
    }

    private void mostrarDatos(LayoutInflater inflater) {
        tvNombrePlanta.setText(planta.getNombre());
        tvNombreCientifico.setText(planta.getNombreC());
        Picasso.get().load(planta.getUrlImagen()).into(imgPlanta);

        String fechaSiembraISO = "-";
        if (huerto.getPlantasSembradas() != null && !huerto.getPlantasSembradas().isEmpty()) {
            fechaSiembraISO = huerto.getPlantasSembradas().get(0).getFechaSiembra().substring(0,10);
        }
        tvFechaSiembra.setText("Sembrado el " + formateaFecha(fechaSiembraISO));


        String proximoRiego = calcularProximoRiego(fechaSiembraISO, Integer.parseInt(planta.getNumRiegoXSemana()));
        tvProximoRiego.setText("💧 Próximo riego: " + proximoRiego);

        layoutEtapas.removeAllViews();
        if (planta.getEtapas() != null && !planta.getEtapas().isEmpty()) {
            LayoutInflater inflaterEtapas = LayoutInflater.from(getContext());
            for (Planta.Etapa etapa : planta.getEtapas()) {
                View etapaView = inflaterEtapas.inflate(R.layout.item_etapa, layoutEtapas, false);
                ((TextView) etapaView.findViewById(R.id.tvNombreEtapa)).setText(etapa.getNombre());
                ((TextView) etapaView.findViewById(R.id.tvDuracion)).setText("Duración: " + etapa.getDiasDuracion());
                ((TextView) etapaView.findViewById(R.id.tvDescripcion)).setText("Descripción: " + etapa.getDescripcion());
                ((TextView) etapaView.findViewById(R.id.tvCuidados)).setText("Cuidados: " + etapa.getCuidados());

                layoutEtapas.addView(etapaView);
            }
        }
    }

    private String calcularProximoRiego(String fechaSiembraISO, int riegosPorSemana) {
        if ("-".equals(fechaSiembraISO) || riegosPorSemana < 1) {
            return "-";
        }
        try {
            int intervaloDias = Math.round(7f / riegosPorSemana);
            LocalDate fechaSiembra = LocalDate.parse(fechaSiembraISO);
            LocalDate hoy = LocalDate.now();
            long diasDesdeSiembra = ChronoUnit.DAYS.between(fechaSiembra, hoy);

            long riegosPasados = diasDesdeSiembra / intervaloDias;
            long diasHastaProximo = intervaloDias - (diasDesdeSiembra % intervaloDias);
            LocalDate proximaFecha = hoy.plusDays(diasHastaProximo);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return proximaFecha.format(formatter);
        } catch (Exception e) {
            return "-";
        }
    }

    private String formateaFecha(String isoDate) {
        try {
            LocalDate date = LocalDate.parse(isoDate);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return date.format(formatter);
        } catch (Exception e) {
            return isoDate;
        }
    }
}
