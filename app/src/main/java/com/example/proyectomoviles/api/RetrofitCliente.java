package com.example.proyectomoviles.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitCliente {
    private static Retrofit instancia;
    private static String BASE_URL = "http://10.0.2.2:8000/";

    public static Retrofit obtener() {
        if (instancia == null) {
            HttpLoggingInterceptor log = new HttpLoggingInterceptor();
            log.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient cliente = new OkHttpClient.Builder()
                    .addInterceptor(log)
                    .build();


            instancia = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(cliente)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return instancia;
    }

    // Método helper para crear el servicio
    public static PlantaApiServicio getPlantaService() {
        return obtener().create(PlantaApiServicio.class);
    }
    public static UsuarioApiServicio getUsuarioService() {
        return obtener().create(UsuarioApiServicio.class);
    }




}
