package com.example.proyectomoviles.api;

import com.example.proyectomoviles.modelos.Huerto;
import com.example.proyectomoviles.modelos.Usuario;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UsuarioApiServicio {

    @POST("registerUser")
    Call<Map<String, Object>> registrarUsuario(@Body Usuario usuario);

    @POST("loginUser")
    Call<Map<String, Object>> loginUsuario(@Body Map<String, String> body);

    @GET("huertos/{usuarioId}")
    Call<List<Huerto>> getHuertosPorUsuario(@Path("usuarioId") String usuarioId);


}
