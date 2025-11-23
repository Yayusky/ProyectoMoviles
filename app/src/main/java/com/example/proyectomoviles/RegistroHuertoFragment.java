package com.example.proyectomoviles;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.proyectomoviles.api.HuertoApiServicio;
import com.example.proyectomoviles.api.RetrofitCliente;
import com.example.proyectomoviles.modelos.Huerto;
import com.example.proyectomoviles.modelos.Planta;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroHuertoFragment extends Fragment {
    private Planta planta;
    String fechaRegistro = java.time.LocalDateTime.now().toString();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registro_huerto, container, false);

        planta = (Planta) getArguments().getSerializable("planta");

        EditText nombreHuerto = view.findViewById(R.id.nombreHuerto);
        EditText cantidadPlantas = view.findViewById(R.id.cantidadPlantas);
        EditText tipoSuelo = view.findViewById(R.id.tipoSuelo);
        EditText tipoRiego = view.findViewById(R.id.tipoRiego);
        EditText frecuenciaRiego = view.findViewById(R.id.frecuenciaRiego);
        EditText notasHuerto = view.findViewById(R.id.notasHuerto);
        TextView sobresRecomendados = view.findViewById(R.id.sobresRecomendados);
        Button btnRegistrar = view.findViewById(R.id.btnRegistrarHuerto);

        // Rellenar los datos sugeridos de la planta seleccionada
        if (planta != null) {
            tipoSuelo.setText(planta.getSuelo());
            tipoRiego.setText(planta.getRiego());
            frecuenciaRiego.setText(String.valueOf(planta.getNumRiegoXSemana()));
        }

        // Actualizar recomendación de sobres de semillas
        cantidadPlantas.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    int cantidad = Integer.parseInt(s.toString());
                    int sobres = (int) Math.ceil((double) cantidad / 25);
                    sobresRecomendados.setText("Sobres de semillas sugeridos: " + sobres);
                } catch (NumberFormatException e) {
                    sobresRecomendados.setText("");
                }
            }
            @Override public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {}
            @Override public void onTextChanged(CharSequence s, int i, int i1, int i2) {}
        });

        btnRegistrar.setOnClickListener(v -> {
            // Validaciones básicas
            if (nombreHuerto.getText().toString().trim().isEmpty()
                    || cantidadPlantas.getText().toString().trim().isEmpty()) {
                Toast.makeText(getContext(), "Por favor llena los campos obligatorios.", Toast.LENGTH_SHORT).show();
                return;
            }
            // Recopila los datos para el nuevo huerto
            Huerto huerto = new Huerto();
            huerto.setNombre(nombreHuerto.getText().toString());
            huerto.setUsuarioId("id_del_usuario"); // pendiente de implementar autenticación
            huerto.setFechaRegistro(java.time.LocalDateTime.now().toString());
            huerto.setTipoSuelo(tipoSuelo.getText().toString());
            huerto.setIrrigacion(
                    new Huerto.Irrigacion(
                            tipoRiego.getText().toString(),
                            Integer.parseInt(frecuenciaRiego.getText().toString())
                    )
            );
            huerto.setNotas(notasHuerto.getText().toString());

            Huerto.PlantaSembrada sembrada = new Huerto.PlantaSembrada();
            sembrada.setPlantaId(planta.getId());
            sembrada.setNombrePlanta(planta.getNombre());
            sembrada.setCantidad(Integer.parseInt(cantidadPlantas.getText().toString()));
            sembrada.setFechaSiembra(java.time.LocalDateTime.now().toString());
            sembrada.setEtapaActual("Fase Inicial");
            sembrada.setNotas("");
            huerto.getPlantasSembradas().add(sembrada);

            // Aquí va la llamada al backend con Retrofit para guardar el huerto (puedes agregarla después)

            HuertoApiServicio api = RetrofitCliente.obtener().create(HuertoApiServicio.class);
            Call<Void> call = api.registrarHuerto(huerto);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getContext(), "Huerto registrado exitosamente", Toast.LENGTH_LONG).show();
                        // Aquí puedes navegar al resumen de huertos, limpiar campos, etc.
                    } else {
                        Toast.makeText(getContext(), "Error al registrar el huerto", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(getContext(), "Fallo de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

            Toast.makeText(getContext(), "Huerto registrado correctamente", Toast.LENGTH_SHORT).show();
            // Navegación o cierre
        });

        return view;
    }


}
