package com.example.proyectomoviles.api;

import com.example.proyectomoviles.modelos.TipoCultivoItem;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EstadisticasApiServicio {

    @GET("estadisticas/tipos-cultivo/{usuario_id}")
    Call<TipoCultivoItem.RespuestaTiposCultivo> getTiposCultivo(@Path("usuario_id") String usuarioId);
}