package com.example.proyectomoviles;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectomoviles.api.HuertoApiServicio;
import com.example.proyectomoviles.api.RetrofitCliente;
import com.example.proyectomoviles.modelos.Huerto;
import com.example.proyectomoviles.modelos.Planta;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.squareup.picasso.Picasso;
import java.time.LocalDate;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleHuertoFragment extends Fragment {

    private TextView tvNombreHuerto, tvNombrePlanta, tvNombreCientifico,
            tvFechaSiembra, tvProximoRiego, tvNombreFase, tvDescripcionFase, tvCuidadosFase;
    private ShapeableImageView imgPlanta;
    private MaterialButton btnRegar, btnEliminar;
    private ImageButton btnAnterior, btnSiguiente;

    private Huerto huerto;
    private Planta planta;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalle_huerto, container, false);

        tvNombreHuerto = view.findViewById(R.id.tvNombreHuerto);
        tvNombrePlanta = view.findViewById(R.id.tvNombrePlanta);
        tvNombreCientifico = view.findViewById(R.id.tvNombreCientifico);
        imgPlanta = view.findViewById(R.id.imgPlanta);
        tvFechaSiembra = view.findViewById(R.id.tvFechaSiembra);
        tvProximoRiego = view.findViewById(R.id.tvProximoRiego);
        tvNombreFase = view.findViewById(R.id.tvNombreFase);
        tvDescripcionFase = view.findViewById(R.id.tvDescripcionFase);
        tvCuidadosFase = view.findViewById(R.id.tvCuidadosFase);
        //btnRegar = view.findViewById(R.id.btnRegar);
        btnEliminar = view.findViewById(R.id.btnEliminar);
        //btnAnterior = view.findViewById(R.id.btnAnterior);
        //btnSiguiente = view.findViewById(R.id.btnSiguiente);

        Bundle args = getArguments();
        if (args != null) {
            huerto = (Huerto) args.getSerializable("huerto");
            planta = (Planta) args.getSerializable("planta");
        }

        llenarDatos();
        Log.d("DetalleHuerto", "ID del huerto = " + huerto.getHuertoId());

        btnEliminar.setOnClickListener(v -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Eliminar cultivo")
                    .setMessage("¿Estás seguro de eliminar este huerto?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        eliminarHuertoEnServidor(huerto);
                    })
                    .setNegativeButton("No", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .show();
        });

        return view;
    }

    private void eliminarHuertoEnServidor(Huerto huerto) {
        String huertoId = huerto.getHuertoId();
        Log.d("DetalleHuerto", "huertoId = " + huertoId);

        HuertoApiServicio api = RetrofitCliente.obtener().create(HuertoApiServicio.class);
        Call<Void> call = api.eliminarHuerto(huertoId);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(),
                            "Huerto eliminado correctamente", Toast.LENGTH_SHORT).show();

                    NavController navController =
                            Navigation.findNavController(requireView());
                    navController.popBackStack();
                } else {
                    Toast.makeText(getContext(),
                            "No se pudo eliminar el huerto", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(),
                        "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void llenarDatos() {
        if (huerto == null || planta == null) return;

        tvNombreHuerto.setText("“" + huerto.getNombre() + "”");
        tvNombrePlanta.setText("“" + planta.getNombre() + "”");
        tvNombreCientifico.setText("“" + planta.getNombreC() + "”");

        Picasso.get().load(planta.getUrlImagen()).into(imgPlanta);

        String fechaSiembraRaw = huerto.getPlantasSembradas().get(0).getFechaSiembra();
        String fechaSiembra = fechaSiembraRaw.substring(0, 10).replace("-", "/");
        tvFechaSiembra.setText("Sembrado el " + fechaSiembra);
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
