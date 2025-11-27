package com.example.proyectomoviles;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectomoviles.api.EstadisticasApiServicio;
import com.example.proyectomoviles.api.RetrofitCliente;
import com.example.proyectomoviles.modelos.TipoCultivoItem;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TipoCultivosFragment extends Fragment {

    private PieChart pieChart;
    private TextView tvResumen;

    private EstadisticasApiServicio apiServicio;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tipo_cultivos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pieChart = view.findViewById(R.id.pieChartTiposCultivo);
        tvResumen = view.findViewById(R.id.tvResumen);

        configurarPieChart();

        apiServicio = RetrofitCliente.getEstadisticasService();

        SharedPreferences prefs = requireContext()
                .getSharedPreferences("usuarioPrefs", Context.MODE_PRIVATE);
        String usuarioId = prefs.getString("usuarioId", null);

        if (usuarioId != null) {
            cargarDatos(usuarioId);
        } else {
            Toast.makeText(getContext(),
                    "No se encontró usuario logueado",
                    Toast.LENGTH_SHORT).show();
            pieChart.setNoDataText("Inicia sesión para ver tus cultivos");
        }
    }

    private void configurarPieChart() {
        pieChart.getDescription().setEnabled(false);
        pieChart.setUsePercentValues(true);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(40f);
        pieChart.setTransparentCircleRadius(45f);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setEntryLabelTextSize(12f);
        pieChart.getLegend().setEnabled(true);
    }

    private void cargarDatos(String usuarioId) {
        Call<TipoCultivoItem.RespuestaTiposCultivo> call = apiServicio.getTiposCultivo(usuarioId);

        call.enqueue(new Callback<TipoCultivoItem.RespuestaTiposCultivo>() {
            @Override
            public void onResponse(Call<TipoCultivoItem.RespuestaTiposCultivo> call,
                                   Response<TipoCultivoItem.RespuestaTiposCultivo> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mostrarEnGrafica(response.body());
                } else {
                    Toast.makeText(getContext(),
                            "No se pudieron obtener estadísticas",
                            Toast.LENGTH_SHORT).show();
                    pieChart.setNoDataText("Sin datos de cultivos");
                }
            }

            @Override
            public void onFailure(Call<TipoCultivoItem.RespuestaTiposCultivo> call, Throwable t) {
                Toast.makeText(getContext(),
                        "Error de conexión: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
                pieChart.setNoDataText("Error al conectar con el servidor");
            }
        });
    }

    private void mostrarEnGrafica(TipoCultivoItem.RespuestaTiposCultivo data) {
        List<PieEntry> entries = new ArrayList<>();

        if (data.getTipos() != null) {
            for (TipoCultivoItem item : data.getTipos()) {
                if (item != null && item.getCantidad() > 0) {
                    entries.add(new PieEntry(item.getCantidad(), item.getTipo()));
                }
            }
        }

        if (entries.isEmpty()) {
            pieChart.clear();
            pieChart.setNoDataText("Aún no tienes cultivos registrados");
            tvResumen.setText("Total de plantas: 0");
            return;
        }

        PieDataSet dataSet = new PieDataSet(entries, "Tipos de cultivo");
        dataSet.setSliceSpace(2f);
        dataSet.setValueTextSize(12f);

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#4CAF50"));
        colors.add(Color.parseColor("#FFC107"));
        colors.add(Color.parseColor("#F44336"));
        colors.add(Color.parseColor("#2196F3"));
        colors.add(Color.parseColor("#9C27B0"));
        dataSet.setColors(colors);

        PieData pieData = new PieData(dataSet);
        pieData.setValueTextColor(Color.WHITE);
        pieData.setValueTextSize(12f);

        pieChart.setData(pieData);
        pieChart.invalidate();

        tvResumen.setText("Total de plantas: " + data.getTotal_plantas());
    }
}
