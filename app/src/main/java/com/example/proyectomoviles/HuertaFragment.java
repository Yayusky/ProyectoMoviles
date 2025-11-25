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

        // Navegación atrás
        topAppBar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());

        // Opcional: implementa botones regar y eliminar
        btnRegar.setOnClickListener(v -> {
            // Lógica para registrar riego (ejemplo: mostrar toast)
            // Toast.makeText(getContext(), "¡Riego registrado!", Toast.LENGTH_SHORT).show();
        });

        btnEliminar.setOnClickListener(v -> {
            // Lógica para eliminar cultivo (ejemplo: pedir confirmación y llamar API)
        });

        return view;
    }

    // Rellena toda la UI con los datos del huerto y planta
    private void mostrarDatos(LayoutInflater inflater) {
        tvNombrePlanta.setText(planta.getNombre());
        tvNombreCientifico.setText(planta.getNombreC());
        Picasso.get().load(planta.getUrlImagen()).into(imgPlanta);

        // Fecha de siembra: tomo la fecha de la primera planta sembrada
        String fechaSiembraISO = "-";
        if (huerto.getPlantasSembradas() != null && !huerto.getPlantasSembradas().isEmpty()) {
            fechaSiembraISO = huerto.getPlantasSembradas().get(0).getFechaSiembra().substring(0,10);
        }
        tvFechaSiembra.setText("Sembrado el " + formateaFecha(fechaSiembraISO));

        // Próximo riego
        String proximoRiego = calcularProximoRiego(fechaSiembraISO, Integer.parseInt(planta.getNumRiegoXSemana()));
        tvProximoRiego.setText("💧 Próximo riego: " + proximoRiego);

        // Etapas de la planta
        layoutEtapas.removeAllViews();
        if (planta.getEtapas() != null && !planta.getEtapas().isEmpty()) {
            LayoutInflater inflaterEtapas = LayoutInflater.from(getContext());
            for (Planta.Etapa etapa : planta.getEtapas()) {
                View etapaView = inflaterEtapas.inflate(R.layout.item_etapa, layoutEtapas, false);

                ((TextView) etapaView.findViewById(R.id.tvNombreEtapa)).setText(etapa.getNombre());
                ((TextView) etapaView.findViewById(R.id.tvDuracion)).setText("Duración: " + etapa.getDiasDuracion());
                ((TextView) etapaView.findViewById(R.id.tvDescripcion)).setText("Descripción: " + etapa.getDescripcion());
                ((TextView) etapaView.findViewById(R.id.tvCuidados)).setText("Cuidados: " + etapa.getCuidados());

                // Si quieres, puedes personalizar el icono cima de cada fase usando setCompoundDrawablesWithIntrinsicBounds o el atributo android:drawableStart en el XML

                layoutEtapas.addView(etapaView);
            }
        }
    }

    // Calcula la próxima fecha de riego según la frecuencia semanal
    private String calcularProximoRiego(String fechaSiembraISO, int riegosPorSemana) {
        if ("-".equals(fechaSiembraISO) || riegosPorSemana < 1) {
            return "-";
        }
        try {
            int intervaloDias = Math.round(7f / riegosPorSemana);
            LocalDate fechaSiembra = LocalDate.parse(fechaSiembraISO);
            LocalDate hoy = LocalDate.now();
            long diasDesdeSiembra = ChronoUnit.DAYS.between(fechaSiembra, hoy);

            // ¿Cuántos riegos han pasado desde la siembra?
            long riegosPasados = diasDesdeSiembra / intervaloDias;
            long diasHastaProximo = intervaloDias - (diasDesdeSiembra % intervaloDias);
            LocalDate proximaFecha = hoy.plusDays(diasHastaProximo);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return proximaFecha.format(formatter);
        } catch (Exception e) {
            return "-";
        }
    }

    // Convierte "2025-09-20" → "20/09/2025" (más amigable)
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




