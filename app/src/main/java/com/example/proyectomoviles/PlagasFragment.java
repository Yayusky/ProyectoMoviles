package com.example.proyectomoviles;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.proyectomoviles.api.PlantaApiServicio;
import com.example.proyectomoviles.api.RetrofitCliente;
import com.example.proyectomoviles.modelos.Plaga;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlagasFragment extends Fragment {

    private RecyclerView recyclerViewPlagas;
    private ProgressBar progressBarPlagas;
    private TextView tvError;
    private AdaptadorPlagas adaptadorPlagas;
    private List<Plaga> listaPlagas = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plagas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inicializarVistas(view);
        configurarRecyclerView();
        cargarPlagas();
    }



    private void inicializarVistas(View view) {
        recyclerViewPlagas = view.findViewById(R.id.recyclerViewPlagas);
        progressBarPlagas = view.findViewById(R.id.progressBar);
        tvError = view.findViewById(R.id.textError);
    }

    private void configurarRecyclerView() {
        adaptadorPlagas = new AdaptadorPlagas(listaPlagas, new AdaptadorPlagas.OnPlagaClickListener() {
            @Override
            public void onPlagaClick(Plaga plaga) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("plaga", plaga);
                NavHostFragment.findNavController(PlagasFragment.this)
                        .navigate(R.id.action_plagasFragment_to_detallePlagaFragment, bundle);
            }
        });
        recyclerViewPlagas.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewPlagas.setAdapter(adaptadorPlagas);
    }


    private void cargarPlagas() {
        mostrarCargando(true);

        PlantaApiServicio api = RetrofitCliente.getPlantaService();
        api.obtenerTodasLasPlagas().enqueue(new Callback<List<Plaga>>() {
            @Override
            public void onResponse(Call<List<Plaga>> call, Response<List<Plaga>> response) {
                mostrarCargando(false);
                if (response.isSuccessful() && response.body() != null) {
                    listaPlagas.clear();
                    listaPlagas.addAll(response.body());
                    adaptadorPlagas.actualizarLista(listaPlagas);

                    if (listaPlagas.isEmpty()) {
                        mostrarError("No hay plagas disponibles");
                    } else {
                        mostrarLista();
                    }
                } else {
                    mostrarError("Error al cargar las plagas");
                }
            }

            @Override
            public void onFailure(Call<List<Plaga>> call, Throwable t) {
                mostrarCargando(false);
                mostrarError("Error de conexión: " + t.getMessage());
            }
        });
    }

    private void mostrarCargando(boolean mostrar) {
        progressBarPlagas.setVisibility(mostrar ? View.VISIBLE : View.GONE);
        if (mostrar) {
            recyclerViewPlagas.setVisibility(View.GONE);
            tvError.setVisibility(View.GONE);
        }
    }

    private void mostrarLista() {
        recyclerViewPlagas.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.GONE);
        progressBarPlagas.setVisibility(View.GONE);
    }

    private void mostrarError(String mensaje) {
        tvError.setText(mensaje);
        tvError.setVisibility(View.VISIBLE);
        recyclerViewPlagas.setVisibility(View.GONE);
        progressBarPlagas.setVisibility(View.GONE);
    }
}

