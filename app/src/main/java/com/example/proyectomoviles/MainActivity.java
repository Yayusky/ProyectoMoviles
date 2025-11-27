package com.example.proyectomoviles;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        LinearLayout bottomNav = findViewById(R.id.bottom_navigation);

        FloatingActionButton btnHome = findViewById(R.id.btn_home);
        FloatingActionButton btnEditarPerfil = findViewById(R.id.btn_editar_perfil);
        FloatingActionButton btnCultivos = findViewById(R.id.btn_cultivos);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            int id = destination.getId();

            if (id == R.id.loginFragment) {
                bottomNav.setVisibility(View.GONE);
            } else {
                bottomNav.setVisibility(View.VISIBLE);
            }
        });

        btnHome.setOnClickListener(v -> {
            navController.popBackStack(R.id.homeFragment, false);
            navController.navigate(R.id.homeFragment);
        });

        btnEditarPerfil.setOnClickListener(v -> {
            navController.popBackStack(R.id.editarPerfil, false);
            navController.navigate(R.id.editarPerfil);
        });

        btnCultivos.setOnClickListener(v -> {
            cerrarAplicacion();
        });

    }
    private void cerrarAplicacion() {
        finishAffinity();
    }
}