//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Button;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
//import com.example.proyectomoviles.modelos.Huerto;
//import com.example.proyectomoviles.modelos.Planta;
//import com.google.android.material.appbar.MaterialToolbar;
//import com.squareup.picasso.Picasso;
//import java.io.Serializable;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.time.temporal.ChronoUnit;
//
//// Suponiendo que tienes estas clases
//// import com.example.proyectomoviles.modelos.Huerto;
//// import com.example.proyectomoviles.modelos.Planta;
//
//public class HuertaFragment extends Fragment {
//
//    private TextView tvNombrePlanta, tvNombreCientifico, tvFechaSiembra, tvProximoRiego;
//    private ImageView imgPlanta;
//    private LinearLayout layoutEtapas;
//    private MaterialToolbar topAppBar;
//    private Button btnRegar, btnEliminar;
//
//    private Huerto huerto;
//    private Planta planta;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_huerta, container, false);
//
//        // Vincula UI
//        tvNombrePlanta = view.findViewById(R.id.tvNombrePlanta);
//        tvNombreCientifico = view.findViewById(R.id.tvNombreCientifico);
//        imgPlanta = view.findViewById(R.id.imgPlanta);
//        tvFechaSiembra = view.findViewById(R.id.tvFechaSiembra);
//        tvProximoRiego = view.findViewById(R.id.tvProximoRiego);
//        layoutEtapas = view.findViewById(R.id.layoutEtapas);
//        topAppBar = view.findViewById(R.id.topAppBar);
//        btnRegar = view.findViewById(R.id.btnRegar);
//        btnEliminar = view.findViewById(R.id.btnEliminar);
//
//        // Recibe huerto y planta por argumentos (Serializable)
//        Bundle args = getArguments();
//        if (args != null) {
//            huerto = (Huerto) args.getSerializable("huerto");
//            planta = (Planta) args.getSerializable("planta");
//        }
//
//        if (planta != null && huerto != null) {
//            mostrarDatos(inflater);
//        }
//
//        // Navegación atrás
//        topAppBar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());
//
//        // Opcional: implementa botones regar y eliminar
//        btnRegar.setOnClickListener(v -> {
//            // Lógica para registrar riego (ejemplo: mostrar toast)
//            // Toast.makeText(getContext(), "¡Riego registrado!", Toast.LENGTH_SHORT).show();
//        });
//
//        btnEliminar.setOnClickListener(v -> {
//            // Lógica para eliminar cultivo (ejemplo: pedir confirmación y llamar API)
//        });
//
//        return view;
//    }
//
//    // Rellena toda la UI con los datos del huerto y planta
//    private void mostrarDatos(LayoutInflater inflater) {
//        tvNombrePlanta.setText(planta.getNombre());
//        tvNombreCientifico.setText(planta.getNombreC());
//        Picasso.get().load(planta.getUrlImagen()).into(imgPlanta);
//
//        // Fecha de siembra: tomo la fecha de la primera planta sembrada
//        String fechaSiembraISO = "-";
//        if (huerto.getPlantasSembradas() != null && !huerto.getPlantasSembradas().isEmpty()) {
//            fechaSiembraISO = huerto.getPlantasSembradas().get(0).getFechaSiembra().substring(0,10);
//        }
//        tvFechaSiembra.setText("Sembrado el " + formateaFecha(fechaSiembraISO));
//
//        // Próximo riego
//        String proximoRiego = calcularProximoRiego(fechaSiembraISO, Integer.parseInt(planta.getNumRiegoXSemana()));
//        tvProximoRiego.setText("💧 Próximo riego: " + proximoRiego);
//
//        // Etapas de la planta
//        layoutEtapas.removeAllViews();
//        if (planta.getEtapas() != null) {
//            for (Planta.Etapa etapa : planta.getEtapas()) {
//                View etapaView = inflater.inflate(R.layout.item_etapa, layoutEtapas, false);
//                ((TextView) etapaView.findViewById(R.id.tvNombreEtapa)).setText(etapa.getNombre());
//                ((TextView) etapaView.findViewById(R.id.tvDuracion)).setText("Duración: " + etapa.getDiasDuracion());
//                ((TextView) etapaView.findViewById(R.id.tvDescripcion)).setText("Descripción: " + etapa.getDescripcion());
//                ((TextView) etapaView.findViewById(R.id.tvCuidados)).setText("Cuidados: " + etapa.getCuidados());
//                layoutEtapas.addView(etapaView);
//            }
//        }
//    }
//
//    // Calcula la próxima fecha de riego según la frecuencia semanal
//    private String calcularProximoRiego(String fechaSiembraISO, int riegosPorSemana) {
//        if ("-".equals(fechaSiembraISO) || riegosPorSemana < 1) {
//            return "-";
//        }
//        try {
//            int intervaloDias = Math.round(7f / riegosPorSemana);
//            LocalDate fechaSiembra = LocalDate.parse(fechaSiembraISO);
//            LocalDate hoy = LocalDate.now();
//            long diasDesdeSiembra = ChronoUnit.DAYS.between(fechaSiembra, hoy);
//
//            // ¿Cuántos riegos han pasado desde la siembra?
//            long riegosPasados = diasDesdeSiembra / intervaloDias;
//            long diasHastaProximo = intervaloDias - (diasDesdeSiembra % intervaloDias);
//            LocalDate proximaFecha = hoy.plusDays(diasHastaProximo);
//
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//            return proximaFecha.format(formatter);
//        } catch (Exception e) {
//            return "-";
//        }
//    }
//
//    // Convierte "2025-09-20" → "20/09/2025" (más amigable)
//    private String formateaFecha(String isoDate) {
//        try {
//            LocalDate date = LocalDate.parse(isoDate);
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//            return date.format(formatter);
//        } catch (Exception e) {
//            return isoDate;
//        }
//    }
//}














