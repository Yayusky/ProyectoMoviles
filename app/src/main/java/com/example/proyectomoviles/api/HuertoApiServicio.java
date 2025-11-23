package com.example.proyectomoviles.api;

import com.example.proyectomoviles.modelos.Huerto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface HuertoApiServicio {
    @POST("/registrarHuerto")
    Call<Void> registrarHuerto(@Body Huerto huerto);
}
