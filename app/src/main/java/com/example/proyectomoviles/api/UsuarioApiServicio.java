package com.example.proyectomoviles.api;

import com.example.proyectomoviles.modelos.Usuario;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UsuarioApiServicio {

    @POST("registerUser")
    Call<Map<String, Object>> registrarUsuario(@Body Usuario usuario);

    @POST("loginUser")
    Call<Map<String, Object>> loginUsuario(@Body Map<String, String> body);


}
