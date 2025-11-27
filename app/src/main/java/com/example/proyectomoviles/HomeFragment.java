package com.example.proyectomoviles;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.proyectomoviles.api.ConsejoApiServicio;
import com.example.proyectomoviles.api.RetrofitCliente;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private TextView tvConsejo;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnableConsejo;
    private static final long INTERVALO = 30_000L;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tvConsejo = view.findViewById(R.id.tvConsejo);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.cardHuerta).setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_listaHuertosFragment)
        );

        view.findViewById(R.id.cardCalendario).setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_calendarioFragment)
        );

        view.findViewById(R.id.cardPlagas).setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_plagasFragment)
        );

        view.findViewById(R.id.cardTiposCultivo).setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_tipoCultivosFragment)
        );

        runnableConsejo = () -> {
            cargarConsejoDesdeApi();
            handler.postDelayed(runnableConsejo, INTERVALO);
        };

        handler.post(runnableConsejo);
    }

    private void cargarConsejoDesdeApi() {
        ConsejoApiServicio api = RetrofitCliente.obtener().create(ConsejoApiServicio.class);
        Call<ConsejoApiServicio.ConsejoRespuesta> call = api.obtenerConsejoAleatorio();

        call.enqueue(new Callback<ConsejoApiServicio.ConsejoRespuesta>() {
            @Override
            public void onResponse(Call<ConsejoApiServicio.ConsejoRespuesta> call, Response<ConsejoApiServicio.ConsejoRespuesta> response) {
                if (response.isSuccessful() && response.body() != null) {
                    tvConsejo.setText(response.body().getConsejo());
                }
            }

            @Override
            public void onFailure(Call<ConsejoApiServicio.ConsejoRespuesta> call, Throwable t) {
                // Puedes dejar el último consejo o mostrar uno fijo
                // tvConsejo.setText("Riega por la mañana para evitar evaporación rápida.");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(runnableConsejo);
    }
}





//public class HomeFragment extends Fragment {
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_home, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        view.findViewById(R.id.cardHuerta).setOnClickListener(v -> {
//            Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_listaHuertosFragment);
//        });
//
//        view.findViewById(R.id.cardCalendario).setOnClickListener(v -> {
//            Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_calendarioFragment);
//        });
//
//        view.findViewById(R.id.cardPlagas).setOnClickListener(v -> {
//            Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_plagasFragment);
//        });
//
//        view.findViewById(R.id.cardTiposCultivo).setOnClickListener(v -> {
//            Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_tipoCultivosFragment);
//        });
//    }
//}