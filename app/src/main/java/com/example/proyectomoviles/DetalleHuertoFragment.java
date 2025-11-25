package com.example.proyectomoviles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectomoviles.modelos.Huerto;
import com.example.proyectomoviles.modelos.Planta;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.squareup.picasso.Picasso;
import java.time.LocalDate;
import java.util.List;

public class DetalleHuertoFragment extends Fragment {

    private TextView tvNombreHuerto, tvNombrePlanta, tvNombreCientifico,
            tvFechaSiembra, tvProximoRiego, tvNombreFase, tvDescripcionFase, tvCuidadosFase;
    private ShapeableImageView imgPlanta;
    private MaterialButton btnRegar, btnEliminar;
    private ImageButton btnAnterior, btnSiguiente;

    private Huerto huerto;
    private Planta planta;
    // Si deseas navegación entre huertos puedes recibir una lista de Huerto y cargar el actual

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalle_huerto, container, false);

        // Bind UI
        tvNombreHuerto = view.findViewById(R.id.tvNombreHuerto);
        tvNombrePlanta = view.findViewById(R.id.tvNombrePlanta);
        tvNombreCientifico = view.findViewById(R.id.tvNombreCientifico);
        imgPlanta = view.findViewById(R.id.imgPlanta);
        tvFechaSiembra = view.findViewById(R.id.tvFechaSiembra);
        tvProximoRiego = view.findViewById(R.id.tvProximoRiego);
        tvNombreFase = view.findViewById(R.id.tvNombreFase);
        tvDescripcionFase = view.findViewById(R.id.tvDescripcionFase);
        tvCuidadosFase = view.findViewById(R.id.tvCuidadosFase);
        btnRegar = view.findViewById(R.id.btnRegar);
        btnEliminar = view.findViewById(R.id.btnEliminar);
        btnAnterior = view.findViewById(R.id.btnAnterior);
        btnSiguiente = view.findViewById(R.id.btnSiguiente);

        // Recibe los argumentos
        Bundle args = getArguments();
        if (args != null) {
            huerto = (Huerto) args.getSerializable("huerto");
            planta = (Planta) args.getSerializable("planta");
        }

        llenarDatos();

        btnRegar.setOnClickListener(v -> {
            Toast.makeText(getContext(), "¡Riego registrado!", Toast.LENGTH_SHORT).show();
        });
        btnEliminar.setOnClickListener(v -> {
            // Implementa lógica para eliminar cultivo
            Toast.makeText(getContext(), "Eliminando cultivo...", Toast.LENGTH_SHORT).show();
        });

        btnAnterior.setOnClickListener(v -> {
            // Implementa navegación a huerto anterior si tienes la lista
        });
        btnSiguiente.setOnClickListener(v -> {
            // Implementa navegación a huerto siguiente
        });

        return view;
    }

    private void llenarDatos() {
        if (huerto == null || planta == null) return;

        tvNombreHuerto.setText("“" + huerto.getNombre() + "”");
        tvNombrePlanta.setText("“" + planta.getNombre() + "”");
        tvNombreCientifico.setText("“" + planta.getNombreC() + "”");

        Picasso.get().load(planta.getUrlImagen()).into(imgPlanta);

        // Fecha de siembra, extrae solo la fecha (yyyy-MM-dd)
        String fechaSiembraRaw = huerto.getPlantasSembradas().get(0).getFechaSiembra();
        String fechaSiembra = fechaSiembraRaw.substring(0, 10).replace("-", "/");
        tvFechaSiembra.setText("Sembrado el " + fechaSiembra);

        // Próximo riego y fase actual
        int numRiego = Integer.parseInt(planta.getNumRiegoXSemana());
        List<Planta.Etapa> etapas = planta.getEtapas();

        String proximoRiego = HuertoUtils.calcularProximoRiego(fechaSiembraRaw, numRiego);
        tvProximoRiego.setText("Próximo riego: " + proximoRiego);

        Planta.Etapa faseActual = HuertoUtils.calcularFaseActual(fechaSiembraRaw, etapas);

        if (faseActual != null) {
            tvNombreFase.setText("“" + faseActual.getNombre() + "”");
            tvDescripcionFase.setText(faseActual.getDescripcion());
            tvCuidadosFase.setText(faseActual.getCuidados());
        }
    }
}
