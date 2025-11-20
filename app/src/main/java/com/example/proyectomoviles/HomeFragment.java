package com.example.proyectomoviles;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.cardHuerta).setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_huertaFragment);
        });

        view.findViewById(R.id.cardCalendario).setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_calendarioFragment);
        });

        view.findViewById(R.id.cardPlagas).setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_plagasFragment);
        });

        view.findViewById(R.id.cardTiposCultivo).setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_tiposCultivoFragment);
        });
    }
}