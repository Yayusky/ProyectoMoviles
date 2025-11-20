package com.example.proyectomoviles.api;

import com.example.proyectomoviles.modelos.Plaga;
//import com.example.proyectomoviles.modelos.Planta;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PlantaApiServicio {
//    @GET("getPlantas")
//    Call<List<Planta>> obtenerTodasLasPlantas();

    @GET("getPlagas")
    Call<List<Plaga>> obtenerTodasLasPlagas();
}

