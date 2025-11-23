package com.example.proyectomoviles;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectomoviles.api.PlantaApiServicio;
import com.example.proyectomoviles.api.RetrofitCliente;
import com.example.proyectomoviles.modelos.Planta;

import java.util.Calendar;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import java.util.Calendar;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarioFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdaptadorPlantas adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendario, container, false);

        recyclerView = view.findViewById(R.id.recyclerPlantas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdaptadorPlantas();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((planta, itemView) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("planta", planta);

            NavController navController = Navigation.findNavController(itemView);
            navController.navigate(R.id.action_calendarioFragment_to_detallePlantaFragment, bundle);
        });

        obtenerPlantasDelMes();

        return view;
    }

    private void obtenerPlantasDelMes() {
        Calendar calendar = Calendar.getInstance();
        int mesActual = calendar.get(Calendar.MONTH) + 1;

        PlantaApiServicio api = RetrofitCliente.getPlantaService();
        Call<List<Planta>> call = api.getPlantasPorMes(mesActual);
        call.enqueue(new Callback<List<Planta>>() {
            @Override
            public void onResponse(Call<List<Planta>> call, Response<List<Planta>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter.setPlantas(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<Planta>> call, Throwable t) {
                // Manejo de error
            }
        });
    }
}

