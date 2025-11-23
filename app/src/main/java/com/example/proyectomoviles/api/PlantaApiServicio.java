package com.example.proyectomoviles.api;

import com.example.proyectomoviles.modelos.Plaga;
import com.example.proyectomoviles.modelos.Planta;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlantaApiServicio {
    @GET("getPlagas")
    Call<List<Plaga>> obtenerTodasLasPlagas();

    @GET("/plantas-por-mes")
    Call<List<Planta>> getPlantasPorMes(@Query("mes") int mes);

}


