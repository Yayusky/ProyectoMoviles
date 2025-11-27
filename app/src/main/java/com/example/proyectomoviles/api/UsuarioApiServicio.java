package com.example.proyectomoviles.api;

import com.example.proyectomoviles.modelos.Huerto;
import com.example.proyectomoviles.modelos.Usuario;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UsuarioApiServicio {

    @POST("registrarUsuario")
    Call<Map<String, Object>> registrarUsuario(@Body Usuario usuario);

    @POST("loginUsuario")
    Call<Map<String, Object>> loginUsuario(@Body Map<String, String> body);

    @GET("huertos/{usuarioId}")
    Call<List<Huerto>> getHuertosPorUsuario(@Path("usuarioId") String usuarioId);
    @GET("consejos/random")
    Call<ConsejoApiServicio.ConsejoRespuesta> obtenerConsejoAleatorio();

    @PUT("actualizarUsuario/{usuario_id}")
    Call<Usuario.UsuarioRespuesta> actualizarLogin(
            @Path("usuario_id") String usuarioId,
            @Body Usuario.UsuarioUpdate body
    );


}