//
//
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.proyectomoviles.api.RetrofitCliente;
//import com.example.proyectomoviles.api.UsuarioApiServicio;
//import com.example.proyectomoviles.modelos.Huerto;
//import com.example.proyectomoviles.modelos.Planta;
//import com.example.proyectomoviles.modelos.PlantaHuertoItem;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class HuertaFragment extends Fragment {
//    private RecyclerView recyclerHuertos;
//    private AdaptadorPlantaHuerto adaptador;
//    private List<PlantaHuertoItem> items = new ArrayList<>();
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_huerta, container, false);
//        recyclerHuertos = view.findViewById(R.id.recyclerHuertos);
//        recyclerHuertos.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        adaptador = new AdaptadorPlantaHuerto(items, getContext());
//        recyclerHuertos.setAdapter(adaptador);
//
//        cargarHuertosDelUsuario();
//
//        return view;
//    }
//
//    private void cargarHuertosDelUsuario() {
//        SharedPreferences prefs = requireActivity().getSharedPreferences("usuarioPrefs", Context.MODE_PRIVATE);
//        String usuarioId = prefs.getString("usuarioId", null);
//
//        UsuarioApiServicio api = RetrofitCliente.getUsuarioService();
//        Call<List<Huerto>> call = api.getHuertosPorUsuario(usuarioId);
//
//        call.enqueue(new Callback<List<Huerto>>() {
//            @Override
//            public void onResponse(Call<List<Huerto>> call, Response<List<Huerto>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    items.clear();
//                    for (Huerto huerto : response.body()) {
//                        for (Huerto.PlantaSembrada ps : huerto.getPlantasSembradas()) {
//                            Planta planta = obtenerPlantaDesdeBase(ps.getPlantaId());
//                            if (planta != null) {
//                                PlantaHuertoItem item = new PlantaHuertoItem();
//                                item.setNombreHuerto(huerto.getNombre());
//                                item.setNombrePlanta(planta.getNombre());
//                                item.setNombreCientifico(planta.getNombreC());
//                                item.setImagenUrl(planta.getUrlImagen());
//                                item.setRiegoXSemana(planta.getNumRiegoXSemana());
//                                item.setFechaSiembra(ps.getFechaSiembra());
//                                item.setInfoCosecha("Cosecha en " + planta.() + " meses");
//                                item.setRecomendaciones(planta.getRecomendaciones());
//                                items.add(item);
//                            }
//                        }
//                    }
//                    adaptador.notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Huerto>> call, Throwable t) {
//                Toast.makeText(getContext(), "Error al cargar huertos: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private Planta obtenerPlantaDesdeBase(String plantaId) {
//        // Implementa aquí tu consulta local o a la API para traer todos los datos de la planta.
//        return null;
//    }
//}