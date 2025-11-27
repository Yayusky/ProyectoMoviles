package com.example.proyectomoviles.api;

import com.example.proyectomoviles.modelos.Huerto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface HuertoApiServicio {
    @POST("/registrarHuerto")
    Call<Void> registrarHuerto(@Body Huerto huerto);

    @GET("/huertos/{usuario_id}")
    Call<List<Huerto>> obtenerHuertos(@Path("usuario_id") String usuarioId);

    @DELETE("huertos/{huerto_id}")
    Call<Void> eliminarHuerto(@Path("huerto_id") String huertoId);
}
