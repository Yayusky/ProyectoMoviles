package com.example.proyectomoviles;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.proyectomoviles.api.HuertoApiServicio;
import com.example.proyectomoviles.api.RetrofitCliente;
import com.example.proyectomoviles.modelos.Huerto;
import com.example.proyectomoviles.modelos.Planta;
import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroHuertoFragment extends Fragment {
    private Planta planta;
    String fechaRegistro = java.time.LocalDateTime.now().toString();
    @SerializedName("usuario_Id")
    String usuarioId;


//Validacion Datos
    private boolean validarHuerto(EditText nombreHuerto, EditText cantidadPlantas, Planta planta) {
        boolean valido = true;

        // Campo: Nombre
        String nombre = nombreHuerto.getText().toString().trim();
        if (nombre.isEmpty()) {
            nombreHuerto.setError("Campo obligatorio");
            valido = false;
        } else {
            nombreHuerto.setError(null);
        }

        // Campo: Cantidad de plantas
        String cantidadTxt = cantidadPlantas.getText().toString().trim();
        int cantidad = 0;
        try {
            cantidad = Integer.parseInt(cantidadTxt);
        } catch (NumberFormatException e) {
            cantidadPlantas.setError("Escribe un número válido");
            valido = false;
        }
        if (cantidadTxt.isEmpty() || cantidad <= 0) {
            cantidadPlantas.setError("Debes ingresar una cantidad válida");
            valido = false;
        } else {
            cantidadPlantas.setError(null);
        }

        // Validación de planta seleccionada (por seguridad)
        if (planta == null) {
            Toast.makeText(getContext(), "Debes seleccionar una planta", Toast.LENGTH_SHORT).show();
            valido = false;
        } else {
            if (planta.getId() == null || planta.getNombre() == null) {
                Toast.makeText(getContext(), "Error interno: planta inválida", Toast.LENGTH_SHORT).show();
                valido = false;
            }
        }

        // Si algo está mal, regresa false
        return valido;
    }

    //Termina validacion datos


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registro_huerto, container, false);

        SharedPreferences prefs = requireActivity().getSharedPreferences("usuarioPrefs", Context.MODE_PRIVATE);
        usuarioId = prefs.getString("usuarioId", null);

        Log.d("RegistroHuertoFragment", "usuarioId obtenido: " + usuarioId);


        planta = (Planta) getArguments().getSerializable("planta");

        EditText nombreHuerto = view.findViewById(R.id.nombreHuerto);
        EditText cantidadPlantas = view.findViewById(R.id.cantidadPlantas);
        TextView tipoSuelo = view.findViewById(R.id.TipoSuelo);
        //EditText tipoRiego = view.findViewById(R.id.tipoRiego);
        TextView frecuenciaRiego = view.findViewById(R.id.frecuenciaRiego);
        EditText notasHuerto = view.findViewById(R.id.notasHuerto);
        TextView sobresRecomendados = view.findViewById(R.id.sobresRecomendos);
        Button btnRegistrar = view.findViewById(R.id.btnRegistrarHuerto);



        // Rellenar los datos sugeridos de la planta seleccionada
        if (planta != null) {
            tipoSuelo.setText(planta.getSuelo());
            //tipoRiego.setText(planta.getRiego());
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

        //Boton Cancelar

        Button btnCancelar = view.findViewById(R.id.btnCancelarRegistro);

        btnCancelar.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.calendarioFragment); // Usa el ID real del fragmento de lista de plantas
        });


        //Boton Registrar
        btnRegistrar.setOnClickListener(v -> {
            // Validaciones básicas
            if (!validarHuerto(nombreHuerto, cantidadPlantas, planta)) {
                // Si hay errores, los mensajes ya se muestran en sus campos
                return;
            }
            // Recopila los datos para el nuevo huerto
            Huerto huerto = new Huerto();
            huerto.setNombre(nombreHuerto.getText().toString());
            huerto.setUsuarioId(usuarioId); // ← usa el id recuperado
            huerto.setFechaRegistro(java.time.LocalDateTime.now().toString());
            tipoSuelo.setText(planta.getSuelo());

            huerto.setNotas(notasHuerto.getText().toString());

            Huerto.PlantaSembrada sembrada = new Huerto.PlantaSembrada();
            sembrada.setPlantaId(planta.getId());
            sembrada.setNombrePlanta(planta.getNombre());
            sembrada.setCantidad(Integer.parseInt(cantidadPlantas.getText().toString()));
            sembrada.setFechaSiembra(java.time.LocalDateTime.now().toString());
            huerto.getPlantasSembradas().add(sembrada);

            // Aquí va la llamada al backend con Retrofit para guardar el huerto (puedes agregarla después)

            HuertoApiServicio api = RetrofitCliente.obtener().create(HuertoApiServicio.class);
            Call<Void> call = api.registrarHuerto(huerto);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getContext(), "Huerto registrado exitosamente", Toast.LENGTH_LONG).show();

                        // Navega al fragmento de inicio (HomeFragment) usando NavController
                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                        navController.navigate(R.id.homeFragment);

                        // Si tienes lógica para limpiar campos, agrégala antes de navegar
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
