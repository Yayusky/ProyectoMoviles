package com.example.proyectomoviles;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectomoviles.api.HuertoApiServicio;
import com.example.proyectomoviles.api.RetrofitCliente;
import com.example.proyectomoviles.modelos.Huerto;
import com.example.proyectomoviles.modelos.Planta;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaHuertosFragment extends Fragment {
    private RecyclerView recyclerHuertos;
    private TextView tvSinHuertas;
    private AdaptadorHuerto adaptadorHuerto;
    private final List<Huerto> listaHuertos = new ArrayList<>();
    private final List<Planta> listaPlantas = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_huertos, container, false);

        recyclerHuertos = view.findViewById(R.id.recyclerHuertos);
        tvSinHuertas = view.findViewById(R.id.tvSinHuertas);

        recyclerHuertos.setLayoutManager(new LinearLayoutManager(getContext()));
        adaptadorHuerto = new AdaptadorHuerto(listaHuertos, listaPlantas, huerto -> {
            // Al hacer click en una card, navega al detalle o realiza la acción que desees.
        });
        recyclerHuertos.setAdapter(adaptadorHuerto);

        cargarDatos();

        return view;
    }

    private void cargarDatos() {
        // 1. Obtiene usuarioId de preferencias
        SharedPreferences prefs = requireActivity().getSharedPreferences("usuarioPrefs", Context.MODE_PRIVATE);
        String usuarioId = prefs.getString("usuarioId", null);
        if (usuarioId == null) {
            mostrarVacio();
            return;
        }

        // 2. Carga plantas primero, luego huertos (para poder cruzar info)
        RetrofitCliente.getPlantaService().obtenerPlantas().enqueue(new Callback<List<Planta>>() {
            @Override
            public void onResponse(Call<List<Planta>> call, Response<List<Planta>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaPlantas.clear();
                    listaPlantas.addAll(response.body());
                    cargarHuertos(usuarioId);
                } else {
                    mostrarVacio();
                }
            }
            @Override
            public void onFailure(Call<List<Planta>> call, Throwable t) {
                mostrarVacio();
            }
        });
    }

    private void cargarHuertos(String usuarioId) {
        RetrofitCliente.obtener().create(HuertoApiServicio.class).obtenerHuertos(usuarioId).enqueue(new Callback<List<Huerto>>() {
            @Override
            public void onResponse(Call<List<Huerto>> call, Response<List<Huerto>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    listaHuertos.clear();
                    listaHuertos.addAll(response.body());
                    recyclerHuertos.setVisibility(View.VISIBLE);
                    tvSinHuertas.setVisibility(View.GONE);
                    adaptadorHuerto.notifyDataSetChanged();
                } else {
                    mostrarVacio();
                }
            }
            @Override
            public void onFailure(Call<List<Huerto>> call, Throwable t) {
                mostrarVacio();
            }
        });
    }

    private void mostrarVacio() {
        listaHuertos.clear();
        adaptadorHuerto.notifyDataSetChanged();
        recyclerHuertos.setVisibility(View.GONE);
        tvSinHuertas.setVisibility(View.VISIBLE);
    }
}
